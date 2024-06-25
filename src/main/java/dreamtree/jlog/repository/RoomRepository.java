package dreamtree.jlog.repository;

import java.util.Optional;

import dreamtree.jlog.domain.Room;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findByCode(String code);
}
