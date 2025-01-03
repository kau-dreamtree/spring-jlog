package com.jlog.domain.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated(forRemoval = true)
public record RoomJoinRequest(

        @JsonProperty("room_code")
        @NotBlank
        @Size(min = 8, max = 8)
        String roomCode,

        @NotBlank
        @Size(min = 2, max = 16)
        String username
) implements RoomRequest {
}
