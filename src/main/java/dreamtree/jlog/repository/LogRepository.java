package dreamtree.jlog.repository;

import static dreamtree.jlog.exception.JLogErrorCode.LOG_NOT_EXISTS;

import java.util.List;
import java.util.Optional;

import dreamtree.jlog.domain.Log;
import dreamtree.jlog.domain.Room;
import dreamtree.jlog.exception.JLogException;

public interface LogRepository {

    Log save(Log log);

    void delete(Log log);

    Optional<Log> findById(Long id);

    List<Log> findAllByRoom(Room room);

    default Log fetchById(long id) {
        return findById(id).orElseThrow(() -> new JLogException(LOG_NOT_EXISTS));
    }
}
