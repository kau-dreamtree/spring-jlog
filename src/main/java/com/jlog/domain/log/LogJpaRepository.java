package com.jlog.domain.log;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LogJpaRepository extends LogRepository, JpaRepository<Log, Long> {
}
