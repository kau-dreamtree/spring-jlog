package com.jlog.domain.room;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomCreateResponse(
        @JsonProperty("room_code")
        String code
) {
}
