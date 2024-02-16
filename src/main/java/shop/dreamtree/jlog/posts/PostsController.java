package shop.dreamtree.jlog.posts;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("api/log")
@RestController
public class PostsController {
    private final PostsService postsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    String save(@RequestBody PostsDto postsDto) {
        postsService.save(postsDto);
        return "Log created";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Posts> get(@RequestParam("uid") String roomUid, @RequestParam("username") String username) {
        return postsService.find(roomUid, username);
    }
}
