package shop.dreamtree.jlog.room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoomDto {
    private String uid;
    private String firstUsername;
    private String secondUsername;

    @Builder
    public RoomDto(String uid, String firstUsername, String secondUsername) {
        this.uid = uid;
        this.firstUsername = firstUsername;
        this.secondUsername = secondUsername;
    }

    public Room toEntity() {
        return Room.builder()
                .uid(uid)
                .firstUsername(firstUsername)
                .secondUsername(secondUsername)
                .build();
    }
}
