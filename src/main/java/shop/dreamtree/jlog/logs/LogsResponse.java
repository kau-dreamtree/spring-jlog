package shop.dreamtree.jlog.logs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LogsResponse {
    @JsonProperty("room_code")
    private String roomUid;
    long balance;
    String username;
    @JsonProperty("logs")
    List<LogsDto> logsDtos;
}
