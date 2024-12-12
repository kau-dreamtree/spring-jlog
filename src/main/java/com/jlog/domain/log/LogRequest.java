package com.jlog.domain.log;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogRequest(

        @JsonProperty("log_id")
        @NotNull
        @Positive
        Long id,

        @JsonProperty("room_code")
        @NotBlank
        @Size(min = 8, max = 8)
        String roomCode,

        @NotBlank
        @Size(min = 2, max = 16)
        String username,

        @JsonProperty("amount")
        @NotNull
        Long expense,

        @Size(max = 255)
        String memo
) {
}
