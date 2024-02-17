package shop.dreamtree.jlog.room;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.dreamtree.jlog.util.Encryptor;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    String create(String username) {
        String uuid = UUID.randomUUID().toString();
        String uid = Objects.requireNonNull(Encryptor.hashString(uuid)).substring(0, 5);
        Room room = Room.builder()
                .uid(uid)
                .firstUsername(username)
                .build();
        roomRepository.save(room);
        return uid;
    }

    @Transactional
    void join(RoomDto roomDto) {
        Room room = roomRepository.findByUid(roomDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("The uid does not exists."));
        room.join(roomDto.getUsername());
        roomRepository.save(room);
    }
}
