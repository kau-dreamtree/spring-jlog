package shop.dreamtree.jlog.logs;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LogsDto {
    @JsonProperty("room_code")
    private String roomUid;
    private String username;
    @JsonProperty("amount")
    private long expense;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonProperty("created_at")
    private LocalDateTime createdDate;
    @JsonProperty("modified_at")
    private LocalDateTime modifiedDate;

    public Logs toEntity() {
        return Logs.builder()
                .username(username)
                .expense(expense)
                .roomUid(roomUid)
                .build();
    }

    public static LogsDto from(Logs logs) {
        return new LogsDto(
                logs.getRoomUid(),
                logs.getUsername(),
                logs.getExpense(),
                logs.getCreatedDate(),
                logs.getModifiedDate()
        );
    }
}
