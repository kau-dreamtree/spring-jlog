package dreamtree.jlog.domain;

public class LogBuilder {

    private final Room room;
    private final Member member;

    private long expense;
    private String memo;

    public LogBuilder(Room room, Member member) {
        this.room = room;
        this.member = member;
    }

    public LogBuilder expense(long expense) {
        this.expense = expense;
        return this;
    }

    public LogBuilder memo(String memo) {
        this.memo = memo;
        return this;
    }

    public Log build() {
        return new Log(null, room, member, expense, memo);
    }
}
