package shop.dreamtree.jlog.dto;

import java.util.List;

import shop.dreamtree.jlog.domain.log.Balance;

public record LogResponse(
        Balance balance,
        List<LogDto> logs
) {
}
