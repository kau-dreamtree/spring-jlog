package com.jlog.domain.room;

import static com.jlog.exception.JLogErrorCode.ROOM_FULL;
import static com.jlog.exception.JLogErrorCode.UNAUTHORIZED_MEMBER_NAME;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlog.domain.member.Member;
import com.jlog.domain.member.MemberRepository;
import com.jlog.exception.JLogException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private static final int ROOM_CODE_LENGTH = 8;

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Room create(RoomRequest request) {
        String code = RandomStringUtils.secure().nextAlphanumeric(ROOM_CODE_LENGTH);
        Member member = memberRepository.save(new Member(request.username()));
        Room room = roomRepository.save(new Room(code, member));
        log.info("Room created: {}", room);
        return room;
    }

    @Transactional
    public Room join(RoomRequest request) {
        Room room = roomRepository.fetchByCode(request.roomCode());
        if (room.existsByName(request.username())) {
            return room;
        }
        requireNotFull(room);
        Member member = memberRepository.save(new Member(request.username()));
        room.join(member);
        return roomRepository.save(room);
    }

    private void requireNotFull(Room room) {
        if (room.isFull()) {
            throw new JLogException(ROOM_FULL);
        }
    }

    @Transactional(readOnly = true)
    public Room get(RoomRequest request) {
        Room room = roomRepository.fetchByCode(request.roomCode());
        requireExists(room, request.username());
        return room;
    }

    private void requireExists(Room room, String username) {
        if (!room.existsByName(username)) {
            throw new JLogException(UNAUTHORIZED_MEMBER_NAME);
        }
    }
}
