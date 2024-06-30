package dreamtree.jlog.fixture;

import static dreamtree.jlog.fixture.MemberFixture.memberFixture;
import static dreamtree.jlog.fixture.RoomFixture.roomFixture;

import dreamtree.jlog.domain.Log;

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
