package dreamtree.jlog.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8, nullable = false, unique = true)
    private String code;

    @Embedded
    private Members members;

    protected Room() {}

    public Room(String code, Member member) {
        // todo: Validate username not empty
        this.code = code;
        this.members = new Members(member);
    }

    public void join(Member member) {
        members.join(Objects.requireNonNull(member));
    }

    public Member requireMemberExistsByName(String name) {
        return members.getMemberByName(name);
    }

    public void addLog(Log log) {
        members.add(log);
    }

    public boolean isFull() {
        return members.isFull();
    }

    public boolean hasRoom() {
        return members.hasRoom();
    }

    public String outpayer() {
        return members.outpayer();
    }

    public long outpayAmount() {
        return members.outpayAmount();
    }

    public Long id() {
        return id;
    }

    public String code() {
        return code;
    }

    public Members members() {
        return members;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof Room other
                && Objects.equals(id, other.id)
                && Objects.equals(code, other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}
