package sk.tuke.gamestudio.service.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.RatingId;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        RatingId ratingId = new RatingId(rating.getGame(), rating.getPlayer());
        Rating ratingOld = entityManager.find(Rating.class, ratingId);

        if(ratingOld != null) {
            ratingOld.setRating(rating.getRating());
            ratingOld.setRatedOn(rating.getRatedOn());
            entityManager.merge(ratingOld);
        }
        else
            entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double averageRating = (Double) entityManager
                .createQuery("select avg(r.rating) from Rating r where r.game = :game")
                .setParameter("game", game  )
                .getSingleResult();

        if (averageRating == null)
            return 0;
        return (int) Math.round(averageRating);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        RatingId ratingId = new RatingId(game, player);
        Rating rating = entityManager.find(Rating.class, ratingId);

        if(rating == null)
            return 0;
        return rating.getRating();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNativeQuery("DELETE from Rating").executeUpdate();
    }
}