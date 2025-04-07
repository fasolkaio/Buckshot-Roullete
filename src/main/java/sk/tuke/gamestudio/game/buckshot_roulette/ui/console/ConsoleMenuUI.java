package sk.tuke.gamestudio.game.buckshot_roulette.ui.console;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.GameMode;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.GameUI;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.MenuState;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.MenuUI;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sk.tuke.gamestudio.game.buckshot_roulette.core.GameMode.*;
import static sk.tuke.gamestudio.game.buckshot_roulette.core.GameMode.Testing;

public class ConsoleMenuUI implements MenuUI {
    private final static int PAGE_CAPACITY = 5;
    private MenuState menuState = MenuState.DEFAULT;
    private final String gameName = "buckshot roulette";
    private int commentPage = 0;

    //input|output utilities
    @Autowired
    private ConsoleGUI gui;
    @Autowired
    private Scanner scanner;

    //input patterns
    private final Pattern menuInputPattern;
    private final Pattern communityInputPattern;
    private final Pattern ratingInputPattern;
    private final Pattern modeInputPattern;

    //services
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    @Autowired
    private GameUI gameUi;

    public ConsoleMenuUI() {
        menuInputPattern = Pattern.compile("^(play|community|exit|reset)$");
        communityInputPattern = Pattern.compile("^(comment|rate|>|<|exit)$");
        ratingInputPattern = Pattern.compile("^[1-5]$");
        modeInputPattern = Pattern.compile("^(single|duo|bot|test|s|d|b|t|exit)$");
    }

    public void run() {
        while (menuState != MenuState.EXIT) {
            show();
            handleInput();
        }
    }

    public void show() {
        gui.displayRepeatedLineOf("=");
        switch (menuState) {
            case DEFAULT -> gui.displayMenu();
            case GAME -> play();
            case COMMUNITY -> showCommunity();
        }
    }


    public void handleInput() {
        String input = scanInput();
        switch (menuState) {
            case DEFAULT:
                menuHandle(input);
                break;
            case GAME:
                gameHandle();
                break;
            case COMMUNITY:
                communityHandle(input);
                break;
        }
    }

    private void menuHandle(String input) {
        Matcher matcher = menuInputPattern.matcher(input);
        if (matcher.matches()) {
            switch (matcher.group(0)) {
                case "play":
                    menuState = MenuState.GAME;
                    break;
                case "community":
                    menuState = MenuState.COMMUNITY;
                    break;
                case "reset":
                    reset();
                    break;
                case "exit":
                    scanner.close();
                    menuState = MenuState.EXIT;
                    break;
            }
        } else {
            gui.displayWarningMassage("Wrong input");
        }
    }

    private void play() {
        GameMode gameMode = getGameMode();
        if (gameMode == null) return;
        Game game = switch (gameMode) {
            case Single -> new Game(askName());
            case P2P -> new Game(askName(), askName());
            default -> new Game(gameMode);
        };
        gameUi.play(game);
    }

    private GameMode getGameMode() {
        gui.displayRuleMessage("Choose game mode (single|duo) or type (exit) to exit: ");
        String input = scanInput();

        Matcher matcher = modeInputPattern.matcher(input);

        if (!matcher.find()) {
            gui.displayWarningMassage("Wrong input");
            return null;
        }

        return switch (matcher.group(0)) {
            case "single", "s" -> Single;
            case "duo", "d" -> P2P;
            case "b", "bot" -> B2B;
            case "t", "test" -> Testing;
            case "exit" -> {
                menuState = MenuState.DEFAULT;
                yield null;
            }
            default -> throw new IllegalArgumentException("Invalid mode");
        };
    }

    private void gameHandle() {
        menuState = MenuState.DEFAULT;
        gui.displayRuleMessage("Press enter to continue ");
        scanner.nextLine();
    }

    private void showCommunity() {
        showRating();
        showTopScores();
        showComments();
        gui.displayCommunityRules();
    }

    private void showTopScores() {
        List<Score> topScores = scoreService.getTopScores("buckshot roulette");
        gui.displayTopScores(topScores);
    }

    private void showRating() {
        int averageRating = ratingService.getAverageRating(gameName);
        gui.displayRatting(averageRating);
    }

    private void showComments() {
        List<Comment> comments = commentService.getComments(gameName);
        if (comments.isEmpty()) {
            gui.displayComments(comments, 0);
            return;
        }
        int size = comments.size();
        int lastPage = (int) (Math.ceil((double) size / PAGE_CAPACITY) - 1);
        if (commentPage > lastPage) {
            commentPage = lastPage;
        }

        int firstComment = commentPage * PAGE_CAPACITY;
        int lastComment = Math.min(firstComment + PAGE_CAPACITY, comments.size());
        List<Comment> cutedList = comments.subList(firstComment, lastComment);

        gui.displayComments(cutedList, commentPage);
    }

    private void communityHandle(String input) {
        Matcher matcher = communityInputPattern.matcher(input);
        if (matcher.matches()) {
            switch (matcher.group(0)) {
                case "comment" -> comment();
                case "<" -> toPreviousPage();
                case ">" -> toNextPage();
                case "exit" -> exitToMenu();
                case "rate" -> rate();
            }
        } else {
            gui.displayWarningMassage("Wrong input");
        }
    }

    private void comment() {
        String name = askName();
        gui.displayRuleMessage("Enter your comment: ");
        String comment = scanner.nextLine();

        commentService.addComment(new Comment(gameName, name, comment, new Date()));
    }


    private void rate() {
        String name = askName();
        int rating = ratingService.getRating(gameName, name);
        if (rating > 0) {
            gui.displayRuleMessage("You have already rated game by " + rating + " stars");
        }
        gui.displayRuleMessage("Rate game from 1 to 5: ");

        Matcher matcher = ratingInputPattern.matcher(scanInput());
        if (matcher.matches()) {
            int newRating = Integer.parseInt(matcher.group(0));
            ratingService.setRating(new Rating(gameName, name, newRating, new Date()));
            gui.displayRuleMessage("Rated successfully");
        } else {
            gui.displayWarningMassage("Wrong input");
        }
    }

    private void toNextPage() {
        commentPage++;
    }

    private void toPreviousPage() {
        commentPage--;
        if (commentPage < 0) {
            commentPage = 0;
        }
    }

    private void exitToMenu() {
        commentPage = 0;
        menuState = MenuState.DEFAULT;
    }

    private String askName() {
        gui.displayRuleMessage("Please enter your name: ");
        return scanner.nextLine().trim();
    }

    private String scanInput() {
        return scanner.nextLine().toLowerCase().trim();
    }

    private void reset() {
        commentService.reset();
        ratingService.reset();
        scoreService.reset();
    }
}
