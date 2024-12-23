package com.jlog.domain.log;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.jlog.domain.member.Member;
import com.jlog.domain.member.MemberRepository;
import com.jlog.domain.room.Room;
import com.jlog.domain.room.RoomRepository;

@DataJpaTest
class LogRepositoryTest {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findLogsByRoomAfterId() {
        // given
        Member member = memberRepository.save(new Member("Lizzy"));
        Room room = roomRepository.save(new Room("roomCode", member));

        Log log1 = Log.builder().room(room).member(member).expense(1000L).build();
        Log log2 = Log.builder().room(room).member(member).expense(2000L).build();
        Log log3 = Log.builder().room(room).member(member).expense(3000L).build();
        logRepository.save(log1);
        logRepository.save(log2);
        logRepository.save(log3);

        var pageRequest = PageRequest.of(0, 20, Direction.DESC, "createdAt");

        // when
        List<Log> actual = logRepository.findLogsByRoomAfterId(room, 1L, pageRequest);

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getExpense()).isEqualTo(3000L);
        assertThat(actual.get(1).getExpense()).isEqualTo(2000L);
    }
}
