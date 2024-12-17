package com.jlog.domain.log;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

import static com.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlog.domain.member.Member;
import com.jlog.domain.room.Room;
import com.jlog.domain.room.RoomRepository;
import com.jlog.exception.JLogException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogRepository logRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public Log create(LogDto request) {
        Room room = roomRepository.fetchByCode(request.roomCode());
        Member member = room.getMemberByName(request.username());
        Objects.requireNonNull(member);
        Log saved = logRepository.save(Log.builder()
                .room(room)
                .member(member)
                .expense(request.expense())
                .memo(request.memo())
                .build());
        member.addExpense(saved.getExpense());
        return saved;
    }

    @Transactional(readOnly = true)
    public LogsWithOutpayResponse findAll(String roomCode, String username) {
        Room room = roomRepository.fetchByCode(roomCode);
        Member member = room.getMemberByName(username);
        Objects.requireNonNull(member);
        List<LogResponse> logs = findAllLogsByRoomOrderByCreatedDateDesc(room);
        return LogsWithOutpayResponse.of(room, logs);
    }

    private List<LogResponse> findAllLogsByRoomOrderByCreatedDateDesc(Room room) {
        return logRepository.findAllByRoom(room)
                .stream()
                .map(LogResponse::from)
                .sorted(comparing(LogResponse::createdAt, reverseOrder()))
                .toList();
    }

    @Transactional(readOnly = true)
    public Slice<Log> findByRoom(LogDto logDto, Pageable pageable) {
        Room room = roomRepository.fetchByCode(logDto.roomCode());
        Member member = room.getMemberByName(logDto.username());
        Objects.requireNonNull(member);
        return logRepository.findByRoom(room, pageable);
    }

    @Transactional
    public Log update(LogDto request) {
        Room room = roomRepository.fetchByCode(request.roomCode());
        Member member = room.getMemberByName(request.username());
        Log log = logRepository.fetchById(request.id());
        validateLogOwner(log, member);
        member.addExpense(request.expense() - log.getExpense());
        log.updateExpense(request.expense());
        log.updateMemo(request.memo());
        return log;
    }

    @Transactional
    public void delete(LogDto request) {
        Room room = roomRepository.fetchByCode(request.roomCode());
        Member member = room.getMemberByName(request.username());
        Log log = logRepository.fetchById(request.id());
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
