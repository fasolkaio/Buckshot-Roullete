package sk.tuke.kpi.kp.ak.gamelib.ui;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.GameState;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.items.ItemUseResult;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Shoot;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.UseItem;
import sk.tuke.kpi.kp.ak.gamelib.core.items.*;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements GameUI{
    private final Game game;
    private final ConsoleGUI gui;
    private final Scanner scanner;
    private boolean firstRound;
    private final Pattern usePattern;
    private final Pattern shootPattern;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
        firstRound = true;
        usePattern = Pattern.compile("(u|use) ([bcmsh])");
        shootPattern = Pattern.compile("(sh|shoot) ([mo])");
        printGameLogo();
        boolean soloGame = isGameSolo();
        if(soloGame){
            game = new Game(askName());
        }
        else{
            game = new Game(askName(), askName());
        }
        gui = new ConsoleGUI(game);
    }

    @Override
    public void play() {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");

        while (!game.isEnded()){
            if(!firstRound)
                game.reinitRound();
            firstRound = false;

            gui.showGun(game.getGun());

            while (!game.isRoundEnded()){
                if(game.getGun().isEmpty()){
                    game.reloadGun();
                    gui.showGun(game.getGun());
                }
                show();
                handleInput();
            }
            show();

            if(!(game.isSingleGame() && doubleOrNothing())){
                game.setGameState(GameState.GAME_ENDED);
            }
        }
    }


    @Override
    public void show() {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");
        gui.printRepeatedLineOf("=");

        //print players
        System.out.print("Turn of player: ");
        gui.printPlayerInfo(game.getActualPlayer());
        gui.printRepeatedLineOf("-");
        System.out.print("Another player: ");
        gui.printPlayerInfo(game.getNotActualPlayer());
        gui.printRepeatedLineOf("-");

        //print input rules
        if(!game.isBotTurn())
            gui.printInputRules();
    }

    @Override
    public void handleInput() {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");
        Action action = null;
        if(!game.isBotTurn()){
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
        System.out.print("Double or nothing? (y/n) ");
        String input = scanner.nextLine().toLowerCase();

        while(!input.equals("y") && !input.equals("n") && !input.equals("yes") && !input.equals("no")){
            System.out.print("Double or nothing? (y/n) ");
            input = scanner.nextLine().toLowerCase();
        }
        return input.equals("y") || input.equals("yes");
    }

    private String askName(){
        System.out.print("Please enter your name: ");
        return scanner.nextLine();
    }

    private boolean isGameSolo(){
        System.out.print("Do you wanna play solo? (y/n) ");
        String input = scanner.nextLine().toLowerCase();

        while(!input.equals("y") && !input.equals("n") && !input.equals("yes") && !input.equals("no")){
            System.out.print("Do you wanna play solo? (y/n) ");
            input = scanner.nextLine().toLowerCase();
        }
        return input.equals("y") || input.equals("yes");
    }

    private void printGameLogo(){
        System.out.println("Welcome to BuckShot Roulette!");
    }
}
