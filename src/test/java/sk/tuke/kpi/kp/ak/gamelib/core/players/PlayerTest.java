package sk.tuke.kpi.kp.ak.gamelib.core.players;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Beer;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Cigarettes;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Handcuff;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testMakeDamage() {
        Player player = new Human("player", 5);
        player.makeDamage(1);
        assertEquals(4, player.getLifeCount());
        player.makeDamage(5);
        assertEquals(0, player.getLifeCount());
        player.makeDamage(1);
        assertEquals(0, player.getLifeCount());
    }

    @Test
    public void testHeal() {
        Player player = new Human("player", 5);
        player.makeDamage(1);
        assertEquals(4, player.getLifeCount());
        player.heal();
        assertEquals(5, player.getLifeCount());
        player.heal();
        assertEquals(5, player.getLifeCount());
    }

    @Test
    public void testUseItemWithFailResult() {
        Game game = new Game("first", "second");
        Player player = game.getActualPlayer();
        player.addItem(new Cigarettes());
        ActionResult result = player.useItem(Cigarettes.class, game);
        assertEquals(ActionResult.USE_ITEM_FAILED, result);
        assertEquals(0, player.getItems().size());
    }

    @Test
    public void testUseItemWithSuccessResult() {
        Game game = new Game("first", "second");
        Player player = game.getActualPlayer();
        player.addItem(new Handcuff());
        ActionResult result = player.useItem(Handcuff.class, game);
        assertEquals(ActionResult.USE_ITEM_SUCCESS, result);
        assertEquals(0, player.getItems().size());
    }

    @Test
    public void testUseItemWhenPlayerHasNoItems() {
        Game game = new Game("first", "second");
        Player player = game.getActualPlayer();
        ActionResult result = player.useItem(Beer.class, game);
        assertEquals(ActionResult.USE_ITEM_FAILED, result);
        assertEquals(0, player.getItems().size());
    }
}