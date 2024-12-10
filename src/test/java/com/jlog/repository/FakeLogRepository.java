package com.jlog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.jlog.domain.Log;
import com.jlog.domain.Room;

public class FakeLogRepository implements LogRepository {

    private static final AtomicLong counter = new AtomicLong(1);

    private final Map<Long, Log> logs;

    public FakeLogRepository() {
        logs = new HashMap<>();
    }

    @Override
    public Log save(Log log) {
        if (Objects.isNull(log.getId())) {
            long id = counter.getAndIncrement();
            log = new Log(id, log.getRoom(), log.getMember(), log.getExpense(), log.getMemo());
        }
        logs.put(log.getId(), log);
        return log;
    }

    @Override
    public void delete(Log log) {
        logs.remove(log.getId());
    }

    @Override
    public Optional<Log> findById(Long id) {
        return Optional.of(logs.get(id));
    }

    @Override
    public List<Log> findAllByRoom(Room room) {
        return logs.values()
                .stream()
                .filter(log -> Objects.equals(room, log.getRoom()))
                .toList();
    }
}
