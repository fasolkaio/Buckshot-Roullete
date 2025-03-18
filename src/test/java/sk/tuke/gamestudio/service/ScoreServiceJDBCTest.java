package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ScoreServiceJDBCTest {
    private ScoreServiceJDBC scoreService = new ScoreServiceJDBC();

    @Test
    public void testAddScore() {
        scoreService.reset();
        scoreService.addScore(new Score("buckshot roulette", "lina", 5000, new Date()));
        List<Score> scores =  scoreService.getTopScores("buckshot roulette");
        assertEquals(1, scores.size());
        Score score = scores.get(0);
        assertEquals("buckshot roulette", score.getGame());
        assertEquals("lina", score.getPlayer());
        assertEquals(5000, score.getPoints());
    }

    @Test
    public void testGetTopScoresOrder() {
        scoreService.reset();

        scoreService.addScore(new Score("buckshot roulette", "pl1", 5000, new Date()));
        scoreService.addScore(new Score("mines", "pl2", 150, new Date()));
        scoreService.addScore(new Score("buckshot roulette", "pl3", 20000, new Date()));
        scoreService.addScore(new Score("buckshot roulette", "pl4", 300, new Date()));
        scoreService.addScore(new Score("buckshot roulette", "pl5", 15042, new Date()));

        List<Score> scores =  scoreService.getTopScores("buckshot roulette");
        assertEquals(4, scores.size());

        Score score = scores.get(0);
        assertEquals("pl3", score.getPlayer());
        assertEquals("buckshot roulette", score.getGame());
        assertEquals(20000, score.getPoints());

        score = scores.get(1);
        assertEquals("pl5", score.getPlayer());
        assertEquals("buckshot roulette", score.getGame());
        assertEquals(15042, score.getPoints());

        score = scores.get(2);
        assertEquals("pl1", score.getPlayer());
        assertEquals("buckshot roulette", score.getGame());
        assertEquals(5000, score.getPoints());

        score = scores.get(3);
        assertEquals("pl4", score.getPlayer());
        assertEquals("buckshot roulette", score.getGame());
        assertEquals(300, score.getPoints());
    }

    @Test
    public void testGetTopScoresCount(){
        scoreService.reset();

        for(int i = 0; i < 20; i++){
            scoreService.addScore(new Score("buckshot roulette", "player", 5000, new Date()));

        }

        List<Score> scores =  scoreService.getTopScores("buckshot roulette");
        assertEquals(10, scores.size());
    }

    @Test
    public void testReset() {
        scoreService.reset();
        List<Score> scores = scoreService.getTopScores("buckshot roulette");
        assertEquals(0, scores.size());
    }
}