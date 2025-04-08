package sk.tuke.gamestudio.server.webservices;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

@RestController
@RequestMapping("/api/rating")
@AllArgsConstructor
public class RatingServiceRestServer {
    private final RatingService ratingService;

    //PUT -> http://localhost:8080/api/rating
    @PutMapping
    void setRating(@RequestBody Rating rating) throws RatingException {
        ratingService.setRating(rating);
    }

    //GET -> http://localhost:8080/api/rating/buckshot-roulette
    @GetMapping("/{game}")
    int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    //GET -> http://localhost:8080/api/rating/buckshot-roulette/lina
    @GetMapping("/{game}/{player}")
    int getRating(@PathVariable String game,@PathVariable String player){
        return ratingService.getRating(game, player);
    }

    //DELETE -> http://localhost:8080/api/rating
    @DeleteMapping
    void reset() {
        ratingService.reset();
    }
}
