package shop.dreamtree.jlog.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.dreamtree.jlog.room.response.RoomResponse;

@RequiredArgsConstructor
@RequestMapping("api/room")
@RestController
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RoomResponse create(@RequestParam("username") String username) {
        return new RoomResponse(roomService.create(username));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    void join(@RequestBody RoomDto roomDto) {
        roomService.join(roomDto);
    }
}
