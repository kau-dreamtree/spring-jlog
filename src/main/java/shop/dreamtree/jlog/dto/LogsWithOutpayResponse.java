package shop.dreamtree.jlog.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogsWithOutpayResponse(
        @JsonProperty("balance") OutpayDto outpay,
        List<LogResponse> logs
) {
}
