package dreamtree.jlog.domain;

import java.time.LocalDateTime;

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
import lombok.extern.slf4j.Slf4j;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
@Slf4j
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
        log.info("{} member {} requested to join", LocalDateTime.now(), member.getName());
        if (exists(member)) {
            return;
        }
        if (!isFull()) {
            members = new Members(members.getMember1(), member);
        }
        throw new IllegalStateException("Room %s is full".formatted(code));
    }

    private boolean exists(Member member) {
        return members.exists(member);
    }

    public boolean isFull() {
        return members.isFull();
    }

    public Member getMemberByName(String name) {
        return members.getMemberByName(name);
    }

    public boolean existsByName(String name) {
        return members.existsByName(name);
    }

    public void addLog(Log log) {
        members.addLog(log);
    }

    public String outpayer() {
        return members.outpayer();
    }

    public long outpayAmount() {
        return members.outpayAmount();
    }
}
