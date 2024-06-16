package shop.dreamtree.jlog.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import shop.dreamtree.jlog.domain.log.Log;

public record LogDto(

        @JsonProperty("log_id")
        long id,

        @JsonProperty("room_code")
        String code,

        String username,

        @JsonProperty("amount")
        long expense,

        String memo,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        @JsonProperty("created_at")
        LocalDateTime createdDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        @JsonProperty("modified_at")
        LocalDateTime modifiedDate
) {

    public Log toLogs() {
        return Log.builder()
                .id(id)
                .username(username)
                .expense(expense)
                .code(code)
                .memo(memo)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();
    }

    public static LogDto from(Log log) {
        return new LogDto(
                log.id(),
                log.code(),
                log.username(),
                log.expense(),
                log.memo(),
                log.createdDate(),
                log.modifiedDate()
        );
    }
}
