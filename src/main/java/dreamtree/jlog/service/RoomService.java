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

    public RoomService(
            RoomFinder roomFinder,
            RoomRepository roomRepository,
            MemberRepository memberRepository
    ) {
        this.roomFinder = roomFinder;
        this.roomRepository = roomRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public String create(RoomCreateRequest request) {
        String code = Encryptor.randomUniqueString(ROOM_CODE_LENGTH);
        Member savedMember = memberRepository.save(new Member(request.username()));
        Room savedRoom = roomRepository.save(new Room(code, savedMember));
        return savedRoom.code();
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
}
