package shop.dreamtree.jlog.domain.log;

public class LogBuilder {

    private Long id;
    private String username;
    private long expense;
    private String code;
    private String memo;

    public LogBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public LogBuilder username(String username) {
        this.username = username;
        return this;
    }

    public LogBuilder expense(long expense) {
        this.expense = expense;
        return this;
    }

    public LogBuilder code(String code) {
        this.code = code;
        return this;
    }

    public LogBuilder memo(String memo) {
        this.memo = memo;
        return this;
    }

    public Log build() {
        return new Log(id, username, expense, code, memo);
    }
}
