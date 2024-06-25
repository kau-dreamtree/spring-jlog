package dreamtree.jlog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dreamtree.jlog.dto.LogRequest;
import dreamtree.jlog.dto.LogsWithOutpayResponse;
import dreamtree.jlog.service.LogService;

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
    public LogsWithOutpayResponse getLogsWithOutpay(@ModelAttribute LogRequest request) {
        return logService.getLogsWithOutpay(request);
    }

    @PutMapping
    public void update(@RequestBody LogRequest request) {
        logService.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id, LogRequest request) {
        var requestWIthId = request.withId(id);
        logService.delete(requestWIthId);
    }
}