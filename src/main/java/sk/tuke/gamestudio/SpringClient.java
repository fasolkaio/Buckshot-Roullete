package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.GameUI;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.MenuUI;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.console.ConsoleGameUI;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.console.ConsoleMenuUI;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.jpa.RatingServiceJPA;
import sk.tuke.gamestudio.service.jpa.ScoreServiceJPA;
import sk.tuke.gamestudio.service.jpaRepository.CommentServiceJpaRepository;
import sk.tuke.gamestudio.service.jpaRepository.RatingServiceJpaRepository;
import sk.tuke.gamestudio.service.jpaRepository.ScoreServiceJpaRepository;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import javax.sql.DataSource;
import java.util.Scanner;


@SpringBootApplication
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }

    //@Bean
    public CommandLineRunner runner(MenuUI menuUI) {
        return s -> menuUI.run();
    }

    @Bean
    public MenuUI menuUI() {
        return new ConsoleMenuUI();
    }

    @Bean
    public GameUI gameUI() {
        return new ConsoleGameUI();
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public ScoreService scoreService(DataSource dataSource) {
//        return new ScoreServiceJDBC(dataSource);
//        return new ScoreServiceJPA();
        return new ScoreServiceJpaRepository();
    }

    @Bean
    public RatingService ratingService(DataSource dataSource) {
//        return new RatingServiceJDBC(dataSource);
//        return new RatingServiceJPA();
        return new RatingServiceJpaRepository();
    }

    @Bean
    public CommentService commentService(DataSource dataSource) {
//        return new CommentServiceJDBC(dataSource);
//        return new CommentServiceJPA();
        return new CommentServiceJpaRepository();
    }

}
