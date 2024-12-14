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

    @PostMapping(path = "api/room")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomCreateResponse create(@RequestBody @Valid RoomCreateRequest request) {
        String roomCode = roomService.create(request);
        return new RoomCreateResponse(roomCode);
    }

    @PutMapping(path = "api/room")
    public void join(@RequestBody RoomJoinRequest request) {
        roomService.join(request);
    }

    @GetMapping(path = "api/v1/rooms")
    public RoomBalanceResponse balance(
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username
    ) {
        return roomService.getBalance(roomCode, username);
    }
}
