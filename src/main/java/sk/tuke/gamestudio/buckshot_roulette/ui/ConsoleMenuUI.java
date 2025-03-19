package sk.tuke.gamestudio.buckshot_roulette.ui;

import sk.tuke.gamestudio.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.buckshot_roulette.core.GameMode;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sk.tuke.gamestudio.buckshot_roulette.core.GameMode.*;
import static sk.tuke.gamestudio.buckshot_roulette.core.GameMode.Testing;

public class ConsoleMenuUI {
    private final static int PAGE_CAPACITY = 5;
    private MenuState menuState = MenuState.DEFAULT;
    private final String gameName = "buckshot roulette";
    private int commentPage = 0;

    //input|output utilities
    private static final ConsoleGUI gui = new ConsoleGUI();
    private static final Scanner scanner = new Scanner(System.in);

    //input patterns
    private final Pattern menuInputPattern;
    private final Pattern commentInputPattern;
    private final Pattern rateInputPattern;
    private final Pattern ratingInputPattern;
    private final Pattern modeInputPattern;

    //services
    private static final CommentService commentService = new CommentServiceJDBC();
    private static final RatingService ratingService = new RatingServiceJDBC();

    public ConsoleMenuUI() {
        menuInputPattern = Pattern.compile("^(play|comment|rate|exit|reset)$");
        commentInputPattern = Pattern.compile("^(comment|>|<|exit)$");
        rateInputPattern = Pattern.compile("^(rate|exit)$");
        ratingInputPattern = Pattern.compile("^[1-5]$");
        modeInputPattern = Pattern.compile("^(single|duo|bot|test|s|d|b|t|exit)$");
    }

    public void run(){
        while (menuState != MenuState.EXIT) {
            show();
            handleInput();
        }
    }

    private void show() {
        gui.displayRepeatedLineOf("=");
        switch (menuState) {
            case DEFAULT:
                gui.displayMenu();
                break;
            case GAME:
                play();
                break;
            case COMMENTS:
                showComments();
                break;
            case RATING:
                showRating();
                break;
        }
    }

    private void handleInput() {
        String input = scanInput();
        switch (menuState) {
            case DEFAULT:
                menuHandle(input);
                break;
            case GAME:
                gameHandle();
                break;
            case COMMENTS:
                commentsHandle(input);
                break;
            case RATING:
                ratingHandle(input);
                break;
        }
    }

    private void menuHandle(String input) {
        Matcher matcher = menuInputPattern.matcher(input);
        if(matcher.matches()){
            switch (matcher.group(0)){
                case "play":
                    menuState = MenuState.GAME;
                    break;
                case "comment":
                    menuState = MenuState.COMMENTS;
                    break;
                case "rate":
                    menuState = MenuState.RATING;
                    break;
                case "reset":
                    reset();
                    break;
                case "exit":
                    scanner.close();
                    menuState = MenuState.EXIT;
                    break;
            }
        }
        else{
            gui.displayLine("Wrong input");
        }
    }

    private void play() {
        GameMode gameMode = getGameMode();
        if(gameMode == null) return;
        Game game;
        switch(gameMode) {
            case Single:
                game = new Game(askName());
                break;
            case P2P:
                game = new Game(askName(), askName());
                break;
            default:
                game = new Game(gameMode);
        }
        GameUI gameUi = new ConsoleGameUI(scanner, gui);
        gameUi.play(game);
    }

    private GameMode getGameMode(){
        gui.displayMassage("Choose game mode (single|duo) or type (exit) to exit: ");
        String input = scanInput();

        Matcher matcher = modeInputPattern.matcher(input);

        if(!matcher.find()){
            gui.displayLine("Wrong input");
            return null;
        }

        switch (matcher.group(0)) {
            case "single":
            case "s":
                return Single;
            case "duo":
            case "d":
                return P2P;
            case "b":
            case "bot":
                return B2B;
            case "t":
            case "test":
                return Testing;
            case "exit":
                menuState = MenuState.DEFAULT;
                return null;
            default:
                throw new IllegalArgumentException("Invalid mode");
        }
    }

    private void gameHandle() {
        gui.displayMassage("Press enter to continue ");
        scanner.nextLine();
        menuState = MenuState.DEFAULT;
    }

    private void showComments() {
        gui.displayLine("<COMMENTS>");
        List<Comment> comments = commentService.getComments(gameName);
        if(comments.isEmpty()){
            gui.displayLine("No comments found");
            gui.displayCommentRules();
            return;
        }
        int size = comments.size();
        int lastPage = (int) (Math.ceil((double) size / PAGE_CAPACITY) - 1);
        if(commentPage > lastPage){
            commentPage = lastPage;
        }

        int firstComment = commentPage * PAGE_CAPACITY;
        int lastComment = Math.min(firstComment + PAGE_CAPACITY, comments.size());
        List<Comment> cutedList = comments.subList(firstComment, lastComment);
        gui.displayComments(cutedList);
        gui.displayCommentRules();
    }

    private void commentsHandle(String input) {
        Matcher matcher = commentInputPattern.matcher(input);
        if(matcher.matches()){
            switch (matcher.group(0)){
                case "comment":
                    comment();
                    break;
                case "<":
                    toPreviousPage();
                    break;
                case "rate":
                    toNextPage();
                    break;
                case "exit":
                    toStartPage();
                    menuState = MenuState.DEFAULT;
                    break;
            }
        }
        else{
            gui.displayLine("Wrong input");
        }
    }

    private void comment() {
        String name = askName();
        gui.displayMassage("Enter your comment: ");
        String comment = scanner.nextLine();

        commentService.addComment(new Comment(gameName, name, comment, new Date()));
    }

    private void toStartPage() {
        commentPage = 0;
    }

    private void toNextPage() {
        commentPage++;
    }

    private void toPreviousPage() {
        commentPage--;
        if(commentPage < 0){
            commentPage = 0;
        }
    }

    private void showRating() {
        gui.displayMassage("Average rating: ");
        int averageRating = ratingService.getAverageRating(gameName);
        gui.displayLine("*".repeat(averageRating) + "\n");
        gui.displayRatingRules();
    }

    private void ratingHandle(String input) {
        Matcher matcher = rateInputPattern.matcher(input);
        if(matcher.matches()){
            switch (matcher.group(0)){
                case "rate":
                    rate();
                    break;
                case "exit":
                    menuState = MenuState.DEFAULT;
                    break;
            }
        }
        else{
            gui.displayLine("Wrong input");
        }
    }

    private void rate() {
        String name = askName();
        int rating = ratingService.getRating(gameName, name);
        if(rating > 0){
            gui.displayLine("You have already rated game by " + rating + " stars");
        }
        gui.displayMassage("Rate game from 1 to 5: ");

        Matcher matcher = ratingInputPattern.matcher(scanInput());
        if(matcher.matches()){
            int newRating = Integer.parseInt(matcher.group(0));
            ratingService.setRating(new Rating(gameName, name, newRating, new Date()));
            gui.displayLine("Rated successfully");
        }
        else{
            gui.displayLine("Wrong input");
        }


    }

    private String askName(){
        gui.displayMassage("Please enter your name: ");
        return scanner.nextLine().trim();
    }

    private String scanInput(){
        return scanner.nextLine().toLowerCase().trim();
    }

    private void reset(){
        commentService.reset();
        ratingService.reset();
        new ScoreServiceJDBC().reset();
    }
}
