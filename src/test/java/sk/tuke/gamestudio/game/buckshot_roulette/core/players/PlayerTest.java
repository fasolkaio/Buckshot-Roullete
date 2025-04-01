package sk.tuke.gamestudio.game.buckshot_roulette.core.players;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.ItemUseResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.Beer;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.Cigarettes;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.Handcuff;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Human;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Player;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
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
    public void testCuff() {
        Player player = new Human("player", 5);
        assertFalse(player.scipTurn());
        assertTrue(player.cuff());
        assertTrue(player.scipTurn());
        assertFalse(player.cuff());
        assertTrue(player.scipTurn());
    }

    @Test
    public void testAddItem(){
        Player player = new Human("player", 5);
        for(int i = 0; i < 8; i ++){
            player.addItem(new Beer());
        }
        assertEquals(8, player.getItems().size());
        player.addItem(new Beer());
        assertEquals(8, player.getItems().size());
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