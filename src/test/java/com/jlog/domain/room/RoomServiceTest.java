package com.jlog.domain.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.jlog.domain.member.FakeMemberRepository;
import com.jlog.domain.member.Member;
import com.jlog.domain.member.MemberRepository;
import com.jlog.domain.member.Members;
import com.jlog.exception.JLogException;

class RoomServiceTest {

    private RoomService sut;

    private RoomRepository roomRepository;
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new FakeMemberRepository();
        roomRepository = new FakeRoomRepository();
        sut = new RoomService(roomRepository, memberRepository);
    }

    @Nested
    class Create {

        @Test
        @DisplayName("success")
        void create_success() {
            var member = new Member("member1");
            var request = new RoomCreateRequest(member.getName());

            var actual = sut.create(request);

            assertThat(actual).isNotNull();
        }
    }

    @Nested
    class Join {

        @Test
        void join_new_member_success() {
            var member = memberRepository.save(new Member("member1"));
            var room = roomRepository.save(new Room("roomcode", member));

            var request = new RoomJoinRequest("roomcode", "member2");

            sut.join(request);

            var savedRoom = roomRepository.fetchByCode("roomcode");

            assertThat(savedRoom).isEqualTo(room);
            assertThat(savedRoom.existsByName(member.getName())).isTrue();
            assertThat(savedRoom.existsByName(request.username())).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"member1", "member2"})
        void join_existing_member_success(String existingUserName) {
            var member1 = memberRepository.save(new Member("member1"));
            var member2 = memberRepository.save(new Member("member2"));
            var members = new Members(member1, member2);
            var room = roomRepository.save(new Room("roomcode", members));

            var request = new RoomJoinRequest("roomcode", existingUserName);

            sut.join(request);

            var savedRoom = roomRepository.fetchByCode("roomcode");

            assertThat(savedRoom).isEqualTo(room);
            assertThat(savedRoom.existsByName(member1.getName())).isTrue();
            assertThat(savedRoom.existsByName(member2.getName())).isTrue();
        }

        @Test
        void join_full_room_fail() {
            var member1 = memberRepository.save(new Member("member1"));
            var member2 = memberRepository.save(new Member("member2"));
            var members = new Members(member1, member2);
            roomRepository.save(new Room("roomcode", members));

            var request = new RoomJoinRequest("roomcode", "member3");

            assertThatExceptionOfType(JLogException.class)
                    .isThrownBy(() -> sut.join(request))
                    .withMessage("The room is full.");
        }
    }

    @Nested
    class GetBalance {

        @Test
        void getBalance_success() {
            // given
            var member1 = memberRepository.save(new Member(null, "member1", 1000L));
            var member2 = memberRepository.save(new Member(null, "member2", 1500L));
            var members = new Members(member1, member2);
            var room = roomRepository.save(new Room("roomcode", members));
            var request = new RoomRequestV1(room.getCode(), member1.getName());

            // when
            Room actual = sut.get(request);

            // then
            assertThat(actual).isNotNull();
            assertThat(actual.outpayAmount()).isEqualTo(500L);
            assertThat(actual.outpayer()).isEqualTo("member2");
        }
    }
}
