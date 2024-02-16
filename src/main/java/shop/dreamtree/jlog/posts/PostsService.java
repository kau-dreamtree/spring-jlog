package shop.dreamtree.jlog.posts;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.dreamtree.jlog.room.Room;
import shop.dreamtree.jlog.room.RoomRepository;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final RoomRepository roomRepository;

    @Transactional
    void save(PostsDto postsDto) {
        authorize(postsDto.getRoomUid(), postsDto.getUsername());
        postsRepository.save(postsDto.toEntity());
    }

    List<Posts> find(String roomUid, String username) {
        authorize(roomUid, username);
        return postsRepository.getAllByRoomUid(roomUid);
    }

    private void authorize(String roomUid, String username) {
        Room room = roomRepository.findByUid(roomUid)
                .orElseThrow(() -> new EntityNotFoundException("No such room exists."));
        if (!room.contains(username)) {
            throw new IllegalArgumentException("Unauthorized username");
        }
    }
}
