package dreamtree.jlog.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreamtree.jlog.domain.Member;
import dreamtree.jlog.domain.Room;
import dreamtree.jlog.dto.RoomCreateRequest;
import dreamtree.jlog.dto.RoomJoinRequest;
import dreamtree.jlog.exception.JLogErrorCode;
import dreamtree.jlog.exception.JLogException;
import dreamtree.jlog.repository.MemberRepository;
import dreamtree.jlog.repository.RoomRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {

    private static final int ROOM_CODE_LENGTH = 8;

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String create(RoomCreateRequest request) {
        String code = RandomStringUtils.secure().nextAlphanumeric(ROOM_CODE_LENGTH);
        Member savedMember = memberRepository.save(new Member(request.username()));
        Room savedRoom = roomRepository.save(new Room(code, savedMember));
        return savedRoom.getCode();
    }

    @Transactional
    public void join(RoomJoinRequest request) {
        Room room = roomRepository.fetchByCode(request.code());
        if (room.existsByName(request.username())) {
            return;
        }
        if (room.isFull()) {
            throw new JLogException(JLogErrorCode.ROOM_FULL);
        }
        Member member = memberRepository.save(new Member(request.username()));
        room.join(member);
        roomRepository.save(room);
    }
}
