package shop.dreamtree.jlog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomRequest(
        @JsonProperty("room_code") String code,
        String username
) {
}
