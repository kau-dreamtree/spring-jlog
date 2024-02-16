package shop.dreamtree.jlog.room;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/room")
@RestController
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody RoomDto roomDto) {
        roomService.create(roomDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    RoomDto join(@RequestBody RoomDto roomDto) {
        return roomService.join(roomDto);
    }
}
