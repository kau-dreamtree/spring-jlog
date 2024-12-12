package com.jlog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomJoinRequest(

        @JsonProperty("room_code")
        @NotBlank
        @Size(min = 8, max = 8)
        String code,

        @NotBlank
        @Size(min = 2, max = 16)
        String username
) {
}
