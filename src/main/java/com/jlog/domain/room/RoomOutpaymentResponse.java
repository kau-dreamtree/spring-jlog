package com.jlog.domain.room;

public record RoomOutpaymentResponse(
        long outpayAmount,
        String outpayer
) {
    public static RoomOutpaymentResponse from(Room room) {
        return new RoomOutpaymentResponse(room.outpayAmount(), room.outpayer());
    }
}
