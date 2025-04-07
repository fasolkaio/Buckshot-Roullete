package sk.tuke.gamestudio.service.jpaRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.repository.ScoreRepository;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@Transactional
public class ScoreServiceJpaRepository implements ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public void addScore(Score score) {
        scoreRepository.save(score);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return scoreRepository.findFirst10ScoresByGameOrderByPointsDesc(game);
    }

    @Override
    public void reset() {
        scoreRepository.reset();
    }
}

