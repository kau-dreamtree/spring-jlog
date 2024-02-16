package shop.dreamtree.jlog.posts;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostsDto {
    private Long id;
    private String username;
    private long expense;
    private String roomUid;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Posts toEntity() {
        return Posts.builder()
                .username(username)
                .expense(expense)
                .roomUid(roomUid)
                .build();
    }
}
