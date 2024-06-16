package shop.dreamtree.jlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomResponse(
        @JsonProperty("room_code") String code
) {
}
