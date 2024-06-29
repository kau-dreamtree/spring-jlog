package dreamtree.jlog.domain;

import static dreamtree.jlog.domain.MemberFixture.memberFixture;

public class RoomFixture {

    public static Room roomFixture() {
        return roomFixture(1L);
    }

    public static Room roomFixture(long id) {
        return roomFixture(id, "room" + id);
    }

    public static Room roomFixture(long id, String code) {
        return roomFixture(id, code, id, id + 1L);
    }

    public static Room roomFixture(long id, String code, long member1Id, long member2Id) {
        var members = new Members(memberFixture(member1Id), memberFixture(member2Id));
        return new Room(id, code, members);
    }
}
