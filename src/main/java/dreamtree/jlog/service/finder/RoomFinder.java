package dreamtree.jlog.service.finder;

import static dreamtree.jlog.exception.JLogErrorCode.ROOM_NOT_EXISTS;

import org.springframework.stereotype.Service;

import dreamtree.jlog.domain.Room;
import dreamtree.jlog.exception.JLogException;
import dreamtree.jlog.repository.RoomRepository;

@Service
public class RoomFinder {

    private final RoomRepository roomRepository;

    public RoomFinder(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomByCode(String code) {
        return roomRepository.findByCode(code).orElseThrow(() -> new JLogException(ROOM_NOT_EXISTS));
    }
}
