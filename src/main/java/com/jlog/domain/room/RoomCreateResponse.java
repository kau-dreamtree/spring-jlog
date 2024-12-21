package com.jlog.domain.room;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated(forRemoval = true)
public record RoomCreateResponse(@JsonProperty("room_code") String code) {
}
