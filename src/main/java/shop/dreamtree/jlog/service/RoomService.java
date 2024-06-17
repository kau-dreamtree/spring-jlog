package shop.dreamtree.jlog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.dreamtree.jlog.domain.room.Room;
import shop.dreamtree.jlog.dto.RoomCreateRequest;
import shop.dreamtree.jlog.dto.RoomJoinRequest;
import shop.dreamtree.jlog.repository.RoomRepository;
import shop.dreamtree.jlog.service.finder.RoomFinder;
import shop.dreamtree.jlog.util.Encryptor;

@Service
public class RoomService {

    private static final int ROOM_CODE_LENGTH = 5;

    private final RoomFinder roomFinder;
    private final RoomRepository roomRepository;

    public RoomService(RoomFinder roomFinder, RoomRepository roomRepository) {
        this.roomFinder = roomFinder;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public String create(RoomCreateRequest request) {
        String code = Encryptor.randomUniqueString(ROOM_CODE_LENGTH);
        Room room = new Room(code, request.username());
        Room saved = roomRepository.save(room);
        return saved.code();
    }

    @Transactional
    public void join(RoomJoinRequest request) {
        Room room = roomFinder.getRoomByCode(request.code());
        room.join(request.username());
        roomRepository.save(room);
    }
}
