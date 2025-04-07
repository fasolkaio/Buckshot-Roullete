package sk.tuke.gamestudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Rating save(Rating rating);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
    Double getAverageRating(String game);

    Optional<Rating> findByGameAndPlayer(String game, String player);

    @Modifying
    @Query(value = "DELETE FROM rating", nativeQuery = true)
    void reset();
}
