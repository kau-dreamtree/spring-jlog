package shop.dreamtree.jlog.domain;

import static shop.dreamtree.jlog.exception.JLogErrorCode.INVALID_EXPENSE_FORMAT;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import shop.dreamtree.jlog.exception.JLogException;

@Entity
public class Log extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Room room;

    @ManyToOne(optional = false)
    private Member member;

    @Column(nullable = false)
    private long expense;

    private String memo;

    protected Log() {
    }

    Log(
            Long id,
            Room room,
            Member member,
            long expense,
            String memo
    ) {
        this.id = id;
        this.room = room;
        this.member = member;
        this.expense = expense;
        this.memo = memo;
    }

    public static LogBuilder builder(Room room, Member member) {
        return new LogBuilder(room, member);
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

    public Room room() {
        return room;
    }

    public Member member() {
        return member;
    }

    public long expense() {
        return expense;
    }

    public String memo() {
        return memo;
    }
}
