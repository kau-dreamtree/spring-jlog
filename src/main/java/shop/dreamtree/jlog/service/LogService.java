package shop.dreamtree.jlog.service;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.dreamtree.jlog.domain.log.Log;
import shop.dreamtree.jlog.domain.room.Room;
import shop.dreamtree.jlog.dto.LogRequest;
import shop.dreamtree.jlog.dto.LogResponse;
import shop.dreamtree.jlog.dto.LogsWithOutpayResponse;
import shop.dreamtree.jlog.dto.OutpayDto;
import shop.dreamtree.jlog.repository.LogRepository;
import shop.dreamtree.jlog.repository.RoomRepository;
import shop.dreamtree.jlog.service.finder.LogFinder;
import shop.dreamtree.jlog.service.finder.RoomFinder;

@Service
public class LogService {

    private final LogFinder logFinder;
    private final LogRepository logRepository;

    private final RoomFinder roomFinder;
    private final RoomRepository roomRepository;

    public LogService(
            LogFinder logFinder,
            LogRepository logRepository, RoomFinder roomFinder,
            RoomRepository roomRepository
    ) {
        this.logFinder = logFinder;
        this.logRepository = logRepository;
        this.roomFinder = roomFinder;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public void createLog(LogRequest logRequest) {
        Room room = roomFinder.getRoomByCode(logRequest.code());
        room.authenticate(logRequest.username());
        Log saved = logRepository.save(logRequest.toLog());
        room.addLog(saved);
        roomRepository.save(room);
        // todo: Delete oldest when logs counts over max_count and update outpay
    }

    public LogsWithOutpayResponse getLogsWithOutpay(String roomCode, String username) {
        Room room = roomFinder.getRoomByCode(roomCode);
        room.authenticate(username);
        OutpayDto outpay = OutpayDto.from(room.outpay());
        List<LogResponse> responses = logFinder.findAllByRoomCode(roomCode)
                .stream()
                .map(LogResponse::from)
                .sorted(comparing(LogResponse::createdDate, reverseOrder()))
                .toList();
        return new LogsWithOutpayResponse(outpay, responses);
    }

    @Transactional
    public void update(LogRequest logRequest) {
        Room room = roomFinder.getRoomByCode(logRequest.code());
        room.authenticate(logRequest.username());
        Log log = logFinder.getLogById(logRequest.id());
        log.updateExpense(logRequest.expense());
        log.updateMemo(logRequest.memo());
        logRepository.save(log);
        updateRoom(room);
        // todo: Delete oldest when logs counts over max_count and update outpay
    }

    @Transactional
    public void delete(LogRequest request) {
        Room room = roomFinder.getRoomByCode(request.code());
        room.authenticate(request.username());
        Log log = logFinder.getLogById(request.id());
        logRepository.delete(log);
        updateRoom(room);
        // todo: Delete oldest when logs counts over max_count and update outpay
    }

    private void updateRoom(Room room) {
        List<Log> logs = logFinder.findAllByRoomCode(room.code());
        room.updateOutpay(logs);
    }
}
