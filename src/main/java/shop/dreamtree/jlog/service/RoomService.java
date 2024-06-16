package shop.dreamtree.jlog.service;

import static shop.dreamtree.jlog.exception.JLogErrorCode.ROOM_NOT_EXISTS;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.dreamtree.jlog.exception.JLogException;
import shop.dreamtree.jlog.domain.room.Room;
import shop.dreamtree.jlog.dto.RoomRequest;
import shop.dreamtree.jlog.repository.RoomRepository;
import shop.dreamtree.jlog.util.Encryptor;

@Service
public class RoomService {

    private static final int ROOM_CODE_LENGTH = 8;

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public String create(String username) {
        String code = Encryptor.randomUniqueString(ROOM_CODE_LENGTH);
        System.out.println(code);
        Room room = new Room(code, username);
        roomRepository.save(room);
        return code;
    }

    @Transactional
    public void join(RoomRequest request) {
        Room room = getRoomByCode(request.code());
        room.join(request.username());
        roomRepository.save(room);
    }

    private Room getRoomByCode(String code) {
        return roomRepository.findByCode(code).orElseThrow(JLogException.getFrom(ROOM_NOT_EXISTS));
    }
}
