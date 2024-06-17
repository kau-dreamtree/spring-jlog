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

import shop.dreamtree.jlog.dto.LogRequest;
import shop.dreamtree.jlog.dto.LogsWithOutpayResponse;
import shop.dreamtree.jlog.service.LogService;

@RestController
@RequestMapping("api/log")
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
        return logService.getLogsWithOutpay(roomCode, username);
    }

    @PutMapping
    public void update(@RequestBody LogRequest request) {
        logService.update(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody LogRequest request) {
        // todo: To work with an id as a path variable and a roomCode as a request parameter.
        logService.delete(request);
    }
}
