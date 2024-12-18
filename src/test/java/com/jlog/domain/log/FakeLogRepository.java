package com.jlog.domain.log;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

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
    public Slice<Log> findByRoom(Room room, Pageable pageable) {
        Comparator<Log> comparator = comparator(pageable);
        List<Log> contents = logs.values()
                .stream()
                .filter(log -> pageNumberFilter(log, pageable))
                .filter(log -> Objects.equals(log.getRoom(), room))
                .sorted(comparator)
                .toList();
        return new SliceImpl<>(contents);
    }

    private boolean pageNumberFilter(Log log, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int id = log.getId().intValue();
        return pageNumber * pageSize <= id && id <= (pageNumber + 1) * pageSize;
    }

    private Comparator<Log> comparator(Pageable pageable) {
        Sort sort = pageable.getSort();
        String property = "createdAt";
        Order order = Objects.requireNonNullElse(sort.getOrderFor(property), Order.by(property));
        Direction direction = order.getDirection();
        Comparator<Log> comparator = Comparator.comparing(Log::getCreatedAt);
        return direction == Direction.ASC ? comparator : comparator.reversed();
    }
}
