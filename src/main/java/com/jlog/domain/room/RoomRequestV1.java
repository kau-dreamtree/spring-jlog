package com.jlog.domain.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoomRequestV1(

        @Size(min = 8, max = 8)
        String roomCode,

        @NotBlank
        @Size(min = 2, max = 16)
        String username
) implements RoomRequest {
}
