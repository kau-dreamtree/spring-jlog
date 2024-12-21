package com.jlog.domain.room;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("removal")
@RestController
@RequestMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Deprecated(forRemoval = true)
    @PostMapping(path = "/api/room")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomCreateResponse create(@RequestBody @Valid RoomCreateRequest request) {
        var room = roomService.create(request);
        return new RoomCreateResponse(room.getCode());
    }

    @Deprecated(forRemoval = true)
    @PutMapping(path = "/api/room")
    public void join(@RequestBody @Valid RoomJoinRequest request) {
        roomService.join(request);
    }

    @PostMapping(path = "api/v1/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createV1(@RequestBody @Valid RoomRequestV1 request) {
        var room = roomService.create(request);
        return RoomResponse.from(room);
    }

    @PutMapping(path = "/api/v1/rooms")
    public RoomResponse joinV1(
            @RequestParam("roomCode") String roomCode,
            @RequestBody @Valid RoomRequestV1 request
    ) {
        request = new RoomRequestV1(roomCode, request.username());
        var room = roomService.join(request);
        return RoomResponse.from(room);
    }

    @GetMapping(path = "/api/v1/rooms")
    public RoomResponse get(
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username
    ) {
        var request = new RoomRequestV1(roomCode, username);
        var room = roomService.get(request);
        return RoomResponse.from(room);
    }
}
