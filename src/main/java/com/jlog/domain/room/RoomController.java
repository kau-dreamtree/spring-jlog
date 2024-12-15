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

@RestController
@RequestMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Deprecated
    @PostMapping(path = "/api/room")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomCreateResponse create(@RequestBody @Valid RoomCreateRequest request) {
        Room room = roomService.create(request);
        return new RoomCreateResponse(room.getCode());
    }

    @Deprecated
    @PutMapping(path = "/api/room")
    public void join(@RequestBody RoomJoinRequest request) {
        roomService.join(request);
    }

    @PostMapping(path = "api/v1/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomCreateResponse createV1(@RequestBody @Valid RoomCreateRequest request) {
        Room room = roomService.create(request);
        return new RoomCreateResponse(room.getCode());
    }

    @PutMapping(path = "/api/v1/rooms")
    public void joinV1(
            @RequestParam("roomCode") String roomCode,
            @RequestBody RoomJoinRequest request
    ) {
        request = new RoomJoinRequest(roomCode, request.username());
        roomService.join(request);
    }

    @GetMapping(path = "/api/v1/rooms")
    public RoomOutpaymentResponse outpayment(
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username
    ) {
        return roomService.getOutpayment(roomCode, username);
    }
}
