package shop.dreamtree.jlog.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import shop.dreamtree.jlog.domain.log.Log;

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
        LocalDateTime createdDate,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        @JsonProperty("modified_at")
        LocalDateTime modifiedDate
) {
        public static LogResponse from(Log log) {
                return new LogResponse(
                        log.id(),
                        log.roomCode(),
                        log.username(),
                        log.expense(),
                        log.memo(),
                        log.createdDate(),
                        log.modifiedDate()
                );
        }
}
