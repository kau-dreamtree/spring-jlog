package dreamtree.jlog.dto;

import org.springframework.web.bind.annotation.BindParam;

import com.fasterxml.jackson.annotation.JsonAlias;

public record LogRequest(
        @JsonAlias("log_id")
        @BindParam("log_id")
        Long id,

        @JsonAlias("room_code")
        @BindParam("room_code")
        String roomCode,

        String username,

        @JsonAlias("amount")
        @BindParam("amount")
        Long expense,

        String memo
) {
    public LogRequest withId(Long id) {
        return new LogRequest(id, roomCode, username, expense, memo);
    }
}
