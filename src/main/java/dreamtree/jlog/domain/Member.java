package dreamtree.jlog.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Column(nullable = false)
    private long expense;

    protected Member() {}

    public Member(String name) {
        this(null, name, 0);
    }

    public Member(Long id, String name, long expense) {
        this.id = id;
        this.name = new Name(name);
        this.expense = expense;
    }

    public void addExpense(long expense) {
        this.expense += expense;
    }

    public void subtractExpense(long expense) {
        this.expense -= expense;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name.value();
    }

    public long expense() {
        return expense;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof Member other
                && Objects.equals(id, other.id)
                && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expense=" + expense +
                '}';
    }
}
