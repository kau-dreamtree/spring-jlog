package com.jlog.domain.log;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jlog.config.JLogDateTimeDeserializer;
import com.jlog.config.JLogDateTimeSerializer;

public record LogResponseV1(

        long id,

        String roomCode,

        String username,

        long expense,

        String memo,
        @JsonSerialize(using = JLogDateTimeSerializer.class)
        @JsonDeserialize(using = JLogDateTimeDeserializer.class)
        LocalDateTime createdAt,

        @JsonSerialize(using = JLogDateTimeSerializer.class)
        @JsonDeserialize(using = JLogDateTimeDeserializer.class)
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
