package shop.dreamtree.jlog.room;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.dreamtree.jlog.logs.LogCreateRequestBody;
import shop.dreamtree.jlog.room.response.RoomResponse;

@RequiredArgsConstructor
@RequestMapping("api/room")
@RestController
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RoomResponse create(@RequestBody LogCreateRequestBody requestBody) {
        return new RoomResponse(roomService.create(requestBody.getUsername()));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    void join(@RequestBody RoomDto roomDto) {
        roomService.join(roomDto);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<String> handleRuntimeException(DataIntegrityViolationException exception) {
        return ResponseEntity.badRequest().body("이름은 5자 이하로 입력해주세요.");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.badRequest().body("존재하지 않는 코드 또는 이름입니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body("방이 가득 찼습니다.");
    }
}
