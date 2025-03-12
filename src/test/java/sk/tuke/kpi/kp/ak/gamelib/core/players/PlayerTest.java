package sk.tuke.kpi.kp.ak.gamelib.core.players;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.items.ItemUseResult;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Beer;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Cigarettes;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Handcuff;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testGetDamage() {
        Player player = new Human("player", 5);
        player.getDamage(1);
        assertEquals(4, player.getLifeCount());
        player.getDamage(5);
        assertEquals(0, player.getLifeCount());
        player.getDamage(1);
        assertEquals(0, player.getLifeCount());
    }

    @Test
    public void testHeal() {
        Player player = new Human("player", 5);
        player.getDamage(1);
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
        int itemsCountBefore = player.getItems().size();
        ItemUseResult result = player.useItem(Cigarettes.class, game);
        assertEquals(ItemUseResult.USE_ITEM_FAILED, result);
        assertEquals(itemsCountBefore, player.getItems().size());
    }

    @Test
    public void testUseItemWithSuccessResult() {
        Game game = new Game("first", "second");
        Player player = game.getActualPlayer();
        player.addItem(new Handcuff());
        int itemsCountBefore = player.getItems().size();
        ItemUseResult result = player.useItem(Handcuff.class, game);
        assertEquals(ItemUseResult.USE_ITEM_SUCCESS, result);
        assertEquals(itemsCountBefore - 1, player.getItems().size());
    }

    @Test
    public void testUseItemWhenPlayerHasNoItems() {
        Game game = new Game("first", "second");
        Player player = game.getActualPlayer();
        while (!player.getItems().isEmpty()) {
            player.getItems().remove(0);
        }
        ItemUseResult result = player.useItem(Beer.class, game);
        assertEquals(ItemUseResult.USE_ITEM_FAILED, result);
        assertEquals(0, player.getItems().size());
    }
}