package shop.dreamtree.jlog.room;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoomDto {
    @JsonProperty("room_code")
    private String uid;
    private String username;

    @Builder
    public RoomDto(String uid, String username) {
        this.uid = uid;
        this.username = username;
    }
}
