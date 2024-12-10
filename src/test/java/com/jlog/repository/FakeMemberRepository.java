package com.jlog.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import com.jlog.domain.Member;

public class FakeMemberRepository implements MemberRepository {

    private static final AtomicLong counter = new AtomicLong(1);

    private final Map<Long, Member> members;

    public FakeMemberRepository() {
        members = new HashMap<>();
    }

    @Override
    public Member save(Member member) {
        if (Objects.isNull(member.getId())) {
            long id = counter.getAndIncrement();
            member = new Member(id, member.getName(), member.getExpense());
        }
        members.put(member.getId(), member);
        return member;
    }
}
