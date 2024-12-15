package com.jlog.domain.log;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LogService {

    Log create(LogDto request);

    LogsWithOutpayResponse findAll(String roomCode, String username);

    Slice<LogResponseV1> findByRoom(LogDto logDto, Pageable pageable);

    Log update(LogDto request);

    void delete(LogDto request);
}
