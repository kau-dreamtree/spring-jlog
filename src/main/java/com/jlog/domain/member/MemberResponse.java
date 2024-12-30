package com.jlog.domain.member;

public record MemberResponse(
        String name,
        long expense
) implements MemberDto {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getName(), member.getExpense());
    }
}
