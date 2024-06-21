package dreamtree.jlog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dreamtree.jlog.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByCode(String code);
}
