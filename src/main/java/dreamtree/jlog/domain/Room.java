package dreamtree.jlog.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8, nullable = false, unique = true)
    private String code;

    @Embedded
    private Members members;

    public Room(String code, Member member) {
        this(code, new Members(member));
    }

    public Room(String code, Members members) {
        this(null, code, members);
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
}
