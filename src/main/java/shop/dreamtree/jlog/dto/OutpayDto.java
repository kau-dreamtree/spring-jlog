package shop.dreamtree.jlog.dto;

import shop.dreamtree.jlog.domain.outpay.Outpay;

public record OutpayDto(
        long amount,
        String username
) {

    public static OutpayDto from(Outpay outpay) {
        return new OutpayDto(outpay.amount(), outpay.username());
    }
}
