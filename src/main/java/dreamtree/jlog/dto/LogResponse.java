package dreamtree.jlog.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import dreamtree.jlog.domain.Log;

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
                        log.getId(),
                        log.getRoom().getCode(),
                        log.getMember().getName(),
                        log.getExpense(),
                        log.getMemo(),
                        log.createdDate(),
                        log.modifiedDate()
                );
        }
}
