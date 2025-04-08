package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.GameUI;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.MenuUI;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.console.ConsoleGameUI;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.console.ConsoleMenuUI;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.rest.CommentServiceRestClient;
import sk.tuke.gamestudio.service.rest.RatingServiceRestClient;
import sk.tuke.gamestudio.service.rest.ScoreServiceRestClient;

import javax.sql.DataSource;
import java.util.Scanner;


@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
//        SpringApplication.run(SpringClient.class);
    }

    @Bean
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
//        return new ScoreServiceJpaRepository();
        return new ScoreServiceRestClient();
    }

    @Bean
    public RatingService ratingService(DataSource dataSource) {
//        return new RatingServiceJDBC(dataSource);
//        return new RatingServiceJPA();
//        return new RatingServiceJpaRepository();
        return new RatingServiceRestClient();
    }

    @Bean
    public CommentService commentService(DataSource dataSource) {
//        return new CommentServiceJDBC(dataSource);
//        return new CommentServiceJPA();
//        return new CommentServiceJpaRepository();
        return new CommentServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
