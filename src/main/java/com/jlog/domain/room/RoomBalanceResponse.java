package com.jlog.domain.room;

public record RoomBalanceResponse(
        long outpayAmount,
        String outpayer
) {
    public static RoomBalanceResponse from(Room room) {
        return new RoomBalanceResponse(room.outpayAmount(), room.outpayer());
    }
}
