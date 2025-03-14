package sk.tuke.kpi.kp.ak.gamelib.ui;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.GameMode;
import sk.tuke.kpi.kp.ak.gamelib.core.GameState;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Shoot;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.UseItem;
import sk.tuke.kpi.kp.ak.gamelib.core.items.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements GameUI{
    private boolean firstRound;
    private GameMode gameMode;

    private final Game game;

    //input|output utilities
    private final ConsoleGUI gui;
    private final Scanner scanner;

    private final Pattern usePattern;
    private final Pattern shootPattern;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
        firstRound = true;
        usePattern = Pattern.compile("(u|use) ([bcmsh])");
        shootPattern = Pattern.compile("(sh|shoot) ([mo])");
        gui = new ConsoleGUI();
        gui.printGameLogo();
        setGameMode();
        switch(gameMode) {
            case Single:
                game = new Game(askName());
                break;
            case P2P:
                game = new Game(askName(), askName());
                break;
            case Testing:
                game = new Game(gameMode);
                break;
            case B2B:
                game = new Game(gameMode);
            default:
                throw new IllegalArgumentException("Unsupported game mode " + gameMode);
        }
    }

    @Override
    public void play() {
        if (game == null)
            throw new NullPointerException("Game is null");

        while (!game.isEnded()){
            if(!firstRound)
                game.reinitRound();
            firstRound = false;

            gui.showGun(game.getGun());

            while (!game.isRoundEnded()){
                if(game.getGun().isEmpty()){
                    game.reloadGun();
                    game.generateItems();
                    gui.showGun(game.getGun());
                }
                show();
                handleInput();
                System.out.println(game.getGameState().toString());
            }
            show();
            gui.printWinner(game.getWinnerName());

            if(game.getGameMode() != GameMode.Single || !doubleOrNothing()){
                game.setGameState(GameState.GAME_ENDED);
            }
        }

        gui.printMassage("Thank you for playing!\n");
    }


    @Override
    public void show() {
        if (game == null)
            throw new NullPointerException("Game is null");

        gui.printRepeatedLineOf("=");

        //print players
        gui.printMassage("Turn of player: ");
        gui.printPlayerInfo(game.getActualPlayer());
        gui.printRepeatedLineOf("-");
        gui.printMassage("Another player: ");
        gui.printPlayerInfo(game.getNotActualPlayer());
        gui.printRepeatedLineOf("-");

        //print input rules
        if(!game.isDealerTurn())
            gui.printInputRules();
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
        //pare input when dealer player turn
        if(game.isDealerTurn()){
            gui.printMassage("Press enter to see Dealer turn");
            scanner.nextLine();
        }

        gui.printActionResult(game.playTurn(action));
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

    private boolean doubleOrNothing(){
        gui.printMassage("Double or nothing? (y/n) ");
        String input = scanner.nextLine().toLowerCase();

        while(!input.equals("y") && !input.equals("n") && !input.equals("yes") && !input.equals("no")){
            gui.printMassage("Double or nothing? (y/n) ");
            input = scanner.nextLine().toLowerCase();
        }
        return input.equals("y") || input.equals("yes");
    }

    private String askName(){
        gui.printMassage("Please enter your name: ");
        return scanner.nextLine();
    }

    private void setGameMode(){
        gui.printMassage("Do you wanna play solo? (y/n) ");
        String input = scanner.nextLine().toLowerCase();

        while(!input.equals("y") && !input.equals("n") && !input.equals("yes") && !input.equals("no") && !input.equals("t") && !input.equals("b")){
            gui.printMassage("Do you wanna play solo? (y/n) ");
            input = scanner.nextLine().toLowerCase();
        }

        if(input.equals("y") || input.equals("yes")){
            gameMode = GameMode.Single;
        }
        else if(input.equals("n") || input.equals("no")){
            gameMode = GameMode.P2P;
        }
        else{
            gameMode = GameMode.Testing;
        }
    }
}
