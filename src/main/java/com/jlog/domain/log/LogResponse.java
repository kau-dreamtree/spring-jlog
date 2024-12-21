package com.jlog.domain.log;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

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

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
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
