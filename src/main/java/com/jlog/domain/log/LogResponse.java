package com.jlog.domain.log;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jlog.config.JLogDateTimeDeserializer;
import com.jlog.config.JLogDateTimeSerializer;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated(forRemoval = true)
public record LogResponse(

        @JsonProperty("log_id")
        long id,

        @JsonProperty("room_code")
        String code,

        String username,

        @JsonProperty("amount")
        long expense,

        String memo,

        @JsonSerialize(using = JLogDateTimeSerializer.class)
        @JsonDeserialize(using = JLogDateTimeDeserializer.class)
        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonSerialize(using = JLogDateTimeSerializer.class)
        @JsonDeserialize(using = JLogDateTimeDeserializer.class)
        @JsonProperty("modified_at")
        LocalDateTime modifiedAt
) {
        public static LogResponse from(Log log) {
                return new LogResponse(
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
