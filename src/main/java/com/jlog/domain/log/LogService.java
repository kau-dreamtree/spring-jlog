package com.jlog.domain.log;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

import static com.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlog.domain.member.Member;
import com.jlog.domain.room.RoomRepository;
import com.jlog.exception.JLogException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private static final int DEFAULT_CONTENT_SIZE = 20;
    private static final Order DEFAULT_ORDER = Order.desc("id");
    private static final Sort DEFAULT_SORT = Sort.by(DEFAULT_ORDER);

    private final LogRepository logRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public Log create(LogDto request) {
        var room = roomRepository.fetchByCode(request.roomCode());
        var member = room.getMemberByName(request.username());
        Objects.requireNonNull(member);
        var log = Log.builder()
                .room(room)
                .member(member)
                .expense(request.expense())
                .memo(request.memo())
                .build();
        var saved = logRepository.save(log);
        member.addExpense(saved.getExpense());
        return saved;
    }

    @SuppressWarnings({"removal", "DeprecatedIsStillUsed"})
    @Deprecated(forRemoval = true)
    @Transactional(readOnly = true)
    public LogsWithOutpayResponse findAll(LogDto request) {
        var room = roomRepository.fetchByCode(request.roomCode());
        var member = room.getMemberByName(request.username());
        Objects.requireNonNull(member);
        var logs = logRepository.findAllByRoom(room)
                .stream()
                .map(LogResponse::from)
                .sorted(comparing(LogResponse::createdAt, reverseOrder()))
                .toList();
        return LogsWithOutpayResponse.of(room, logs);
    }

    @Transactional(readOnly = true)
    public List<Log> findLogsByRoomAfterId(LogDto request) {
        var room = roomRepository.fetchByCode(request.roomCode());
        var member = room.getMemberByName(request.username());
        Objects.requireNonNull(member);
        var pageRequest = PageRequest.of(0, DEFAULT_CONTENT_SIZE, DEFAULT_SORT);
        return logRepository.findLogsByRoomAfterId(room, request.id(), pageRequest);
    }

    @Transactional
    public Log update(LogDto request) {
        var room = roomRepository.fetchByCode(request.roomCode());
        var member = room.getMemberByName(request.username());
        var log = logRepository.fetchById(request.id());
        validateLogOwner(log, member);
        member.addExpense(request.expense() - log.getExpense());
        log.updateExpense(request.expense());
        log.updateMemo(request.memo());
        return log;
    }

    @Transactional
    public void delete(LogDto request) {
        var room = roomRepository.fetchByCode(request.roomCode());
        var member = room.getMemberByName(request.username());
        var log = logRepository.fetchById(request.id());
        validateLogOwner(log, member);
        member.subtractExpense(log.getExpense());
        logRepository.delete(log);
    }

    private void validateLogOwner(Log log, Member member) {
        if (!log.ownedBy(member)) {
            throw new JLogException(UNAUTHORIZED_MEMBER);
        }
    }
}
