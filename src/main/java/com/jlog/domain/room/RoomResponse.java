package com.jlog.domain.room;

import java.util.List;

import com.jlog.domain.member.MemberDto;
import com.jlog.domain.member.MemberResponse;

public record RoomResponse(
        String roomCode,
        List<MemberDto> members,
        String outpayer,
        long outpayAmount
) {

    public RoomResponse(Room room) {
        this(room.getCode(), getMembers(room), room.outpayer(), room.outpayAmount());
    }

    private static List<MemberDto> getMembers(Room room) {
        return room.getMembers()
                .stream()
                .map(MemberResponse::new)
                .map(response -> (MemberDto) response)
                .toList();
    }
}
