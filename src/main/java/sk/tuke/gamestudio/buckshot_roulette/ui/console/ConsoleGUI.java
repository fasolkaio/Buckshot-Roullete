package sk.tuke.gamestudio.buckshot_roulette.ui.console;

import lombok.AllArgsConstructor;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.ActionResult;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.ShootActionResult;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.SkipTurnActionResult;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.UseActionResult;
import sk.tuke.gamestudio.buckshot_roulette.core.items.Item;
import sk.tuke.gamestudio.buckshot_roulette.core.items.ItemUseResult;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Human;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Player;
import sk.tuke.gamestudio.buckshot_roulette.core.weapon.Gun;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

@AllArgsConstructor
public class ConsoleGUI {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    private void displayGameLogo(){
        System.out.println(BLUE + "Welcome to BuckShot Roulette!" + RESET);
    }

    public void displayMenu(){
        displayGameLogo();
        System.out.print( YELLOW + "\nMENU:\n" + RESET +
                "Play\n" +
                "Comment\n" +
                "Rate\n" +
                "Exit\n" +
                BLUE + "\nEnter command: " + RESET);
    }

    public void displayGun(Gun gun) {
        displayRepeatedLineOf("*");
        System.out.printf("Gun was reloaded: " +
                RED +"%d live around" + RESET +
                " | " +
                GREEN + "%d blank%n" + RESET,
                gun.getLiveBulletsCount(), gun.getBulletsCount() - gun.getLiveBulletsCount());
        displayRepeatedLineOf("*");
    }

    public void displayComments(List<Comment> comments) {
        comments.forEach(comment -> {
            displayRepeatedLineOf("-");
            System.out.println(BLUE + "(" + comment.getCommentedOn().toString() + ") " + RESET + GREEN + comment.getPlayer() + RESET + ":\n" + comment.getComment());
            displayRepeatedLineOf("-");
        });
    }

    public void displayTopScores(List<Score> topScores) {
        System.out.println(YELLOW + "\nTOP SCORES:" + RESET);
        topScores.forEach(score -> {
            System.out.println(BLUE + "(" + score.getPlayedOn().toString() + ") " + RESET + YELLOW + score.getPlayer() + ": " + score.getPoints() + RESET);
        });
    }

    public void displayInputRules() {
        System.out.print(BLUE + "* If you wanna use item type (u/use x) where x is an item to use\n"+
                "c - cigarettes, h - handcuff, b -beer, s - saw, m - magnifying glass\n"+
                "\n* If you wanna shoot yourself type (sh/shoot m), opponent (sh/shoot o)\n" +
                "\nEnter your turn: " + RESET);
    }

    public void displayCommentRules(){
        System.out.println(BLUE + "* Type (comment) to add comment or " +
                "type (< | >) to scroll comment list or " +
                "type (exit) to exit *" + RESET);
    }

    public void displayRatingRules() {
        System.out.println(BLUE + "* Type (rate) to rate game or " +
                "type (exit) to exit *" + RESET);
    }

    public void displayActionResult(ActionResult actionResult) {
        if(actionResult == null)
            throw new UnsupportedOperationException("No action result");

        if(actionResult instanceof SkipTurnActionResult)
            System.out.println("Player " + ((SkipTurnActionResult) actionResult).getPlayer().getName().toUpperCase() + " skipped turn");
        else if(actionResult instanceof ShootActionResult)
            displayShootActionResult((ShootActionResult) actionResult);
        else if(actionResult instanceof UseActionResult)
            displayUseItemActionResult((UseActionResult) actionResult);
        else
            throw new UnsupportedOperationException("Unsupported action result: " + actionResult.getClass().getSimpleName());
    }

    private void displayUseItemActionResult(UseActionResult actionResult) {
        if(actionResult == null)
            throw new UnsupportedOperationException("No action result");
        String playerName = actionResult.getPlayer().getName().toUpperCase();
        String itemName = actionResult.getItemClass().getSimpleName().toLowerCase();

        if(actionResult.getItemUseResult() == ItemUseResult.ERROR) {
            System.out.println(RED + "Error in using item: " + itemName + RESET);
            return;
        }

        if(actionResult.getItemUseResult() == ItemUseResult.USE_ITEM_FAILED){
            System.out.println(RED + "Player " + playerName + " can't use " + itemName + RESET);
            return;
        }

        System.out.println(GREEN + "Player " + playerName + " used " + itemName + " successfully" + RESET);
        if(actionResult.getItemUseResult() == ItemUseResult.BULLET_WAS_BLANK){
            System.out.println(GREEN + "Bullet was blank" + RESET);
        }
        else if(actionResult.getItemUseResult() == ItemUseResult.BULLET_WAS_LIVE){
            System.out.println(RED + "Bullet was live"+ RESET);
        }
    }

    private void displayShootActionResult(ShootActionResult actionResult) {
        String playerName = actionResult.getPlayer().getName().toUpperCase();
        String status = actionResult.isShootResult() ? (RED + " hit " + RESET) : (GREEN + " missed a shot at " + RESET);
        String who = actionResult.isSelfShoot() ? "himself" : "the opponent";

        System.out.println("Player " + playerName  + status + who);
    }

    public void displayWinner(String winnerName) {
        displayRepeatedLineOf("*");
        System.out.println(YELLOW + "\nPlayer " + winnerName.toUpperCase() + " wins!" + RESET);
        displayRepeatedLineOf("*");
    }

    public void displayPlayerInfo(Player player) {
        if (player == null)
            throw new UnsupportedOperationException("Player is null");

        System.out.println(player.getName().toUpperCase());
        if(player instanceof Human){
            if(player.getLifeCount() > 0)
                System.out.println(GREEN +"\n  _---_\n" +
                        " ( o_o ) \n" +
                        " / >-< \\ \n" + RESET);
            else
                System.out.println(BLUE + "\n  _---_\n" +
                        " ( x_x ) \n" +
                        " / >-< \\ \n" + GREEN);
        }
        else{
            if(player.getLifeCount() > 0)
                System.out.println(RED + "\n  /\\_/\\\n" +
                        " ( o.o )\n" +
                        " / >^< \\\n" + RESET);
            else
                System.out.println(BLUE + "\n  /\\_/\\\n" +
                        " ( x.x )\n" +
                        " / >^< \\\n" + RESET);
        }
        System.out.printf("Life count: " +
                RED + "%d" + RESET +
                "\nItems:\n", player.getLifeCount());
        System.out.println(YELLOW + String.join("\n", player.getItems().stream()
                .map(Item::toString)
                .toArray(String[]::new)) + RESET);
    }

    public void displayRepeatedLineOf(String string) {
        System.out.println("\n" + string.repeat(40) + "\n");
    }

    public void displayMassage(String message) {
        System.out.print(message);
    }

    public void displayLine(String line) {
        System.out.println(line);
    }
}
