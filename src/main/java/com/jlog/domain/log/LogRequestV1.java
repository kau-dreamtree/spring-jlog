package com.jlog.domain.log;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record LogRequestV1(

        Long id,

        @NotBlank
        @Size(min = 8, max = 8)
        String roomCode,

        @NotBlank
        @Size(min = 2, max = 16)
        String username,

        @Positive
        Long expense,

        @Size(max = 255)
        String memo

) implements LogDto {

    public static LogRequestV1 of(Long id, String roomCode, String username, Long expense, String memo) {
        return new LogRequestV1(id, roomCode, username, expense, memo);
    }

    public static LogRequestV1 of(Long id, String roomCode, String username) {
        return of(id, roomCode, username, null, null);
    }

    public static LogRequestV1 of(String roomCode, String username, Long expense, String memo) {
        return of(null, roomCode, username, expense, memo);
    }
}
