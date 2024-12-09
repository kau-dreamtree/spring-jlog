package dreamtree.jlog.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import dreamtree.jlog.domain.Room;

public class FakeRoomRepository implements RoomRepository {

    private static final AtomicLong counter = new AtomicLong(1);

    private final List<Room> rooms;

    public FakeRoomRepository() {
        this(new ArrayList<>());
    }

    public FakeRoomRepository(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public Room save(Room room) {
        if (Objects.nonNull(room.getId())) {
            return room;
        }
        long id = counter.getAndIncrement();
        room = new Room(id, room.getCode(), room.getMembers());
        rooms.add(room);
        return room;
    }

    @Override
    public Optional<Room> findByCode(String code) {
        return rooms.stream()
                .filter(room -> Objects.equals(code, room.getCode()))
                .findFirst();
    }
}
