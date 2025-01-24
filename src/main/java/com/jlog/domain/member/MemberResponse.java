package com.jlog.domain.member;

public record MemberResponse(String name, long expense) implements MemberDto {
    public MemberResponse(Member member) {
        this(member.getName(), member.getExpense());
    }
}
