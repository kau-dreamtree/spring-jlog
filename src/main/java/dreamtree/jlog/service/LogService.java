package dreamtree.jlog.service;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreamtree.jlog.domain.Log;
import dreamtree.jlog.domain.Member;
import dreamtree.jlog.domain.Room;
import dreamtree.jlog.dto.LogRequest;
import dreamtree.jlog.dto.LogResponse;
import dreamtree.jlog.dto.LogsWithOutpayResponse;
import dreamtree.jlog.dto.OutpayResponse;
import dreamtree.jlog.repository.LogRepository;
import dreamtree.jlog.repository.MemberRepository;
import dreamtree.jlog.repository.RoomRepository;
import dreamtree.jlog.service.finder.LogFinder;
import dreamtree.jlog.service.finder.RoomFinder;

@Service
public class LogService {

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
        Room room = roomFinder.getRoomByCode(request.code());
        Member member = room.authenticate(request.username());
        Log log = Log.builder(room, member)
                .expense(request.expense())
                .memo(request.memo())
                .build();
        Log saved = logRepository.save(log);
        room.addLog(saved);
        roomRepository.save(room);
        // todo: Delete oldest when logs counts over max_count and update outpay
    }

    public LogsWithOutpayResponse getLogsWithOutpay(String roomCode, String username) {
        Room room = roomFinder.getRoomByCode(roomCode);
        room.authenticate(username);
        OutpayResponse outpay = OutpayResponse.from(room);
        List<LogResponse> responses = findAllLogsByRoomOrderByCreatedDateDesc(room);
        return new LogsWithOutpayResponse(outpay, responses);
    }

    public List<LogResponse> findAllLogsByRoomOrderByCreatedDateDesc(Room room) {
        return logFinder.findAllByRoom(room)
                .stream()
                .map(LogResponse::from)
                .sorted(comparing(LogResponse::createdDate, reverseOrder()))
                .toList();
    }

    @Transactional
    public void update(LogRequest request) {
        Room room = roomFinder.getRoomByCode(request.code());
        Member member = room.authenticate(request.username()); // todo: check this member equals to the member in the found log.
        Log log = logFinder.getLogById(request.id());
        long difference = request.expense() - log.expense();
        member.addExpense(difference);
        log.updateExpense(request.expense());
        log.updateMemo(request.memo());
        logRepository.save(log);
        memberRepository.save(member);
        // todo: Delete oldest when logs counts over max_count and update outpay
    }

    @Transactional
    public void delete(LogRequest request) {
        Room room = roomFinder.getRoomByCode(request.code());
        Member member = room.authenticate(request.username());
        Log log = logFinder.getLogById(request.id());
        long expense = log.expense();
        member.subtractExpense(expense);
        logRepository.delete(log);
        memberRepository.save(member);
        // todo: Delete oldest when logs counts over max_count and update outpay
    }
}
