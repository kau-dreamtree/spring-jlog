package dreamtree.jlog.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

import dreamtree.jlog.dto.LogRequest;
import dreamtree.jlog.dto.LogsWithOutpayResponse;
import dreamtree.jlog.service.LogService;

@RestController
@RequestMapping(path = "api/log", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody LogRequest request) {
        logService.createLog(request);
    }

    @GetMapping
    public LogsWithOutpayResponse getLogsWithOutpay(
            @RequestParam("room_code") String roomCode,
            @RequestParam("username") String username
    ) {
        LogRequest request = new LogRequest(roomCode, username);
        return logService.getLogsWithOutpay(request);
    }

    @PutMapping
    public void update(@RequestBody LogRequest request) {
        logService.update(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody LogRequest request) {
        logService.delete(request);
    }
}
