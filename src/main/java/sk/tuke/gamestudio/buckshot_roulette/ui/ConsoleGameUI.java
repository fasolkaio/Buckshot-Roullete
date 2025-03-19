package sk.tuke.gamestudio.buckshot_roulette.ui;

import sk.tuke.gamestudio.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.buckshot_roulette.core.GameState;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.Action;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.Shoot;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.UseItem;
import sk.tuke.gamestudio.buckshot_roulette.core.items.*;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Player;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleGameUI implements GameUI{
    private Game game;
    private boolean firstRound = true;

    //output/input utilities
    private final ConsoleGUI gui;
    private final Scanner scanner;

    //services
    private static final ScoreService scoreService = new ScoreServiceJDBC();

    //command patterns
    private final Pattern usePattern;
    private final Pattern shootPattern;

    public ConsoleGameUI(Scanner scanner, ConsoleGUI gui) {
        usePattern = Pattern.compile("^(u|use) ([bcmsh])");
        shootPattern = Pattern.compile("^(sh|shoot) ([mo])");
        this.scanner = scanner;
        this.gui = gui;
    }

    @Override
    public void play(Game game) {
        if (game == null)
            throw new NullPointerException("Game is null");
        this.game = game;

        while (!game.isEnded()){
            if(!firstRound)
                game.reinitRound();
            firstRound = false;

            gui.displayGun(game.getGun());

            while (!game.isRoundEnded()){
                if(game.getGun().isEmpty()){
                    game.reloadGun();
                    game.generateItems();
                    gui.displayGun(game.getGun());
                }
                show();
                handleInput();
            }
            show();

            gui.displayWinner(game.getWinnerName());

            if(!game.singleMod() || game.getHumanPlayer().getLifeCount() == 0){
                game.setGameState(GameState.GAME_ENDED);
            }
            else{
                doubleOrNothing();
            }
        }

        showTotalScore();
        gui.displayLine("Thank you for playing!");
    }

    @Override
    public void show() {
        if (game == null)
            throw new NullPointerException("Game is null");

        gui.displayRepeatedLineOf("=");

        if(game.singleMod())
            gui.displayMassage("Your score: " + game.getScore() + "\n\n");

        //print players
        gui.displayMassage("Another player: ");
        gui.displayPlayerInfo(game.getNotActualPlayer());
        gui.displayRepeatedLineOf("-");
        gui.displayMassage("Turn of player: ");
        gui.displayPlayerInfo(game.getActualPlayer());
        gui.displayRepeatedLineOf("-");

        //print input rules
        if(!game.isDealerTurn())
            gui.displayInputRules();
    }

    @Override
    public void handleInput() {
        if (game == null)
            throw new NullPointerException("Game is null");

        Action action = null;
        //parse input when human player turn
        if(!game.isDealerTurn() && !game.getActualPlayer().scipTurn()){
            String input = scanner.nextLine().trim().toLowerCase();
            Matcher matcherUse = usePattern.matcher(input);
            Matcher matcherShoot = shootPattern.matcher(input);

            if(matcherUse.find())
                action = useItem(matcherUse);
            else if(matcherShoot.find())
                action = shootPlayer(matcherShoot);
            else{
                System.out.println("Wrong input!");
                return;
            }
        }
        //parse input when dealer player turn
        if(game.isDealerTurn()){
            gui.displayMassage("Press enter to see Dealer turn");
            scanner.nextLine();
        }

        gui.displayActionResult(game.playTurn(action));
    }

    private Action useItem(Matcher matcher) {
        Class<? extends Item> itemClass = null;
        switch (matcher.group(2)) {
            case "b":
                itemClass = Beer.class;
                break;
            case "m":
                itemClass = MagnifyingGlass.class;
                break;
            case "c":
                itemClass = Cigarettes.class;
                break;
            case "s":
                itemClass = Saw.class;
                break;
            case "h":
                itemClass = Handcuff.class;
                break;
            default:
                throw new UnsupportedOperationException("Unknown operation parameter: " + matcher.group(1));
        }
        return new UseItem(game, itemClass);
    }

    private Action shootPlayer(Matcher matcher) {
        boolean selfShoot;
        switch (matcher.group(2)) {
            case "m":
                selfShoot = true;
                break;
            case "o":
                selfShoot = false;
                break;
            default:
                throw new UnsupportedOperationException("Unknown operation parameter: " + matcher.group(1));
        }
        return new Shoot(game, selfShoot);
    }

    private void doubleOrNothing(){
        gui.displayMassage("Double or nothing? (y/n) ");
        String input = scanner.nextLine().toLowerCase().trim();

        while(!input.equals("y") && !input.equals("n") && !input.equals("yes") && !input.equals("no")){
            gui.displayMassage("Double or nothing? (y/n) ");
            input = scanner.nextLine().toLowerCase().trim();
        }
        if (!input.equals("y") && !input.equals("yes")){
            game.setGameState(GameState.GAME_ENDED);
        }
    }

    private void showTotalScore(){
        if(game.singleMod()){
            gui.displayLine("Your total score is: " + game.getScore());
            Player player = game.getHumanPlayer();
            List<Score> topScores = scoreService.getTopScores("buckshot roulette");

            if(player.getLifeCount() != 0){
                scoreService.addScore(new Score("buckshot roulette", player.getName(), game.getScore(), new Date()));
                if(!topScores.isEmpty()){
                    int lastScore = topScores.get(topScores.size() -1).getPoints();
                    if(game.getScore() > lastScore)
                        gui.displayLine("Welcome to leaders table!");
                }
                else {
                    gui.displayLine("Welcome to leaders table!");
                }
            }

            topScores = scoreService.getTopScores("buckshot roulette");
            gui.displayTopScores(topScores);
        }
    }
}
