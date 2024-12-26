package com.jlog.domain.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static com.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jlog.domain.member.FakeMemberRepository;
import com.jlog.domain.member.Member;
import com.jlog.domain.member.MemberRepository;
import com.jlog.domain.room.FakeRoomRepository;
import com.jlog.domain.room.Room;
import com.jlog.domain.room.RoomRepository;
import com.jlog.exception.JLogException;

@SuppressWarnings("removal")
class LogServiceTest {

    private LogService sut;

    private LogRepository logRepository;
    private RoomRepository roomRepository;
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new FakeMemberRepository();
        roomRepository = new FakeRoomRepository();
        logRepository = new FakeLogRepository();
        sut = new LogService(logRepository, roomRepository);
    }

    @Test
    void create() {
        // given
        String roomCode = "ROOM1234";
        String username = "john";

        var member = memberRepository.save(new Member(username));
        var room = roomRepository.save(new Room(roomCode, member));

        long expense = 1000L;
        String memo = "test memo";

        var request = new LogRequest(null, roomCode, username, expense, memo);

        // when
        var actual = sut.create(request);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual.getRoom()).isEqualTo(room);
        assertThat(actual.getMember()).isEqualTo(member);
        assertThat(actual.getExpense()).isEqualTo(expense);
        assertThat(actual.getMemo()).isEqualTo(memo);
    }

    @Test
    void findAll() {
        // given
        String roomCode = "ROOM1234";
        String username = "john";

        Member member = memberRepository.save(new Member(username));
        roomRepository.save(new Room(roomCode, member));

        var createRequest1 = new LogRequest(null, roomCode, username, 1000L, "Memo1");
        var createRequest2 = new LogRequest(null, roomCode, username, 2000L, "Memo2");

        sut.create(createRequest1);
        sut.create(createRequest2);

        var findRequest = new LogRequest(null, roomCode, username, null, null);

        // when
        LogsWithOutpayResponse actual = sut.findAll(findRequest);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.logs()).hasSize(2);
        assertThat(actual.outpayAmount()).isEqualTo(3000L);
        assertThat(actual.outpayer()).isEqualTo(username);
    }

    @Test
    void findLogsByRoomAfterId() {
        // given
        String roomCode = "ROOM1234";
        String username = "john";

        Member member = memberRepository.save(new Member(username));
        roomRepository.save(new Room(roomCode, member));

        var request1 = LogRequestV1.of(roomCode, username, 1000L, "Memo1");
        var request2 = LogRequestV1.of(roomCode, username, 2000L, "Memo2");

        sut.create(request1);
        sut.create(request2);

        var logRequest = LogRequestV1.of(0L, roomCode, username, null, null);

        // when
        List<Log> response = sut.findLogsByRoomAfterId(logRequest);

        // then
        assertThat(response).hasSize(2);
    }

    @Test
    void update() {
        // given
        String roomCode = "ROOM1234";
        String username = "john";

        Member member = memberRepository.save(new Member(username));
        Room room = roomRepository.save(new Room(roomCode, member));

        var createRequest = new LogRequest(null, roomCode, username, 1000L, "Memo1");

        var actual = sut.create(createRequest);

        var updateRequest = new LogRequest(actual.getId(), roomCode, username, 2000L, "Memo2");

        // when
        sut.update(updateRequest);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getRoom()).isEqualTo(room);
        assertThat(actual.getMember()).isEqualTo(member);
        assertThat(actual.getExpense()).isEqualTo(2000L);
        assertThat(actual.getMemo()).isEqualTo("Memo2");
    }

    @Test
    void update_unauthorized() {
        // given
        String roomCode = "ROOM1234";
        String username1 = "john";
        String username2 = "doe";

        Member member1 = memberRepository.save(new Member(username1));
        memberRepository.save(new Member(username2));
        roomRepository.save(new Room(roomCode, member1));

        var createRequest = new LogRequest(null, roomCode, username1, 1000L, "Memo1");

        var actual = sut.create(createRequest);

        var updateRequest = new LogRequest(actual.getId(), roomCode, username2, 2000L, "Memo2");

        // when & then
        assertThatExceptionOfType(JLogException.class)
                .isThrownBy(() -> sut.update(updateRequest))
                .withMessage(UNAUTHORIZED_MEMBER.message());
    }

    @Test
    void delete() {
        // given
        String roomCode = "ROOM1234";
        String username1 = "john";
        String username2 = "doe";

        Member member1 = memberRepository.save(new Member(username1));
        memberRepository.save(new Member(username2));
        roomRepository.save(new Room(roomCode, member1));

        var createRequest = new LogRequest(null, roomCode, username1, 1000L, "Memo1");

        var actual = sut.create(createRequest);

        var deleteRequest = new LogRequest(actual.getId(), roomCode, username1, 1000L, "Memo1");

        // when
        sut.delete(deleteRequest);

        // then
        assertThat(logRepository.findById(actual.getId())).isEmpty();
    }

    @Test
    void delete_unauthorized() {
        // given
        String roomCode = "ROOM1234";
        String username1 = "john";
        String username2 = "doe";

        Member member1 = memberRepository.save(new Member(username1));
        memberRepository.save(new Member(username2));
        roomRepository.save(new Room(roomCode, member1));

        var createRequest = new LogRequest(null, roomCode, username1, 1000L, "Memo1");

        var actual = sut.create(createRequest);

        var deleteRequest = new LogRequest(actual.getId(), roomCode, username2, 1000L, "Memo1");

        // when & then
        assertThatExceptionOfType(JLogException.class)
                .isThrownBy(() -> sut.delete(deleteRequest))
                .withMessage(UNAUTHORIZED_MEMBER.message());
    }
}
