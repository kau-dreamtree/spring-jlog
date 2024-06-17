package shop.dreamtree.jlog.service.finder;

import static shop.dreamtree.jlog.exception.JLogErrorCode.ID_MUST_NOT_BE_NULL;
import static shop.dreamtree.jlog.exception.JLogErrorCode.LOG_NOT_EXISTS;

import java.util.List;

import org.springframework.stereotype.Service;

import shop.dreamtree.jlog.domain.log.Log;
import shop.dreamtree.jlog.exception.JLogException;
import shop.dreamtree.jlog.repository.LogRepository;

@Service
public class LogFinder {

    private final LogRepository logRepository;

    public LogFinder(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Log getLogById(Long id) {
        if (id == null) {
            throw new JLogException(ID_MUST_NOT_BE_NULL);
        }
        return logRepository.findById(id).orElseThrow(JLogException.getFrom(LOG_NOT_EXISTS));
    }

    public List<Log> findAllByRoomCode(String roomCode) {
        return logRepository.findAllByRoomCode(roomCode);
    }
}
