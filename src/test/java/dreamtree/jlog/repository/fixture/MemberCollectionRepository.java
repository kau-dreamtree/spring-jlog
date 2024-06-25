package dreamtree.jlog.repository.fixture;

import java.util.ArrayList;
import java.util.List;

import dreamtree.jlog.domain.Member;
import dreamtree.jlog.repository.MemberRepository;

public class MemberCollectionRepository implements MemberRepository {

    private final List<Member> members;

    public MemberCollectionRepository() {
        members = new ArrayList<>();
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
