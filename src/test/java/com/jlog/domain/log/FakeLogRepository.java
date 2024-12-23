package com.jlog.domain.log;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.domain.Pageable;

import com.jlog.domain.room.Room;

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
        return Optional.ofNullable(logs.get(id));
    }

    @Override
    public List<Log> findAllByRoom(Room room) {
        return logs.values()
                .stream()
                .filter(log -> Objects.equals(room, log.getRoom()))
                .toList();
    }

    @Override
    public List<Log> findLogsByRoomAfterId(Room room, Long lastId, Pageable pageable) {
        return logs.values()
                .stream()
                .filter(log -> Objects.equals(log.getRoom(), room))
                .filter(log -> log.getId() > lastId)
                .limit(pageable.getPageSize())
                .sorted(Comparator.comparing(Log::getCreatedAt).reversed())
                .toList();
    }
}
