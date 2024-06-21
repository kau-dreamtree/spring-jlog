package dreamtree.jlog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dreamtree.jlog.domain.Member;
import dreamtree.jlog.domain.Room;
import dreamtree.jlog.dto.RoomCreateRequest;
import dreamtree.jlog.dto.RoomJoinRequest;
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
        String code = Encryptor.randomUniqueString(ROOM_CODE_LENGTH);
        Member member = new Member(request.username());
        Member savedMember = memberRepository.save(member);
        Room room = new Room(code, savedMember);
        Room savedRoom = roomRepository.save(room);
        return savedRoom.code();
    }

    @Transactional
    public void join(RoomJoinRequest request) {
        Room room = roomFinder.getRoomByCode(request.code());
        if (room.hasNoRoom()) {
            Member member = room.authenticate(request.username());
            room.join(member);
            return;
        }
        Member member = new Member(request.username());
        Member saved = memberRepository.save(member);
        room.join(saved);
        roomRepository.save(room);
    }
}
