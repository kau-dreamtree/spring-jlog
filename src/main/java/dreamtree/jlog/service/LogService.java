package dreamtree.jlog.service;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

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
import dreamtree.jlog.repository.LogRepository;
import dreamtree.jlog.repository.MemberRepository;
import dreamtree.jlog.repository.RoomRepository;
import dreamtree.jlog.service.finder.LogFinder;
import dreamtree.jlog.service.finder.RoomFinder;

@Service
public class LogService {

    private static final Logger log = LoggerFactory.getLogger(LogService.class);
    private final LogFinder logFinder;
    private final LogRepository logRepository;

    private final RoomFinder roomFinder;
    private final RoomRepository roomRepository;

    private final MemberRepository memberRepository;

    public LogService(
            LogFinder logFinder,
            LogRepository logRepository,
            RoomFinder roomFinder,
            RoomRepository roomRepository,
            MemberRepository memberRepository
    ) {
        this.logFinder = logFinder;
        this.logRepository = logRepository;
        this.roomFinder = roomFinder;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }


    @Transactional
    public void createLog(LogRequest request) {
        log.info("LogService: createLog(): ${}", request.toString());
        Room room = roomFinder.getRoomByCode(request.roomCode());
        Member member = room.requireMemberExistsByName(request.username());
        Log saved = logRepository.save(Log.builder(room, member)
                .expense(request.expense())
                .memo(request.memo())
                .build());
        room.addLog(saved);
        roomRepository.save(room);
    }

    public LogsWithOutpayResponse getLogsWithOutpay(LogRequest request) {
        Room room = roomFinder.getRoomByCode(request.roomCode());
        room.requireMemberExistsByName(request.username());
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
        Room room = roomFinder.getRoomByCode(request.roomCode());
        Member member = room.requireMemberExistsByName(request.username());
        Log log = logFinder.getLogById(request.id());
        log.requireMemberEquals(member);
        member.addExpense(request.expense() - log.expense());
        log.updateExpense(request.expense());
        log.updateMemo(request.memo());
        logRepository.save(log);
        memberRepository.save(member);
    }

    @Transactional
    public void delete(LogRequest request) {
        Room room = roomFinder.getRoomByCode(request.roomCode());
        Member member = room.requireMemberExistsByName(request.username());
        Log log = logFinder.getLogById(request.id());
        log.requireMemberEquals(member);
        member.subtractExpense(log.expense());
        logRepository.delete(log);
        memberRepository.save(member);
    }
}
