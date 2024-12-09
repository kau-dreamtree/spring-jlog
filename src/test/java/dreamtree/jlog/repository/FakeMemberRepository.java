package dreamtree.jlog.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import dreamtree.jlog.domain.Member;

public class FakeMemberRepository implements MemberRepository {

    private static final AtomicLong counter = new AtomicLong(1);

    private final List<Member> members;

    public FakeMemberRepository() {
        this(new ArrayList<>());
    }

    public FakeMemberRepository(List<Member> members) {
        this.members = members;
    }

    @Override
    public Member save(Member member) {
        long id = counter.incrementAndGet();
        member = new Member(id, member.getName(), member.getExpense());
        members.add(member);
        return member;
    }
}
