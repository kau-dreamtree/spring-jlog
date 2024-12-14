package com.jlog.domain.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import static com.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.jlog.domain.member.FakeMemberRepository;
import com.jlog.domain.member.Member;
import com.jlog.domain.member.MemberRepository;
import com.jlog.domain.room.FakeRoomRepository;
import com.jlog.domain.room.Room;
import com.jlog.domain.room.RoomRepository;
import com.jlog.exception.JLogException;

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
        sut = new LogService(logRepository, roomRepository, memberRepository);
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
        long id = sut.create(request);

        // then
        Log actual = logRepository.fetchById(id);
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
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

        var request1 = new LogRequest(null, roomCode, username, 1000L, "Memo1");
        var request2 = new LogRequest(null, roomCode, username, 2000L, "Memo2");

        sut.create(request1);
        sut.create(request2);

        // when
        LogsWithOutpayResponse actual = sut.findAll(roomCode, username);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.logs()).hasSize(2);
        assertThat(actual.outpayAmount()).isEqualTo(3000L);
        assertThat(actual.outpayer()).isEqualTo(username);
    }

    @Test
    void findByRoom() {
        // given
        String roomCode = "ROOM1234";
        String username = "john";

        Member member = memberRepository.save(new Member(username));
        Room room = roomRepository.save(new Room(roomCode, member));

        var request1 = new LogRequest(null, roomCode, username, 1000L, "Memo1");
        var request2 = new LogRequest(null, roomCode, username, 2000L, "Memo2");

        sut.create(request1);
        sut.create(request2);

        var pageRequest = PageRequest.of(0, 10);

        // when
        Slice<LogResponseV1> response = sut.findByRoom(roomCode, username, pageRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).isNotEmpty();
        assertThat(response.getContent().size()).isEqualTo(2);
    }

    @Test
    void update() {
        // given
        String roomCode = "ROOM1234";
        String username = "john";

        Member member = memberRepository.save(new Member(username));
        Room room = roomRepository.save(new Room(roomCode, member));

        var createRequest = new LogRequest(null, roomCode, username, 1000L, "Memo1");

        long id = sut.create(createRequest);

        var updateRequest = new LogRequest(id, roomCode, username, 2000L, "Memo2");

        // when
        sut.update(updateRequest);
        Log actual = logRepository.fetchById(id);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(id);
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

        long id = sut.create(createRequest);

        var updateRequest = new LogRequest(id, roomCode, username2, 2000L, "Memo2");

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

        long id = sut.create(createRequest);

        var deleteRequest = new LogRequest(id, roomCode, username1, 1000L, "Memo1");

        // when
        sut.delete(deleteRequest);

        // then
        assertThat(logRepository.findById(id)).isEmpty();
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

        long id = sut.create(createRequest);

        var deleteRequest = new LogRequest(id, roomCode, username2, 1000L, "Memo1");

        // when & then
        assertThatExceptionOfType(JLogException.class)
                .isThrownBy(() -> sut.delete(deleteRequest))
                .withMessage(UNAUTHORIZED_MEMBER.message());
    }
}
