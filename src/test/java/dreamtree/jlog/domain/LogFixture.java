package dreamtree.jlog.domain;

import static dreamtree.jlog.domain.MemberFixture.memberFixture;
import static dreamtree.jlog.domain.RoomFixture.roomFixture;

public class LogFixture {

    public static Log logFixture() {
        return logFixture(1L);
    }

    public static Log logFixture(long id) {
        return logFixture(id, 0, null);
    }

    public static Log logFixture(long id, long expense) {
        return logFixture(id, expense, null);
    }

    public static Log logFixture(long id, long expense, String memo) {
        var member = memberFixture(id);
        var room = roomFixture(id);
        return new Log(id, room, member, expense, memo);
    }
}
