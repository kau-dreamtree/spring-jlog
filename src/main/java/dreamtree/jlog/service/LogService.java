package dreamtree.jlog.service;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

import static dreamtree.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreamtree.jlog.domain.Log;
import dreamtree.jlog.domain.Member;
import dreamtree.jlog.domain.Room;
import dreamtree.jlog.dto.LogRequest;
import dreamtree.jlog.dto.LogResponse;
import dreamtree.jlog.dto.LogsWithOutpayResponse;
import dreamtree.jlog.exception.JLogException;
import dreamtree.jlog.repository.LogRepository;
import dreamtree.jlog.repository.MemberRepository;
import dreamtree.jlog.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {

    private static final Logger log = LoggerFactory.getLogger(LogService.class);

    private final LogRepository logRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createLog(LogRequest request) {
        log.info("LogService: createLog(): {}", request.toString());
        Room room = roomRepository.fetchByCode(request.roomCode());
        Member member = room.getMemberByName(request.username());
        Log saved = logRepository.save(Log.builder()
                .room(room)
                .member(member)
                .expense(request.expense())
                .memo(request.memo())
                .build());
        room.addLog(saved);
        roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public LogsWithOutpayResponse getLogsWithOutpay(String roomCode, String username) {
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
