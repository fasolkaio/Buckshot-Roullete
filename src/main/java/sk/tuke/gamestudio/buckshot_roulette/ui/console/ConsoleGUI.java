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

    private void displayGameLogo(){
        System.out.println("Welcome to BuckShot Roulette!");
    }

    public void displayMenu(){
        displayGameLogo();
        System.out.print("\nMENU:\n" +
                "Play\n" +
                "Comment\n" +
                "Rate\n" +
                "Exit\n" +
                "\nEnter command: ");
    }

    public void displayGun(Gun gun) {
        displayRepeatedLineOf("*");
        System.out.printf("Gun was reloaded: %d live around | %d blank%n", gun.getLiveBulletsCount(), gun.getBulletsCount() - gun.getLiveBulletsCount());
        displayRepeatedLineOf("*");
    }

    public void displayComments(List<Comment> comments) {
        comments.forEach(comment -> {
            displayRepeatedLineOf("-");
            System.out.println("(" + comment.getCommentedOn().toString() + ") " + comment.getPlayer() + ":\n" + comment.getComment());
            displayRepeatedLineOf("-");
        });
    }

    public void displayTopScores(List<Score> topScores) {
        System.out.println("\nTOP SCORES:");
        topScores.forEach(score -> {
            System.out.println("(" + score.getPlayedOn().toString() + ") " + score.getPlayer() + ": " + score.getPoints());
        });
    }

    public void displayInputRules() {
        System.out.print("* If you wanna use item type (u/use x) where x is an item to use\n"+
                "c - cigarettes, h - handcuff, b -beer, s - saw, m - magnifying glass\n"+
                "\n* If you wanna shoot yourself type (sh/shoot m), opponent (sh/shoot o)\n" +
                "\nEnter your turn: ");
    }

    public void displayCommentRules(){
        System.out.println("* Type (comment) to add comment or " +
                "type (< | >) to scroll comment list or " +
                "type (exit) to exit *");
    }

    public void displayRatingRules() {
        System.out.println("* Type (rate) to rate game or " +
                "type (exit) to exit *");
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
            System.out.println("Error in using item: " + itemName);
            return;
        }

        if(actionResult.getItemUseResult() == ItemUseResult.USE_ITEM_FAILED){
            System.out.println("Player " + playerName + " can't use " + itemName);
            return;
        }

        System.out.println("Player " + playerName + " used " + itemName + " successfully");
        if(actionResult.getItemUseResult() == ItemUseResult.BULLET_WAS_BLANK){
            System.out.println("Bullet was blank");
        }
        else if(actionResult.getItemUseResult() == ItemUseResult.BULLET_WAS_LIVE){
            System.out.println("Bullet was live");
        }
    }

    private void displayShootActionResult(ShootActionResult actionResult) {
        String playerName = actionResult.getPlayer().getName().toUpperCase();
        String status = actionResult.isShootResult() ? " hit " : " missed a shot at ";
        String who = actionResult.isSelfShoot() ? "himself" : "the opponent";

        System.out.println("Player " + playerName  + status + who);
    }

    public void displayWinner(String winnerName) {
        displayRepeatedLineOf("*");
        System.out.println("\nPlayer " + winnerName.toUpperCase() + " wins!");
        displayRepeatedLineOf("*");
    }

    public void displayPlayerInfo(Player player) {
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
                        " / >^< \\\n");
        }
        System.out.printf("Life count: %d\nItems:\n", player.getLifeCount());
        System.out.println(String.join("\n", player.getItems().stream()
                .map(Item::toString)
                .toArray(String[]::new)));
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
