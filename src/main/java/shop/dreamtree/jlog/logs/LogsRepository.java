package shop.dreamtree.jlog.logs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<Logs, Long> {
    List<Logs> getAllByRoomUid(String uid);
}
