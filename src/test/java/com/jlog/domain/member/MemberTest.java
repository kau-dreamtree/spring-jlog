package com.jlog.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("Throws exception if the given expense is greater than member's expense.")
    @Test
    void subtractExpense() {
        Member member = new Member();
        member.addExpense(5000L);

        ThrowingCallable subtractExpense = () -> member.subtractExpense(5001L);

        assertThatIllegalStateException().isThrownBy(subtractExpense);
    }

    @DisplayName("Only ID field is used for identity.")
    @Test
    void testEquals() {
        var member1 = new Member(1L, "zeus", 0L);
        var member2 = new Member(1L, "lizzy", 1000L);
        var member3 = new Member(2L, "merry", 2000L);

        assertThat(member1).isEqualTo(member2);
        assertThat(member2).isNotEqualTo(member3);
    }
}
