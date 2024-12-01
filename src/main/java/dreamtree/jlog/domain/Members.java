package dreamtree.jlog.domain;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;

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

    @OneToOne(optional = false)
    @AttributeOverride(name = "id", column = @Column(name = "member1_id"))
    private Member member1;

    @OneToOne
    @AttributeOverride(name = "id", column = @Column(name = "member2_id"))
    private Member member2;

    public Members(Member member) {
        this(member, null);
    }

    public boolean exists(Member member) {
        return member1.equals(member) || member2.equals(member);
    }

    public boolean isFull() {
        return member1 != null && member2 != null;
    }

    public Member getMemberByName(String name) {
        return stream()
                .filter(member -> Objects.equals(member.getName(), name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean existsByName(String name) {
        return stream().anyMatch(m -> Objects.equals(m.getName(), name));
    }

    public void addLog(Log log) {
        Member member = stream()
                .filter(m -> Objects.equals(m, log.getMember()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        member.addExpense(log.getExpense());
    }

    public String outpayer() {
        if (outpayAmount() == 0) {
            return "";
        }
        return stream()
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

    private Stream<Member> stream() {
        return Stream.of(member1, member2);
    }
}
