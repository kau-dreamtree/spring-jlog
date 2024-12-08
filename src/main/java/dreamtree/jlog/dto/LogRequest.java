package dreamtree.jlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogRequest(
        @JsonProperty("log_id")
        Long id,
        @JsonProperty("room_code")
        String roomCode,
        String username,
        @JsonProperty("amount")
        Long expense,
        String memo
) {
    public LogRequest(String roomCode, String username) {
        this(null, roomCode, username, null, null);
    }

    public LogRequest(long id, String roomCode, String username) {
        this(id, roomCode, username, null, null);
    }
}
