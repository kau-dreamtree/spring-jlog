package dreamtree.jlog.dto;

import dreamtree.jlog.domain.Room;

public record OutpayResponse(
        long amount,
        String username
) {

    public static OutpayResponse from(Room room) {
        return new OutpayResponse(room.outpayAmount(), room.outpayer());
    }
}
