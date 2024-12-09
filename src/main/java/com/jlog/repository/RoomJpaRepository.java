package com.jlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlog.domain.Room;

public interface RoomJpaRepository extends RoomRepository, JpaRepository<Room, Long> {
}
