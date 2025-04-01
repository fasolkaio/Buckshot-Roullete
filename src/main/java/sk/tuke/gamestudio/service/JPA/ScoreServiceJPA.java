package sk.tuke.gamestudio.service.JPA;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreException;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

public class ScoreServiceJPA implements ScoreService {
    @Override
    public void addScore(Score score) throws ScoreException {

    }

    @Override
    public List<Score> getTopScores(String game) throws ScoreException {
        return List.of();
    }

    @Override
    public void reset() throws ScoreException {

    }
}
