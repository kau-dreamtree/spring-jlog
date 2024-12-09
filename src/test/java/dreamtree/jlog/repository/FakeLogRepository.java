package dreamtree.jlog.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import dreamtree.jlog.domain.Log;
import dreamtree.jlog.domain.Room;

public class FakeLogRepository implements LogRepository {

    private static final AtomicLong counter = new AtomicLong(1);

    private final List<Log> logs;

    public FakeLogRepository() {
        this(new ArrayList<>());
    }

    public FakeLogRepository(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public Log save(Log log) {
        long id = counter.getAndIncrement();
        log = new Log(id, log.getRoom(), log.getMember(), log.getExpense(), log.getMemo());
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
                .filter(log -> Objects.equals(id, log.getId()))
                .findFirst();
    }

    @Override
    public List<Log> findAllByRoom(Room room) {
        return logs.stream()
                .filter(log -> Objects.equals(room, log.getRoom()))
                .toList();
    }
}
