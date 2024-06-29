package dreamtree.jlog.service.finder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static dreamtree.jlog.domain.RoomFixture.roomFixture;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import dreamtree.jlog.domain.Room;
import dreamtree.jlog.exception.JLogException;
import dreamtree.jlog.repository.RoomRepository;
import dreamtree.jlog.repository.fixture.RoomCollectionRepository;

class RoomFinderTest {

    private RoomFinder roomFinder;

    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository = new RoomCollectionRepository();
        roomFinder = new RoomFinder(roomRepository);
    }

    @Test
    void getRoomByCode() {
        var room1 = roomFixture(1L, "room1");
        var room2 = roomFixture(2L, "room2");
        var room3 = roomFixture(3L, "room3");
        save(room1, room2, room3);

        var actual = roomFinder.getRoomByCode("room2");
        assertThat(actual).isEqualTo(room2);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"room0", "abcd", "asdf", "room5"})
    void getRoomByCode_exception(String code) {
        var room1 = roomFixture(1L, "room1");
        var room2 = roomFixture(2L, "room2");
        var room3 = roomFixture(3L, "room3");
        save(room1, room2, room3);

        assertThatThrownBy(() -> roomFinder.getRoomByCode(code))
                .isInstanceOf(JLogException.class);
    }

    private void save(Room... rooms) {
        Arrays.stream(rooms).forEach(room -> roomRepository.save(room));
    }
}
