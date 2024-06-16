package shop.dreamtree.jlog.domain.log;

import static shop.dreamtree.jlog.exception.JLogErrorCode.INVALID_EXPENSE_FORMAT;

import java.time.LocalDateTime;

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
    private String code;

    @Column(nullable = false)
    private long expense;

    private String memo;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    protected Log() {
    }

    public Log(Long id,
            String username,
            long expense,
            String code,
            String memo,
            LocalDateTime createdDate,
            LocalDateTime modifiedDate
    ) {
        this.id = id;
        this.username = username;
        this.code = code;
        this.expense = expense;
        this.memo = memo;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
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

    public Long id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String code() {
        return code;
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
