package dreamtree.jlog.service.finder;

import static dreamtree.jlog.exception.JLogErrorCode.ROOM_NOT_EXISTS;

import org.springframework.stereotype.Service;

import dreamtree.jlog.domain.Room;
import dreamtree.jlog.exception.JLogException;
import dreamtree.jlog.repository.RoomJpaRepository;

@Service
public class RoomFinder {

    private final RoomJpaRepository roomRepository;

    public RoomFinder(RoomJpaRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomByCode(String code) {
        return roomRepository.findByCode(code).orElseThrow(() -> new JLogException(ROOM_NOT_EXISTS));
    }
}
