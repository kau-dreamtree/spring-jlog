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
    private Balance balance;
    @JsonProperty("logs")
    List<LogsDto> logsDtos;
}
