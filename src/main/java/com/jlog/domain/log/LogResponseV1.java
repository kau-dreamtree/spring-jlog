package com.jlog.domain.log;

import java.time.LocalDateTime;

public record LogResponseV1(
        long id,
        String roomCode,
        String username,
        long expense,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static LogResponseV1 from(Log log) {
        return new LogResponseV1(
                log.getId(),
                log.getRoomCode(),
                log.getMemberName(),
                log.getExpense(),
                log.getMemo(),
                log.getCreatedAt(),
                log.getModifiedAt()
        );
    }
}
