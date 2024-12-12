package com.jlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomCreateResponse(
        @JsonProperty("room_code")
        String code
) {
}
