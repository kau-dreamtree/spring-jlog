package shop.dreamtree.jlog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import shop.dreamtree.jlog.dto.LogRequest;
import shop.dreamtree.jlog.dto.RoomRequest;
import shop.dreamtree.jlog.dto.RoomResponse;
import shop.dreamtree.jlog.service.RoomService;

@RestController
@RequestMapping("api/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse create(@RequestBody LogRequest requestBody) {
        return new RoomResponse(roomService.create(requestBody.username()));
    }

    @PutMapping
    public void join(@RequestBody RoomRequest request) {
        roomService.join(request);
    }
}
