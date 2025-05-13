package sk.tuke.gamestudio.server.webservices;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/score")
@AllArgsConstructor
public class ScoreServiceRestServer {
    private final ScoreService scoreService;

    //GET -> http://localhost:8080/api/score/buckshot-roulette
    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    //POST -> http://localhost:8080/api/score
    @PostMapping
    void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    //DELETE -> http://localhost:8080/api/score
    @DeleteMapping
    void reset() {
        scoreService.reset();
    }
}

