package dreamtree.jlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogRequest(
        @JsonProperty("log_id") Long id,
        @JsonProperty("room_code") String code,
        String username,
        @JsonProperty("amount") long expense,
        String memo
) {
}
