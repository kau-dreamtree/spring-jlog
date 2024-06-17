package shop.dreamtree.jlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import shop.dreamtree.jlog.domain.log.Log;

public record LogRequest(
        @JsonProperty("log_id") Long id,
        @JsonProperty("room_code") String code,
        String username,
        @JsonProperty("amount") long expense,
        String memo
) {
    public Log toLog() {
        return Log.builder()
                .id(id)
                .code(code)
                .username(username)
                .expense(expense)
                .memo(memo)
                .build();
    }
}
