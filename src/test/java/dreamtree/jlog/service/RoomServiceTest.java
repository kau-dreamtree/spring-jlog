package dreamtree.jlog.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import dreamtree.jlog.domain.Member;
import dreamtree.jlog.dto.RoomCreateRequest;
import dreamtree.jlog.repository.FakeMemberRepository;
import dreamtree.jlog.repository.FakeRoomRepository;
import dreamtree.jlog.repository.RoomRepository;
import dreamtree.jlog.service.finder.RoomFinder;

class RoomServiceTest {

    private RoomService roomService;

    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository = new FakeRoomRepository();
        roomService = new RoomService(
                new RoomFinder(roomRepository),
                roomRepository,
                new FakeMemberRepository()
        );
    }

    @Nested
    class Create {

        @Test
        @DisplayName("Create a room.")
        void create() {
            var member = new Member("member1");
            var request = new RoomCreateRequest(member.name());

            var code = roomService.create(request);
            var saved = roomRepository.findByCode(code);

            assertThat(saved).isNotNull();
        }
    }
}
