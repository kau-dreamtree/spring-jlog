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
    public String create(RoomCreateRequest request) {
        String code = RandomStringUtils.secure().nextAlphanumeric(ROOM_CODE_LENGTH);
        Member savedMember = memberRepository.save(new Member(request.username()));
        Room savedRoom = roomRepository.save(new Room(code, savedMember));
        log.info("Room created: {}", savedRoom);
        return savedRoom.getCode();
    }

    @Transactional
    public void join(RoomJoinRequest request) {
        Room room = roomRepository.fetchByCode(request.code());
        if (room.existsByName(request.username())) {
            return;
        }
        requireNotFull(room);
        Member member = memberRepository.save(new Member(request.username()));
        room.join(member);
        roomRepository.save(room);
    }

    private void requireNotFull(Room room) {
        if (room.isFull()) {
            throw new JLogException(ROOM_FULL);
        }
    }

    @Transactional(readOnly = true)
    public RoomBalanceResponse getBalance(String roomCode, String username) {
        Room room = roomRepository.fetchByCode(roomCode);
        requireExists(room, username);
        return RoomBalanceResponse.from(room);
    }

    private void requireExists(Room room, String username) {
        if (!room.existsByName(username)) {
            throw new JLogException(UNAUTHORIZED_MEMBER_NAME);
        }
    }
}
