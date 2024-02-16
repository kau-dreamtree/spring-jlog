package shop.dreamtree.jlog.room;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    void create(RoomDto roomDto) {
        if (roomRepository.existsByUid(roomDto.getUid())) {
            throw new EntityExistsException("The uid already exists.");
        }
        roomRepository.save(roomDto.toEntity());
    }

    @Transactional
    RoomDto join(RoomDto roomDto) {
        Room room = roomRepository.findByUid(roomDto.getUid())
                .orElseThrow(() -> new EntityNotFoundException("The uid does not exists."));
        room.join(roomDto.getSecondUsername());
        System.out.println(roomDto.getSecondUsername());
        return RoomDto.builder()
                .uid(room.getUid())
                .firstUsername(room.getFirstUsername())
                .secondUsername(room.getSecondUsername())
                .build();
    }
}
