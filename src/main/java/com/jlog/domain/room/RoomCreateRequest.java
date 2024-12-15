package com.jlog.domain.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoomCreateRequest(
        @NotBlank
        @Size(min = 2, max = 16)
        String username
) implements RoomRequest {

    @Override
    public String roomCode() {
        throw new UnsupportedOperationException();
    }
}
