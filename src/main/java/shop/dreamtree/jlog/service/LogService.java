package shop.dreamtree.jlog.service;

import static shop.dreamtree.jlog.exception.JLogErrorCode.LOG_NOT_EXISTS;
import static shop.dreamtree.jlog.exception.JLogErrorCode.ROOM_NOT_EXISTS;
import static shop.dreamtree.jlog.exception.JLogErrorCode.UNAUTHORIZED_USERNAME;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.dreamtree.jlog.domain.log.Balance;
import shop.dreamtree.jlog.domain.log.Log;
import shop.dreamtree.jlog.domain.room.Room;
import shop.dreamtree.jlog.dto.LogDto;
import shop.dreamtree.jlog.dto.LogResponse;
import shop.dreamtree.jlog.exception.JLogException;
import shop.dreamtree.jlog.repository.LogRepository;
import shop.dreamtree.jlog.repository.RoomRepository;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final RoomRepository roomRepository;

    public LogService(LogRepository logRepository, RoomRepository roomRepository) {
        this.logRepository = logRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public void save(LogDto logDto) {
        authenticate(logDto.code(), logDto.username());
        logRepository.save(logDto.toLogs());
    }

    public LogResponse getLogsWithBalance(String roomCode, String username) {
        authenticate(roomCode, username);
        List<Log> logs = logRepository.getAllByCodeOrderByCreatedDateDesc(roomCode);
        List<LogDto> dtos = logs.stream().map(LogDto::from).toList();
        Balance balance = Balance.from(logs);
        return new LogResponse(balance, dtos);
    }

    @Transactional
    public void update(LogDto logDto) {
        authenticate(logDto.code(), logDto.username());
        Log log = getLogById(logDto.id());
        log.updateExpense(logDto.expense());
        logRepository.save(log);
    }

    @Transactional
    public void delete(LogDto logDto) {
        authenticate(logDto.code(), logDto.username());
        logRepository.deleteById(logDto.id());
    }

    private void authenticate(String code, String username) {
        Room room = getRoomByCode(code);
        if (room.cannotJoin(username)) {
            throw new JLogException(UNAUTHORIZED_USERNAME);
        }
    }

    private Log getLogById(long id) {
        return logRepository.findById(id).orElseThrow(JLogException.getFrom(LOG_NOT_EXISTS));
    }

    private Room getRoomByCode(String code) {
        return roomRepository.findByCode(code).orElseThrow(JLogException.getFrom(ROOM_NOT_EXISTS));
    }
}
