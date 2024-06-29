package dreamtree.jlog.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dreamtree.jlog.dto.RoomCreateRequest;
import dreamtree.jlog.dto.RoomJoinRequest;
import dreamtree.jlog.dto.RoomResponse;
import dreamtree.jlog.service.RoomService;

@RestController
@RequestMapping(path = "api/room", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse create(@RequestBody RoomCreateRequest request) {
        String roomCode = roomService.create(request);
        return new RoomResponse(roomCode);
    }

    @PutMapping
    public void join(@RequestBody RoomJoinRequest request) {
        roomService.join(request);
    }
}
