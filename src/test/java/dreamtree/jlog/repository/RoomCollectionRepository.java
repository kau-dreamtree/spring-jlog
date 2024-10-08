package dreamtree.jlog.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dreamtree.jlog.domain.Room;

public class RoomCollectionRepository implements RoomRepository {

    private final List<Room> rooms;

    public RoomCollectionRepository() {
        this(new ArrayList<>());
    }

    public RoomCollectionRepository(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public Room save(Room room) {
        rooms.add(room);
        return room;
    }

    @Override
    public Optional<Room> findByCode(String code) {
        return rooms.stream()
                .filter(room -> Objects.equals(code, room.code()))
                .findFirst();
    }
}
