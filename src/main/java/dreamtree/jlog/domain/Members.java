package dreamtree.jlog.domain;

import static dreamtree.jlog.exception.JLogErrorCode.MEMBER_NOT_EXISTS;
import static dreamtree.jlog.exception.JLogErrorCode.ROOM_FULL;
import static dreamtree.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER_NAME;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;

import dreamtree.jlog.exception.JLogException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class Members {

    @Transient
    private final Set<Member> members = new HashSet<>();

    @OneToOne(optional = false)
    @AttributeOverride(name = "id", column = @Column(name = "member1_id"))
    private Member member1;

    @OneToOne
    @AttributeOverride(name = "id", column = @Column(name = "member2_id"))
    private Member member2;

    public Members(Member member) {
        this(member, null);
    }

    @PostLoad
    public void postLoad() {
        members.add(member1);
        if (member2 != null) {
            members.add(member2);
        }
    }

    public void join(Member member) {
        if (cannotJoin(member)) {
            throw new JLogException(ROOM_FULL);
        }
        joinIfEmpty(member);
        log.info("{} member {} joined", LocalDateTime.now(), member.getName());
    }

    public boolean cannotJoin(Member member) {
        return isFull() && doesNotContain(member);
    }

    private void joinIfEmpty(Member member) {
        if (hasRoom() && doesNotContain(member)) {
            member2 = member;
        }
    }

    public boolean isFull() {
        return member1 != null && member2 != null;
    }

    public boolean hasRoom() {
        return !isFull();
    }

    public boolean contains(Member member) {
        return members.contains(member);
    }

    public boolean doesNotContain(Member member) {
        return !contains(member);
    }

    public void add(Log log) {
        Member member = getMember(log.getMember());
        member.addExpense(log.getExpense());
    }

    public Member getMember(Member member) {
        return members.stream()
                .filter(m -> Objects.equals(m, member))
                .findFirst()
                .orElseThrow(() -> new JLogException(MEMBER_NOT_EXISTS));
    }

    public Member getMemberByName(String name) {
        return members.stream()
                .filter(member -> Objects.equals(member.getName(), name))
                .findFirst()
                .orElseThrow(() -> new JLogException(UNAUTHORIZED_MEMBER_NAME));
    }

    public String outpayer() {
        if (outpayAmount() == 0) {
            return "";
        }
        return members.stream()
                .max(Comparator.comparing(Member::getExpense))
                .map(Member::getName)
                .orElse("");
    }

    public long outpayAmount() {
        if (Objects.isNull(member2)) {
            return member1.getExpense();
        }
        long diff = Math.subtractExact(member1.getExpense(), member2.getExpense());
        return Math.abs(diff);
    }
}
