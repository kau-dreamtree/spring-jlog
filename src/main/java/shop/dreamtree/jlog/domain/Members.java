package shop.dreamtree.jlog.domain;

import static shop.dreamtree.jlog.exception.JLogErrorCode.ROOM_FULL;
import static shop.dreamtree.jlog.exception.JLogErrorCode.UNAUTHORIZED_USERNAME;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import shop.dreamtree.jlog.exception.JLogException;

@Embeddable
public class Members {

    private static final Logger log = LoggerFactory.getLogger(Members.class);

    @OneToOne(optional = false)
    @AttributeOverride(name = "id", column = @Column(name = "member1_id"))
    private Member member1;

    @OneToOne
    @AttributeOverride(name = "id", column = @Column(name = "member2_id"))
    private Member member2;

    protected Members() {}

    public Members(Member member) {
        this.member1 = member;
    }

    public void join(Member member) {
        if (cannotJoin(member)) {
            throw new JLogException(ROOM_FULL);
        }
        joinIfEmpty(member);
        log.info("%s member %s joined".formatted(LocalDateTime.now(), member.name()));
    }

    public boolean cannotJoin(Member member) {
        return hasNoRoom() && doesNotContain(member);
    }

    private void joinIfEmpty(Member member) {
        if (hasRoom() && doesNotContain(member)) {
            member2 = member;
        }
    }

    public boolean hasNoRoom() {
        return member2 != null;
    }

    public boolean hasRoom() {
        return !hasNoRoom();
    }

    public boolean contains(Member member) {
        return Objects.equals(member, member1) || Objects.equals(member, member2);
    }

    public boolean doesNotContain(Member member) {
        return !contains(member);
    }

    public void add(Log log) {
        Member member = findMember(log.member());
        member.addExpense(log.expense());
    }

    public Member findMember(Member member) {
        if (Objects.equals(member1, member)) {
            return member1;
        }
        if (Objects.equals(member2, member)) {
            return member2;
        }
        throw new JLogException(UNAUTHORIZED_USERNAME);
    }

    public Member findMember(String username) {
        if (member1.hasNameOf(username)) {
            return member1;
        }
        if (hasNoRoom() && member2.hasNameOf(username)) {
            return member2;
        }
        throw new JLogException(UNAUTHORIZED_USERNAME);
    }

    public String outpayer() {
        if (member1.expense() < member2.expense()) {
            return member2.name();
        }
        if (member1.expense() > member2.expense()) {
            return member1.name();
        }
        return "";
    }

    public long outpayAmount() {
        return Math.abs(member1.expense() - member2.expense());
    }
}
