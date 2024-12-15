package com.jlog.domain.log;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jlog.domain.room.Room;

public record LogsWithOutpayResponse(
        @JsonProperty("balance") OutpayResponse outpayResponse,
        List<LogResponse> logs
) {
    private record OutpayResponse(long amount, String username) {}

    public static LogsWithOutpayResponse of(Room room, List<LogResponse> logs) {
        var outpay = new OutpayResponse(room.outpayAmount(), room.outpayer());
        return new LogsWithOutpayResponse(outpay, logs);
    }

    public long outpayAmount() {
        return outpayResponse.amount;
    }

    public String outpayer() {
        return outpayResponse.username;
    }
}
