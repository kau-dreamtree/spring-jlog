package shop.dreamtree.jlog.domain.log;

import java.time.LocalDateTime;

public class LogBuilder {

    private Long id;
    private String username;
    private long expense;
    private String code;
    private String memo;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

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

    public LogBuilder createdDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public LogBuilder modifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public Log build() {
        return new Log(id, username, expense, code, memo, createdDate, modifiedDate);
    }
}
