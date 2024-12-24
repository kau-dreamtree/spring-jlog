package com.jlog.domain.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LogTest {

    @DisplayName("Throws exception if the given expense is negative.")
    @Test
    void updateExpense() {
        // given
        var log = new Log();

        // when
        ThrowingCallable updateExpense = () -> log.updateExpense(-1);

        // then
        assertThatIllegalStateException().isThrownBy(updateExpense);
    }

    @DisplayName("Only ID field is used for identity.")
    @Test
    void testEquals() {
        var log1 = new Log(1L, null, null, 0L, null);
        var log2 = new Log(1L, null, null, 1000L, null);
        var log3 = new Log(2L, null, null, 0L, null);

        assertThat(log1).isEqualTo(log2);
        assertThat(log2).isNotEqualTo(log3);
    }
}
