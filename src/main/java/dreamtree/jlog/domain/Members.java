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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dreamtree.jlog.exception.JLogException;

@Embeddable
public class Members {

    private static final Logger log = LoggerFactory.getLogger(Members.class);

    @Transient
    private final Set<Member> members = new HashSet<>();

    @OneToOne(optional = false)
    @AttributeOverride(name = "id", column = @Column(name = "member1_id"))
    private Member member1;

    @OneToOne
    @AttributeOverride(name = "id", column = @Column(name = "member2_id"))
    private Member member2;

    protected Members() {}

    public Members(Member member) {
        member1 = member;
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
        log.info("%s member %s joined".formatted(LocalDateTime.now(), member.name()));
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
        return member2 != null;
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
        Member member = getMember(log.member());
        member.addExpense(log.expense());
    }

    public Member getMember(Member member) {
        return members.stream()
                .filter(m -> Objects.equals(m, member))
                .findFirst()
                .orElseThrow(() -> new JLogException(MEMBER_NOT_EXISTS));
    }

    public Member getMemberByName(String name) {
        return members.stream()
                .filter(member -> Objects.equals(member.name(), name))
                .findFirst()
                .orElseThrow(() -> new JLogException(UNAUTHORIZED_MEMBER_NAME));
    }

    public String outpayer() {
        if (outpayAmount() == 0) {
            return "";
        }
        return members.stream()
                .max(Comparator.comparing(Member::expense))
                .map(Member::name)
                .orElse("");
    }

    public long outpayAmount() {
        if (member2 == null) {
            return member1.expense();
        }
        return Math.abs(member1.expense() - member2.expense());
    }
}
