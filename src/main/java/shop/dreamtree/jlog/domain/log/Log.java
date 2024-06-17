package shop.dreamtree.jlog.domain.log;

import static shop.dreamtree.jlog.exception.JLogErrorCode.INVALID_EXPENSE_FORMAT;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import shop.dreamtree.jlog.exception.JLogException;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String roomCode;

    @Column(nullable = false)
    private long expense;

    private String memo;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    // todo: @ManyToOne 관계 설정
    // @ManyToOne(optional = false)
    // private Room room

    protected Log() {
    }

    public Log(
            Long id,
            String username,
            long expense,
            String roomCode,
            String memo
    ) {
        this.id = id;
        this.username = username;
        this.roomCode = roomCode;
        this.expense = expense;
        this.memo = memo;
    }

    public static LogBuilder builder() {
        return new LogBuilder();
    }

    public void updateExpense(long expense) {
        if (expense < 0L) {
            throw new JLogException(INVALID_EXPENSE_FORMAT);
        }
        this.expense = expense;
    }

    public void updateMemo(String memo) {
        this.memo = Objects.requireNonNullElse(memo, "");
    }

    public Long id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String roomCode() {
        return roomCode;
    }

    public long expense() {
        return expense;
    }

    public String memo() {
        return memo;
    }

    public LocalDateTime createdDate() {
        return createdDate;
    }

    public LocalDateTime modifiedDate() {
        return modifiedDate;
    }
}
