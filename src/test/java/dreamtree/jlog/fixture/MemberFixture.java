package dreamtree.jlog.fixture;

import dreamtree.jlog.domain.Member;

public class MemberFixture {

    public static Member memberFixture(long id) {
        return memberFixture(id, 0);
    }

    public static Member memberFixture(long id, long expense) {
        return new Member(id, "member" + id, expense);
    }
}
