package dreamtree.jlog.repository;

import java.util.ArrayList;
import java.util.List;

import dreamtree.jlog.domain.Member;

public class FakeMemberRepository implements MemberRepository {

    private final List<Member> members;

    public FakeMemberRepository() {
        this(new ArrayList<>());
    }

    public FakeMemberRepository(List<Member> members) {
        this.members = members;
    }

    @Override
    public Member save(Member member) {
        members.add(member);
        return member;
    }
}
