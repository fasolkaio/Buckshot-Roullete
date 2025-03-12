package sk.tuke.kpi.kp.ak.gamelib.ui;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.GameState;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Shoot;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.UseItem;
import sk.tuke.kpi.kp.ak.gamelib.core.items.*;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Human;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements GameUI{
    private final Game game;
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
    }

    @Override
    public void play() {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");

        while (!game.isEnded()){
            if(!firstRound)
                game.reinitRound();
            firstRound = false;

            showGun(game.getGun());
            while (!game.isRoundEnded()){
                show();
                if(game.isBotTurn())
                    printDealerActionResult(game.playTurn(null));
                else
                    handleInput();
            }

            if(!(game.isSingleGame() && doubleOrNothing())){
                game.setGameState(GameState.GAME_ENDED);
            }
        }
    }


    @Override
    public void show() {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");
        printRepeatedLineOf("=");

        //print players
        System.out.print("Turn of player: ");
        printPlayerInfo(game.getActualPlayer());
        printRepeatedLineOf("-");
        System.out.print("Another player: ");
        printPlayerInfo(game.getNotActualPlayer());
        printRepeatedLineOf("-");

        //print input rules
        if(!game.isBotTurn())
            printInputRules();
    }

    @Override
    public void handleInput() {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");
        String input = scanner.nextLine().trim().toLowerCase();

        Matcher matcherUse = usePattern.matcher(input);
        Matcher matcherShoot = shootPattern.matcher(input);

        if(matcherUse.find())
            useItem(matcherUse);
        else if(matcherShoot.find())
            shootPlayer(matcherShoot);
        else{
            System.out.println("Wrong input!");
        }
    }

    private void useItem(Matcher matcher) {

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

        Action action = new UseItem(game, itemClass);
        ActionResult result = game.playTurn(action);
        printUseActionResult(result);
    }

    private void shootPlayer(Matcher matcher) {
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

        Player actualPlayer = game.getActualPlayer();
        Action action = new Shoot(selfShoot, game);
        ActionResult result = game.playTurn(action);
        printShootResult(actualPlayer.getName(), selfShoot, result);
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

    private void showGun(Gun gun) {
        printRepeatedLineOf("*");
        System.out.printf("Gun was reloaded: %d live around | %d blank%n", gun.getLiveBulletsCount(), gun.getBulletsCount() - gun.getLiveBulletsCount());
        printRepeatedLineOf("*");
    }

    private void printGameLogo(){
        System.out.println("Welcome to BuckShot Roulette!");
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

    private void printPlayerInfo(Player player) {
        if (player == null)
            throw new UnsupportedOperationException("Player is null");

        System.out.println(player.getName().toUpperCase());
        if(player instanceof Human){
            if(player.getLifeCount() > 0)
                System.out.println("\n  _---_\n" +
                        " ( o_o ) \n" +
                        " / >-< \\ \n");
            else
                System.out.println("\n  _---_\n" +
                        " ( x_x ) \n" +
                        " / >-< \\ \n");
        }
        else{
            if(player.getLifeCount() > 0)
                System.out.println("\n  /\\_/\\\n" +
                        " ( o.o )\n" +
                        " / >^< \\\n");
            else
                System.out.println("\n  /\\_/\\\n" +
                        " ( x.x )\n" +
                        " / >^<\\\n");
        }
        System.out.printf("Life count: %d\nItems:\n", player.getLifeCount());
        System.out.println(String.join("\n", player.getItems().stream()
                .map(Item::toString)
                .toArray(String[]::new)));
    }

    private void printRepeatedLineOf(String string) {
        System.out.println("\n" + string.repeat(40) + "\n");
    }

    private void printInputRules() {
        System.out.print("* If you wanna use item type (u/use x) where x is an item to use\n"+
                "c - cigarettes, h - handcuff, b -beer, s - saw, m - magnifying glass\n"+
                "\n* If you wanna shoot yourself type (sh/shoot m), opponent (sh/shoot o)\n" +
                "\nEnter your turn: ");
    }

    private void printUseActionResult(ActionResult result) {
        switch (result){
            case BULLET_WAS_BLANK:
                System.out.println("Bullet was blank!");
                break;
            case BULLET_WAS_LIVE:
                System.out.println("Bullet was live!");
                break;
            case USE_ITEM_SUCCESS:
                System.out.println("You have successfully used the item!");
                break;
            case USE_ITEM_FAILED:
                System.out.println("You have failed to use the item!");
                break;
            default:
                throw new UnsupportedOperationException("Unsupported action result: " + result);
        }
    }

    private void printShootResult(String shooter, boolean selfshoot, ActionResult result) {
        String shooted = selfshoot ? "himself" : "opponent";

        switch (result) {
            case HIT_SUCCESS:
                System.out.println(shooter + " shooted "+ shooted + " succesful");
                break;
            case HIT_FAILED:
                System.out.println(shooter + " shooted " + shooted + " failure");
                break;
            default:
                throw new UnsupportedOperationException("Unknown operation result: " + result);
        }

    }

    private void printDealerActionResult(ActionResult actionResult) {
        switch(actionResult){
            case HIT_FAILED:
                System.out.println("Dealer shoot failed");
                break;
            case HIT_SUCCESS:
                System.out.println("Dealer shoot successful");
                break;
            case USE_ITEM_SUCCESS:
                System.out.println("Dealer use item successful");
                break;
            case USE_ITEM_FAILED:
                System.out.println("Dealer use item failed");
                break;
            case BULLET_WAS_LIVE:
                System.out.println("Dealer bullet was live");
                break;
            case BULLET_WAS_BLANK:
                System.out.println("Dealer bullet was blank");
                break;
            default:
                break;
        }
    }
}
