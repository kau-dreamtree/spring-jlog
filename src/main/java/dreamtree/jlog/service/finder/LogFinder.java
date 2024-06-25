package dreamtree.jlog.service.finder;

import static dreamtree.jlog.exception.JLogErrorCode.ID_MUST_NOT_BE_NULL;
import static dreamtree.jlog.exception.JLogErrorCode.LOG_NOT_EXISTS;

import org.springframework.stereotype.Service;

import dreamtree.jlog.domain.Log;
import dreamtree.jlog.exception.JLogException;
import dreamtree.jlog.repository.LogRepository;

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
        return logRepository.findById(id).orElseThrow(() -> new JLogException(LOG_NOT_EXISTS));
    }
}
