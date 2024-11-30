package dreamtree.jlog.service;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreamtree.jlog.domain.Member;
import dreamtree.jlog.domain.Room;
import dreamtree.jlog.dto.RoomCreateRequest;
import dreamtree.jlog.dto.RoomJoinRequest;
import dreamtree.jlog.exception.JLogException;
import dreamtree.jlog.repository.MemberRepository;
import dreamtree.jlog.repository.RoomRepository;
import dreamtree.jlog.service.finder.RoomFinder;
import dreamtree.jlog.util.Encryptor;

@Service
public class RoomService {

    private static final int ROOM_CODE_LENGTH = 8;

    private final RoomFinder roomFinder;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    public RoomService(RoomFinder roomFinder, RoomRepository roomRepository, MemberRepository memberRepository) {
        this.roomFinder = roomFinder;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public String create(RoomCreateRequest request) {
        String code = randomUniqueString();
        Member savedMember = memberRepository.save(new Member(request.username()));
        Room savedRoom = roomRepository.save(new Room(code, savedMember));
        return savedRoom.getCode();
    }

    @Transactional
    public void join(RoomJoinRequest request) {
        Room room = roomFinder.getRoomByCode(request.code());
        Member member = null;
        if (room.isFull()) {
            member = room.requireMemberExistsByName(request.username());
        }
        if (room.hasRoom()) {
            member = memberRepository.save(new Member(request.username()));
        }
        room.join(member);
        roomRepository.save(room);
    }

    private String randomUniqueString() {
        try {
            String uuid = UUID.randomUUID().toString();
            String sha256 = Encryptor.toSHA256(uuid);
            return sha256.substring(0, ROOM_CODE_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new JLogException("Failed to generate random unique string");
        }
    }
}
