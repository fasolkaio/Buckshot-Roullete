package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import sk.tuke.gamestudio.server.controllers.BuckshotRouletteController;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.UserService;
import sk.tuke.gamestudio.service.jpa.CommentServiceJPA;
import sk.tuke.gamestudio.service.jpa.RatingServiceJPA;
import sk.tuke.gamestudio.service.jpa.ScoreServiceJPA;
import sk.tuke.gamestudio.service.jpa.UserServiceJPA;

@SpringBootApplication
@EntityScan(basePackages = "sk.tuke.gamestudio.entity")
@EnableScheduling
//swagger: http://localhost:8080/swagger-ui.html
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }

    @Bean
    public UserService userService() {return new UserServiceJPA();}
}

