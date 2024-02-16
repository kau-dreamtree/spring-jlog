package shop.dreamtree.jlog.room;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByUid(String uid);
    Optional<Room> findByUid(String uid);
}
