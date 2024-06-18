package shop.dreamtree.jlog.service.finder;

import static shop.dreamtree.jlog.exception.JLogErrorCode.ROOM_NOT_EXISTS;

import org.springframework.stereotype.Service;

import shop.dreamtree.jlog.domain.Room;
import shop.dreamtree.jlog.exception.JLogException;
import shop.dreamtree.jlog.repository.RoomRepository;

@Service
public class RoomFinder {

    private final RoomRepository roomRepository;

    public RoomFinder(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomByCode(String code) {
        return roomRepository.findByCode(code).orElseThrow(JLogException.getFrom(ROOM_NOT_EXISTS));
    }
}
