package sk.tuke.kpi.kp.ak.gamelib.ui;

import lombok.AllArgsConstructor;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.items.ItemUseResult;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Item;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Human;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

@AllArgsConstructor
public class ConsoleGUI {
    Game game;

    public void printPlayerInfo(Player player) {
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

    public void showGun(Gun gun) {
        printRepeatedLineOf("*");
        System.out.printf("Gun was reloaded: %d live around | %d blank%n", gun.getLiveBulletsCount(), gun.getBulletsCount() - gun.getLiveBulletsCount());
        printRepeatedLineOf("*");
    }

    public void printActionResult(ActionResult actionResult) {

    }

    public void printRepeatedLineOf(String string) {
        System.out.println("\n" + string.repeat(40) + "\n");
    }

    public void printInputRules() {
        System.out.print("* If you wanna use item type (u/use x) where x is an item to use\n"+
                "c - cigarettes, h - handcuff, b -beer, s - saw, m - magnifying glass\n"+
                "\n* If you wanna shoot yourself type (sh/shoot m), opponent (sh/shoot o)\n" +
                "\nEnter your turn: ");
    }

}
