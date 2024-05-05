package shop.dreamtree.jlog.logs;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/log")
@RestController
public class LogsController {
    private final LogsService logsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    String save(@RequestBody LogsDto logsDto) {
        logsService.save(logsDto);
        return "Log created";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    LogsResponse get(@RequestParam("room_code") String uid, @RequestParam("username") String username) {
        return logsService.find(uid, username);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    String update(@RequestBody LogsDto logsDto) {
        logsService.update(logsDto);
        return "Log updated";
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String delete(@RequestBody LogsDto logsDto) {
        logsService.delete(logsDto);
        return "Log deleted";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.badRequest().body("존재하지 않는 코드 또는 이름입니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body("접근할 수 없는 방입니다.");
    }
}
