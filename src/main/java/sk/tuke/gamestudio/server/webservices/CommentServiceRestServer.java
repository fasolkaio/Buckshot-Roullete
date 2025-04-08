package sk.tuke.gamestudio.server.webservices;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentServiceRestServer {
    private final CommentService commentService;

    //GET -> http://localhost:8080/api/comment/buckshot-roulette
    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }

    //POST -> http://localhost:8080/api/comment
    @PostMapping
    void addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
    }

    //DELETE -> http://localhost:8080/api/score
    @DeleteMapping
    void reset() {
        commentService.reset();
    }
}
