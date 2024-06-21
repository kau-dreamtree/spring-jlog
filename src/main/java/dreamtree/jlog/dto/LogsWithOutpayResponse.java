package dreamtree.jlog.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogsWithOutpayResponse(
        @JsonProperty("balance") OutpayResponse outpay,
        List<LogResponse> logs
) {
}
