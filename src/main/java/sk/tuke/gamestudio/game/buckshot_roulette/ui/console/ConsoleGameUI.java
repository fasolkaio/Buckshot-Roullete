package sk.tuke.gamestudio.game.buckshot_roulette.ui.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.GameState;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.Action;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.Shoot;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.UseItem;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.*;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Player;
import sk.tuke.gamestudio.game.buckshot_roulette.ui.GameUI;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleGameUI implements GameUI {
    private Game game;
    private boolean firstRound = true;

    //output/input utilities
    @Autowired
    private ConsoleGUI gui;
    @Autowired
    private Scanner scanner;

    //services
    @Autowired
    private ScoreService scoreService;

    //command patterns
    private final Pattern usePattern;
    private final Pattern shootPattern;

    public ConsoleGameUI() {
        usePattern = Pattern.compile("^(u|use) ([bcmsh])");
        shootPattern = Pattern.compile("^(sh|shoot) ([mo])");
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
        gui.displayInfoMessage("Thank you for playing!");
    }

    @Override
    public void show() {
        if (game == null)
            throw new NullPointerException("Game is null");

        gui.displayRepeatedLineOf("=");

        if(game.singleMod())
            gui.displayInfoMessage("Your score: " + game.getScore() + "\n");

        //print players
        gui.displayDefaultMassage("Another player: ");
        gui.displayPlayerInfo(game.getNotActualPlayer());
        gui.displayRepeatedLineOf("-");
        gui.displayDefaultMassage("Turn of player: ");
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
                gui.displayWarningMassage("Wrong input!");
                return;
            }
        }
        //parse input when dealer player turn
        if(game.isDealerTurn()){
            gui.displayRuleMessage("Press enter to see Dealer turn");
            scanner.nextLine();
        }

        gui.displayActionResult(game.playTurn(action));
    }

    private Action useItem(Matcher matcher) {
        Class<? extends Item> itemClass = switch (matcher.group(2)) {
            case "b" -> Beer.class;
            case "m" -> MagnifyingGlass.class;
            case "c" -> Cigarettes.class;
            case "s" -> Saw.class;
            case "h" -> Handcuff.class;
            default -> throw new UnsupportedOperationException("Unknown operation parameter: " + matcher.group(1));
        };
        return new UseItem(game, itemClass);
    }

    private Action shootPlayer(Matcher matcher) {
        boolean selfShoot = switch (matcher.group(2)) {
            case "m" -> true;
            case "o" -> false;
            default -> throw new UnsupportedOperationException("Unknown operation parameter: " + matcher.group(1));
        };
        return new Shoot(game, selfShoot);
    }

    private void doubleOrNothing(){
        gui.displayInfoMessage("Double or nothing? (y/n) ");
        String input = scanner.nextLine().toLowerCase().trim();

        while(!input.equals("y") && !input.equals("n") && !input.equals("yes") && !input.equals("no")){
            gui.displayInfoMessage("Double or nothing? (y/n) ");
            input = scanner.nextLine().toLowerCase().trim();
        }
        if (!input.equals("y") && !input.equals("yes")){
            game.setGameState(GameState.GAME_ENDED);
        }
    }

    private void showTotalScore(){
        if(game.singleMod()){
            gui.displayInfoMessage("Your total score is: " + game.getScore());
            Player player = game.getHumanPlayer();
            List<Score> topScores = scoreService.getTopScores("buckshot roulette");

            if(player.getLifeCount() != 0){
                scoreService.addScore(new Score("buckshot roulette", player.getName(), game.getScore(), new Date()));
                if(!topScores.isEmpty()){
                    int lastScore = topScores.get(topScores.size() -1).getPoints();
                    if(game.getScore() > lastScore)
                        gui.displayInfoMessage("Welcome to leaders table!");
                }
                else {
                    gui.displayInfoMessage("Welcome to leaders table!");
                }
            }

            topScores = scoreService.getTopScores("buckshot roulette");
            gui.displayTopScores(topScores);
        }
    }
}
