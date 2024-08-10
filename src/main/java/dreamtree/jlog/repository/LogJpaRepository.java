package dreamtree.jlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dreamtree.jlog.domain.Log;

public interface LogJpaRepository extends LogRepository, JpaRepository<Log, Long> {
}
