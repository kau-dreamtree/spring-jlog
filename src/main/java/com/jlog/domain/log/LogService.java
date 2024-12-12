package com.jlog.domain.log;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

import static com.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlog.domain.member.Member;
import com.jlog.domain.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Transactional
    public long create(LogRequest request) {
        log.info("create() {}", request.toString());
        Room room = roomRepository.fetchByCode(request.roomCode());
        Member member = room.getMemberByName(request.username());
        Log saved = logRepository.save(Log.builder()
                .room(room)
                .member(member)
                .expense(request.expense())
                .memo(request.memo())
                .build());
        room.addLog(saved);
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public LogsWithOutpayResponse findAll(String roomCode, String username) {
        Room room = roomRepository.fetchByCode(roomCode);
        room.getMemberByName(username);
        List<LogResponse> logs = findAllLogsByRoomOrderByCreatedDateDesc(room);
        return LogsWithOutpayResponse.of(room, logs);
    }

    private List<LogResponse> findAllLogsByRoomOrderByCreatedDateDesc(Room room) {
        return logRepository.findAllByRoom(room)
                .stream()
                .map(LogResponse::from)
                .sorted(comparing(LogResponse::createdDate, reverseOrder()))
                .toList();
    }

    @Transactional
    public void update(LogRequest request) {
        log.info("LogService: update(): {}", request.toString());
        Room room = roomRepository.fetchByCode(request.roomCode());
        Member member = room.getMemberByName(request.username());
        Log log = logRepository.fetchById(request.id());
        validateLogOwner(log, member);
        member.addExpense(request.expense() - log.getExpense());
        log.updateExpense(request.expense());
        log.updateMemo(request.memo());
        logRepository.save(log);
        memberRepository.save(member);
    }

    @Transactional
    public void delete(LogRequest request) {
        log.info("LogService: delete(): {}", request.toString());
        Room room = roomRepository.fetchByCode(request.roomCode());
        Member member = room.getMemberByName(request.username());
        Log log = logRepository.fetchById(request.id());
        validateLogOwner(log, member);
        member.subtractExpense(log.getExpense());
        logRepository.delete(log);
        memberRepository.save(member);
    }

    private void validateLogOwner(Log log, Member member) {
        if (!log.ownedBy(member)) {
            throw new JLogException(UNAUTHORIZED_MEMBER);
        }
    }
}
