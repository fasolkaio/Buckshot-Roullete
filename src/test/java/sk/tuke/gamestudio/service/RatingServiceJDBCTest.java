package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RatingServiceJDBCTest {
    RatingService service = new RatingServiceJDBC();

    @Test
    public void setRating() {
        service.reset();
        service.setRating(new Rating("buckshot roulette", "lina", 5, new Date()));
        assertEquals(5, service.getAverageRating("buckshot roulette"));
        service.setRating(new Rating("buckshot roulette", "lina", 4, new Date()));
        assertEquals(4, service.getAverageRating("buckshot roulette"));

    }

    @Test
    public void getAverageRating() {
        service.reset();
        assertEquals(0, service.getAverageRating("buckshot roulette"));

        service.setRating(new Rating("buckshot roulette", "lina", 5, new Date()));
        service.setRating(new Rating("mines", "player", 1, new Date()));
        service.setRating(new Rating("buckshot roulette", "player", 3, new Date()));
        service.setRating(new Rating("mines", "player2", 3, new Date()));


        assertEquals(4, service.getAverageRating("buckshot roulette"));
        assertEquals(2, service.getAverageRating("mines"));
    }

    @Test
    public void getRating() {
        service.reset();
        service.setRating(new Rating("buckshot roulette", "lina", 5, new Date()));
        service.setRating(new Rating("mines", "player", 3, new Date()));
        service.setRating(new Rating("buckshot roulette", "player", 4, new Date()));

        assertEquals(5, service.getRating("buckshot roulette", "lina"));
        assertEquals(3, service.getRating("mines", "player"));
        assertEquals(4, service.getRating("buckshot roulette", "player"));
    }

    @Test
    public void testReset() {
        service.reset();
        assertEquals(0, service.getAverageRating("buckshot roulette"));
        service.setRating(new Rating("buckshot roulette", "lina", 5, new Date()));
        assertEquals(5, service.getAverageRating("buckshot roulette"));
    }
}