package dreamtree.jlog.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String name;

    @Column(nullable = false)
    private long expense;

    protected Member() {}

    public Member(String name) {
        this.name = name;
        this.expense = 0;
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
        return name;
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
}
