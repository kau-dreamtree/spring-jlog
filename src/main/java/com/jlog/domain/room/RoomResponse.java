package com.jlog.domain.room;

import java.util.ArrayList;
import java.util.List;

import com.jlog.domain.member.MemberDto;
import com.jlog.domain.member.MemberResponse;

public record RoomResponse(
        String roomCode,
        List<MemberDto> members,
        String outpayer,
        long outpayAmount
) {
    public static RoomResponse from(Room room) {
        var members = new ArrayList<MemberDto>();
        room.getMembers()
                .stream()
                .map(MemberResponse::from)
                .forEach(members::add);
        return new RoomResponse(room.getCode(), members, room.outpayer(), room.outpayAmount());
    }
}
