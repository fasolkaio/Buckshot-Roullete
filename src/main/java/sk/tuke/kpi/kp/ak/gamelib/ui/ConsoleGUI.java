package sk.tuke.kpi.kp.ak.gamelib.ui;

import lombok.AllArgsConstructor;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.items.ItemUseResult;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Item;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Human;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

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

    public void printRepeatedLineOf(String string) {
        System.out.println("\n" + string.repeat(40) + "\n");
    }

    public void printInputRules() {
        System.out.print("* If you wanna use item type (u/use x) where x is an item to use\n"+
                "c - cigarettes, h - handcuff, b -beer, s - saw, m - magnifying glass\n"+
                "\n* If you wanna shoot yourself type (sh/shoot m), opponent (sh/shoot o)\n" +
                "\nEnter your turn: ");
    }

    public void printUseActionResult(ItemUseResult result) {
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

    public void printShootResult(String shooter, boolean selfshoot, ItemUseResult result) {
        String shooted = selfshoot ? "himself" : "opponent";

//        switch (result) {
//            case HIT_SUCCESS:
//                System.out.println(shooter + " shooted "+ shooted + " succesful");
//                break;
//            case HIT_FAILED:
//                System.out.println(shooter + " shooted " + shooted + " failure");
//                break;
//            default:
//                throw new UnsupportedOperationException("Unknown operation result: " + result);
//        }

    }

    public void printDealerActionResult(ItemUseResult ItemUseResult) {
//        switch(ItemUseResult){
//            case HIT_FAILED:
//                System.out.println("Dealer shoot failed");
//                break;
//            case HIT_SUCCESS:
//                System.out.println("Dealer shoot successful");
//                break;
//            case USE_ITEM_SUCCESS:
//                System.out.println("Dealer use item successful");
//                break;
//            case USE_ITEM_FAILED:
//                System.out.println("Dealer use item failed");
//                break;
//            case BULLET_WAS_LIVE:
//                System.out.println("Dealer bullet was live");
//                break;
//            case BULLET_WAS_BLANK:
//                System.out.println("Dealer bullet was blank");
//                break;
//            default:
//                throw new UnsupportedOperationException("Unsupported action result: " + ItemUseResult.toString());
//        }
    }
}
