package dreamtree.jlog.repository.fixture;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dreamtree.jlog.domain.Room;
import dreamtree.jlog.repository.RoomRepository;

public class RoomCollectionRepository implements RoomRepository {

    private final List<Room> rooms;

    public RoomCollectionRepository() {
        this.rooms = new ArrayList<>();
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