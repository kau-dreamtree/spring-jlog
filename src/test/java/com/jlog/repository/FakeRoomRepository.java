package com.jlog.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.jlog.domain.Room;

public class FakeRoomRepository implements RoomRepository {

    private static final AtomicLong counter = new AtomicLong(1);

    private final Map<Long, Room> rooms;

    public FakeRoomRepository() {
        rooms = new HashMap<>();
    }

    @Override
    public Room save(Room room) {
        if (Objects.isNull(room.getId())) {
            long id = counter.getAndIncrement();
            room = new Room(id, room.getCode(), room.getMembers());
        }
        rooms.put(room.getId(), room);
        return room;
    }

    @Override
    public Optional<Room> findByCode(String code) {
        return rooms.values()
                .stream()
                .filter(room -> Objects.equals(code, room.getCode()))
                .findFirst();
    }
}
