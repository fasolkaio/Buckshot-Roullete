package sk.tuke.gamestudio.service.JPA;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

public class RatingServiceJPA implements RatingService {
    @Override
    public void setRating(Rating rating) throws RatingException {

    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return 0;
    }

    @Override
    public void reset() throws RatingException {

    }
}
