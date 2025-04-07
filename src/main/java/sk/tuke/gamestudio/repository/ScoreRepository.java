package sk.tuke.gamestudio.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreRepository extends Repository<Score, Integer> {
    Score save(Score score);

    List<Score> findFirst10ScoresByGameOrderByPointsDesc(String game);

    @Modifying
    @Query(value = "DELETE FROM score", nativeQuery = true)
    void reset();
}
