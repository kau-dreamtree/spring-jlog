package shop.dreamtree.jlog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.dreamtree.jlog.domain.log.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByRoomCode(String roomCode);
}
