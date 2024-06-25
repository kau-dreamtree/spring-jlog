package dreamtree.jlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dreamtree.jlog.domain.Room;

public interface RoomJpaRepository extends RoomRepository, JpaRepository<Room, Long> {
}
