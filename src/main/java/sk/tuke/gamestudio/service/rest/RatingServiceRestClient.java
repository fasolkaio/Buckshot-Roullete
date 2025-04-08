package sk.tuke.gamestudio.service.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

public class RatingServiceRestClient implements RatingService {
    @Value("${remote.server.api}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.put(url + "/rating", rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return restTemplate.getForObject(url + "/rating/" + game, Integer.class);
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return restTemplate.getForObject(url + "/rating/" + game + "/" + player, Integer.class);
    }

    @Override
    public void reset() throws RatingException {
        restTemplate.delete(url + "/rating");
    }
}
