package com.jlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomJoinRequest(
        @JsonProperty("room_code") String code,
        String username
) {
}
