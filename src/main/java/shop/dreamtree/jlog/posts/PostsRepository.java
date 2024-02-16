package shop.dreamtree.jlog.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.dreamtree.jlog.room.Room;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> getAllByRoomUid(String uid);
}
