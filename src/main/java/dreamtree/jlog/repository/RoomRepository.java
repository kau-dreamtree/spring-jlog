package dreamtree.jlog.repository;

import static dreamtree.jlog.exception.JLogErrorCode.ROOM_NOT_EXISTS;

import java.util.Optional;

import dreamtree.jlog.domain.Room;
import dreamtree.jlog.exception.JLogException;

public interface RoomRepository {

    Room save(Room room);

    Optional<Room> findByCode(String code);

    default Room fetchByCode(String code) {
        return findByCode(code).orElseThrow(() -> new JLogException(ROOM_NOT_EXISTS));
    }
}
