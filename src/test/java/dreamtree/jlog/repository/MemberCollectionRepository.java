package dreamtree.jlog.repository;

import java.util.ArrayList;
import java.util.List;

import dreamtree.jlog.domain.Member;

public class MemberCollectionRepository implements MemberRepository {

    private final List<Member> members;

    public MemberCollectionRepository() {
        this(new ArrayList<>());
    }

    public MemberCollectionRepository(List<Member> members) {
        this.members = members;
    }

    @Override
    public Member save(Member member) {
        members.add(member);
        return member;
    }
}
