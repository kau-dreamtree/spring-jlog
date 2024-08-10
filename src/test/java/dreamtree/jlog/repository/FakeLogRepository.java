package dreamtree.jlog.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dreamtree.jlog.domain.Log;
import dreamtree.jlog.domain.Room;

public class FakeLogRepository implements LogRepository {

    private final List<Log> logs;

    public FakeLogRepository() {
        this(new ArrayList<>());
    }

    public FakeLogRepository(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public Log save(Log log) {
        logs.add(log);
        return log;
    }

    @Override
    public void delete(Log log) {
        logs.remove(log);
    }

    @Override
    public Optional<Log> findById(Long id) {
        return logs.stream()
                .filter(log -> Objects.equals(id, log.id()))
                .findFirst();
    }

    @Override
    public List<Log> findAllByRoom(Room room) {
        return logs.stream()
                .filter(log -> Objects.equals(room, log.room()))
                .toList();
    }
}
