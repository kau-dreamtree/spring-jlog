package com.jlog.domain.member;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;

import com.jlog.exception.JLogErrorCode;
import com.jlog.exception.JLogException;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Members {

    @OneToOne(optional = false)
    @AttributeOverride(name = "id", column = @Column(name = "member1_id"))
    @EqualsAndHashCode.Include
    private Member member1;

    @OneToOne
    @AttributeOverride(name = "id", column = @Column(name = "member2_id"))
    @EqualsAndHashCode.Include
    private Member member2;

    public Members(Member member) {
        this(member, null);
    }

    public Members(Member member1, Member member2) {
        this.member1 = member1;
        this.member2 = member2;
        if (Objects.nonNull(member2) && Objects.equals(member1.getName(), member2.getName())) {
            throw new JLogException(JLogErrorCode.DUPLICATE_NAME);
        }
    }

    public boolean exists(Member member) {
        return member.equals(member1) || member.equals(member2);
    }

    public boolean isFull() {
        return Objects.nonNull(member1) && Objects.nonNull(member2);
    }

    public Member getMemberByName(String name) {
        return stream()
                .filter(member -> Objects.equals(member.getName(), name))
                .findFirst()
                .orElse(null);
    }

    public boolean existsByName(String name) {
        return stream().anyMatch(m -> m.matchName(name));
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

    public Stream<Member> stream() {
        return Stream.of(member1, member2).filter(Objects::nonNull);
    }
}
