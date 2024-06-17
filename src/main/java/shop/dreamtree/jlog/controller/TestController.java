package shop.dreamtree.jlog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    private record TestResponse(String method, String message) {
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TestResponse post() {
        return new TestResponse("POST", "success");
    }

    @GetMapping
    TestResponse get() {
        return new TestResponse("GET", "success");
    }

    @PutMapping
    TestResponse put() {
        return new TestResponse("PUT", "success");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete() {
    }
}
