package shop.dreamtree.jlog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import shop.dreamtree.jlog.dto.LogDto;
import shop.dreamtree.jlog.dto.LogResponse;
import shop.dreamtree.jlog.service.LogService;

@RequestMapping("api/log")
@RestController
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody LogDto logDto) {
        logService.save(logDto);
    }

    @GetMapping
    public LogResponse getLogsWithBalance(
            @RequestParam("room_code") String roomCode,
            @RequestParam("username") String username
    ) {
        return logService.getLogsWithBalance(roomCode, username);
    }

    @PutMapping
    public void update(@RequestBody LogDto logDto) {
        logService.update(logDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody LogDto logDto) {
        logService.delete(logDto);
    }
}
