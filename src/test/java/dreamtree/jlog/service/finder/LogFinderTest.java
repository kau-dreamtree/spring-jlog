package dreamtree.jlog.service.finder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import dreamtree.jlog.domain.Fixtures;
import dreamtree.jlog.domain.Log;
import dreamtree.jlog.exception.JLogException;
import dreamtree.jlog.repository.FakeLogRepository;
import dreamtree.jlog.repository.LogRepository;

class LogFinderTest {

    private LogFinder logFinder;

    private LogRepository logRepository;

    @BeforeEach
    void setUp() {
        logRepository = new FakeLogRepository();
        logFinder = new LogFinder(logRepository);
    }

    @Test
    void getLogById() {
        var log1 = Fixtures.log(1L, 1000L);
        var log2 = Fixtures.log(2L, 2000L);
        var log3 = Fixtures.log(3L, 3000L);
        save(log1, log2, log3);

        var actual = logFinder.getLogById(2L);
        assertThat(actual).isEqualTo(log2);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1L, 0L, 5L})
    void getLogById_exception(Long id) {
        var log1 = Fixtures.log(1L, 1000L);
        var log2 = Fixtures.log(2L, 2000L);
        var log3 = Fixtures.log(3L, 3000L);
        save(log1, log2, log3);

        assertThatThrownBy(() -> logFinder.getLogById(id))
                .isInstanceOf(JLogException.class);
    }

    private void save(Log... logs) {
        Arrays.stream(logs).forEach(log -> logRepository.save(log));
    }
}
