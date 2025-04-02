package sk.tuke.gamestudio.game.buckshot_roulette.ui.console;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.ActionResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.ShootActionResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.SkipTurnActionResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.UseActionResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.Item;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.ItemUseResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Human;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Player;
import sk.tuke.gamestudio.game.buckshot_roulette.core.weapon.Gun;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

@Component
@NoArgsConstructor
public class ConsoleGUI {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    private void displayGameLogo() {
        System.out.println("Welcome to BuckShot Roulette!");
    }

    public void displayMenu() {
        displayGameLogo();
        System.out.print("\nMENU:\n" +
                yellow("Play\n" +
                        "Community\n" +
                        "Exit\n") + blue("\nEnter command: "));
    }

    public void displayComments(List<Comment> comments, int commentPage) {
        System.out.println("\nCOMMENTS: ");
        if (comments.isEmpty()) {
            System.out.println(blue("No comments found"));
            return;
        }

        comments.forEach(comment -> {
            displayRepeatedLineOf("-");
            System.out.println(blue("(" + comment.getCommentedOn().toString() + ") ") +
                    green(comment.getPlayer()) +
                    ":\n" + comment.getComment());
            displayRepeatedLineOf("-");
        });

        System.out.println("<Page " + (commentPage + 1) + ">\n");
    }

    public void displayTopScores(List<Score> topScores) {
        System.out.println("\nTOP SCORES:");
        if (topScores.isEmpty()) {
            System.out.println(blue("No scores found"));
            return;
        }
        topScores.forEach(score -> {
            System.out.println(blue("(" + score.getPlayedOn().toString() + ") ") +
                    yellow(score.getPlayer() + ": " + score.getPoints()));
        });
    }

    public void displayRatting(int rating) {
        System.out.println("Average rating: " + yellow("*".repeat(rating)));
    }

    public void displayGun(Gun gun) {
        displayRepeatedLineOf("*");
        System.out.printf("Gun was reloaded: " +
                        red("%d live around") +
                        " | " +
                        green("%d blank%n"),
                gun.getLiveBulletsCount(), gun.getBulletsCount() - gun.getLiveBulletsCount());
        displayRepeatedLineOf("*");
    }

    public void displayInputRules() {
        System.out.println(blue("* If you wanna use item type (u/use x) where x is an item to use\n" +
                "c - cigarettes, h - handcuff, b -beer, s - saw, m - magnifying glass\n" +
                "\n* If you wanna shoot yourself type (sh/shoot m), opponent (sh/shoot o)\n" +
                "\nEnter your turn: "));
    }

    public void displayCommunityRules() {
        System.out.println(blue("* Type (rate) to rate game or " +
                "(comment) to add comment or " +
                "(< | >) to scroll comment list or " +
                "(exit) to exit *"));
    }

    public void displayActionResult(ActionResult actionResult) {
        if (actionResult == null)
            throw new UnsupportedOperationException("No action result");

        if (actionResult instanceof SkipTurnActionResult)
            System.out.println("Player " + ((SkipTurnActionResult) actionResult).getPlayer().getName().toUpperCase() + " skipped turn");
        else if (actionResult instanceof ShootActionResult)
            displayShootActionResult((ShootActionResult) actionResult);
        else if (actionResult instanceof UseActionResult)
            displayUseItemActionResult((UseActionResult) actionResult);
        else
            throw new UnsupportedOperationException("Unsupported action result: " + actionResult.getClass().getSimpleName());
    }

    private void displayUseItemActionResult(UseActionResult actionResult) {
        if (actionResult == null)
            throw new UnsupportedOperationException("No action result");
        String playerName = actionResult.getPlayer().getName().toUpperCase();
        String itemName = actionResult.getItemClass().getSimpleName().toLowerCase();

        if (actionResult.getItemUseResult() == ItemUseResult.ERROR) {
            displayWarningMassage("Error in using item: " + itemName);
            return;
        }

        if (actionResult.getItemUseResult() == ItemUseResult.USE_ITEM_FAILED) {
            System.out.println(red("Player " + playerName + " can't use " + itemName));
            return;
        }

        System.out.println(green("Player " + playerName + " used " + itemName + " successfully"));
        if (actionResult.getItemUseResult() == ItemUseResult.BULLET_WAS_BLANK) {
            System.out.println(green("Bullet was blank"));
        } else if (actionResult.getItemUseResult() == ItemUseResult.BULLET_WAS_LIVE) {
            System.out.println(red("Bullet was live"));
        }
    }

    private void displayShootActionResult(ShootActionResult actionResult) {
        String playerName = actionResult.getPlayer().getName().toUpperCase();
        String status = actionResult.isShootResult() ? red(" hit ") : green(" missed a shot at ");
        String who = actionResult.isSelfShoot() ? yellow("himself") : yellow("the opponent");

        System.out.println("Player " + playerName + status + who);
    }

    public void displayWinner(String winnerName) {
        displayRepeatedLineOf("*");
        System.out.println(yellow("\nPlayer " + winnerName.toUpperCase() + " wins!"));
        displayRepeatedLineOf("*");
    }

    public void displayPlayerInfo(Player player) {
        if (player == null)
            throw new UnsupportedOperationException("Player is null");

        System.out.println(player.getName().toUpperCase());
        if (player instanceof Human) {
            if (player.getLifeCount() > 0)
                System.out.println(green("\n  _---_\n" +
                        " ( o_o ) \n" +
                        " / >-< \\ \n"));
            else
                System.out.println(blue("\n  _---_\n" +
                        " ( x_x ) \n" +
                        " / >-< \\ \n"));
        } else {
            if (player.getLifeCount() > 0)
                System.out.println(red("\n  /\\_/\\\n" +
                        " ( o.o )\n" +
                        " / >^< \\\n"));
            else
                System.out.println(blue("\n  /\\_/\\\n" +
                        " ( x.x )\n" +
                        " / >^< \\\n"));
        }
        System.out.printf("Life count: " +
                red("%d") +
                "\nItems:\n", player.getLifeCount());
        System.out.println(yellow(String.join("\n", player.getItems().stream()
                .map(Item::toString)
                .toArray(String[]::new))));
    }

    public void displayWarningMassage(String message) {
        System.out.println(red(message));
    }

    public void displayRuleMessage(String message) {
        System.out.println(blue(message));
    }

    public void displayInfoMessage(String message) {
        System.out.println(yellow(message));
    }

    public void displayRepeatedLineOf(String string) {
        System.out.println(string.repeat(40));
    }

    public void displayDefaultMassage(String message) {
        System.out.print(message);
    }

    private String yellow(String string) {
        return YELLOW + string + RESET;
    }

    private String red(String string) {
        return RED + string + RESET;
    }

    private String green(String string) {
        return GREEN + string + RESET;
    }

    private String blue(String string) {
        return BLUE + string + RESET;
    }
}
