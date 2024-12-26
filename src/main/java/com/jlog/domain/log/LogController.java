package com.jlog.domain.log;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

@SuppressWarnings("removal")
@RestController
@RequestMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @Deprecated(forRemoval = true)
    @PostMapping(path = "/api/log")
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(cacheNames = "logs", key = "#request.roomCode()")
    public void save(@RequestBody LogRequest request) {
        logService.create(request);
    }

    @Deprecated(forRemoval = true)
    @GetMapping(path = "/api/log")
    @Cacheable(cacheNames = "logs", key = "#roomCode")
    public LogsWithOutpayResponse getLogsWithOutpay(
            @RequestParam("room_code") String roomCode,
            @RequestParam("username") String username
    ) {
        var request = new LogRequest(null, roomCode, username, null, null);
        return logService.findAll(request);
    }

    @Deprecated(forRemoval = true)
    @PutMapping(path = "/api/log")
    public void update(@RequestBody LogRequest request) {
        logService.update(request);
    }

    @Deprecated(forRemoval = true)
    @DeleteMapping(path = "/api/log")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody LogRequest request) {
        logService.delete(request);
    }

    @PostMapping(path = "/api/v1/logs")
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(cacheNames = "logs", key = "#roomCode")
    public LogResponseV1 createV1(
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username,
            @RequestBody LogRequestV1 request
    ) {
        request = LogRequestV1.of(roomCode, username, request.expense(), request.memo());
        var log = logService.create(request);
        return LogResponseV1.from(log);
    }

    @GetMapping(path = "/api/v1/logs")
    public List<LogResponseV1> findV1(
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username,
            @RequestParam(value = "lastId", required = false) Long lastId
    ) {
        var request = LogRequestV1.of(lastId, roomCode, username);
        var logs = logService.findLogsByRoomAfterId(request);
        return logs.stream().map(LogResponseV1::from).toList();
    }

    @PutMapping(path = "/api/v1/logs/{id}")
    public LogResponseV1 updateV1(
            @PathVariable Long id,
            @RequestParam("roomCode") String roomCode,
            @RequestParam("username") String username,
            @RequestBody LogRequestV1 request
    ) {
        request = LogRequestV1.of(id, roomCode, username, request.expense(), request.memo());
        var log = logService.update(request);
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
