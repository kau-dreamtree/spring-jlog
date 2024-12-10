package com.jlog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoomCreateRequest(
        @NotBlank
        @Size(min = 2, max = 16)
        String username
) {
}
