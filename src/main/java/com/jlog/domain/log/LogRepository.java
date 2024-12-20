package com.jlog.domain.log;

import static com.jlog.exception.JLogErrorCode.LOG_NOT_EXISTS;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.jlog.domain.room.Room;
import com.jlog.exception.JLogException;

public interface LogRepository {

    Log save(Log log);

    void delete(Log log);

    Optional<Log> findById(Long id);

    List<Log> findAllByRoom(Room room);

    List<Log> findLogsByRoomAndLastId(Room room, Long lastId, Pageable pageable);

    default Log fetchById(long id) {
        return findById(id).orElseThrow(() -> new JLogException(LOG_NOT_EXISTS));
    }
}
