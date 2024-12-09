package com.jlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlog.domain.Log;

public interface LogJpaRepository extends LogRepository, JpaRepository<Log, Long> {
}
