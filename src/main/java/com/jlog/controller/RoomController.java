package com.jlog.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jlog.dto.RoomCreateRequest;
import com.jlog.dto.RoomJoinRequest;
import com.jlog.dto.RoomResponse;
import com.jlog.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/room", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse create(@RequestBody @Valid RoomCreateRequest request) {
        String roomCode = roomService.create(request);
        return new RoomResponse(roomCode);
    }

    @PutMapping
    public void join(@RequestBody RoomJoinRequest request) {
        roomService.join(request);
    }
}
