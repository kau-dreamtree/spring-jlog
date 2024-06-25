package dreamtree.jlog.domain;

import static dreamtree.jlog.exception.JLogErrorCode.INVALID_EXPENSE_FORMAT;
import static dreamtree.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import dreamtree.jlog.exception.JLogException;

@Entity
@Table(indexes = {
        @Index(name = "log_created_date_idx", columnList = "created_date"),
        @Index(name = "log_modified_date_idx", columnList = "modified_date"),
})
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

    public void requireMemberEquals(Member member) {
        if (Objects.equals(this.member, member)) {
            return;
        }
        throw new JLogException(UNAUTHORIZED_MEMBER);
    }

    public void updateExpense(long expense) {
        if (expense < 0L) {
            throw new JLogException(INVALID_EXPENSE_FORMAT);
        }
        this.expense = expense;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof Log other
                && Objects.equals(id, other.id)
                && Objects.equals(room, other.room)
                && Objects.equals(member, other.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, member);
    }
}
