package com.jlog.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomJpaRepository extends RoomRepository, JpaRepository<Room, Long> {
}
