package sk.tuke.gamestudio.service.jpaRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.repository.RatingRepository;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

import java.util.Optional;

@Transactional
public class RatingServiceJpaRepository implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public void setRating(Rating rating) throws RatingException {
        Optional<Rating> existing = ratingRepository.findByGameAndPlayer(rating.getGame(), rating.getPlayer());

        if (existing.isPresent()) {
            Rating updated = existing.get();
            updated.setRating(rating.getRating());
            updated.setRatedOn(rating.getRatedOn());
            ratingRepository.save(updated);
        } else {
            ratingRepository.save(rating);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double averageRating = ratingRepository.getAverageRating(game);
        if(averageRating == null)
            return 0;
        return (int) Math.round(averageRating);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        Optional<Rating> rating = ratingRepository.findByGameAndPlayer(game, player);
        return rating.map(Rating::getRating).orElse(0);
    }

    @Override
    public void reset() throws RatingException {
        ratingRepository.reset();
    }
}
