package dreamtree.jlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dreamtree.jlog.domain.Log;
import dreamtree.jlog.domain.Room;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByRoom(Room room);
}
