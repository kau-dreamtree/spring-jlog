package com.jlog.domain.log;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class LogController {

    private final LogServiceImpl logService;

    @PostMapping(path = "/api/log")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody LogRequest request) {
        logService.create(request);
    }

    @Deprecated(forRemoval = true)
    @GetMapping(path = "/api/log")
    public LogsWithOutpayResponse getLogsWithOutpay(
            @RequestParam("room_code") String roomCode,
            @RequestParam("username") String username
    ) {
        return logService.findAll(roomCode, username);
    }

    @PutMapping(path = "/api/log")
    public void update(@RequestBody LogRequest request) {
        logService.update(request);
    }

    @DeleteMapping(path = "/api/log")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody LogRequest request) {
        logService.delete(request);
    }

    @PostMapping(path = "/api/v1/logs")
    @ResponseStatus(HttpStatus.CREATED)
    public LogResponseV1 createV1(
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username,
            @RequestBody LogRequestV1 request
    ) {
        request = LogRequestV1.of(roomCode, username, request.expense(), request.memo());
        Log log = logService.create(request);
        return LogResponseV1.from(log);
    }

    @GetMapping(path = "/api/v1/logs")
    public Slice<LogResponseV1> getLogs(
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username,
            Pageable pageable
    ) {
        var request = LogRequestV1.of(roomCode, username);
        return logService.findByRoom(request, pageable);
    }

    @PutMapping(path = "/api/v1/logs/{id}")
    public LogResponseV1 updateV1(
            @PathVariable Long id,
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username,
            @RequestBody LogRequestV1 request
    ) {
        request = new LogRequestV1(id, roomCode, username, request.expense(), request.memo());
        Log log = logService.update(request);
        return LogResponseV1.from(log);
    }

    @DeleteMapping(path = "/api/v1/logs/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteV1(
            @PathVariable Long id,
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username
    ) {
        var request = LogRequestV1.of(id, roomCode, username);
        logService.delete(request);
    }
}
