package com.jlog.domain.log;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jlog.domain.room.Room;

public interface LogJpaRepository extends LogRepository, JpaRepository<Log, Long> {
    @Query("SELECT l FROM Log l WHERE l.room = :room AND (:lastId IS NULL OR l.id < :lastId)")
    List<Log> findLogsByRoomAfterId(@Param("room") Room room, @Param("lastId") Long lastId, Pageable pageable);
}
