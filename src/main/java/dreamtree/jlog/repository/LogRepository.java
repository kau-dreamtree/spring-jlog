package dreamtree.jlog.repository;

import java.util.List;
import java.util.Optional;

import dreamtree.jlog.domain.Log;
import dreamtree.jlog.domain.Room;

public interface LogRepository {
    Log save(Log log);
    void delete(Log log);
    Optional<Log> findById(Long id);
    List<Log> findAllByRoom(Room room);
}
