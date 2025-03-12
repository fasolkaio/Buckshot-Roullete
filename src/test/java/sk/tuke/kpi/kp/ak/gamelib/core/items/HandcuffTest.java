package sk.tuke.kpi.kp.ak.gamelib.core.items;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

import static org.junit.jupiter.api.Assertions.*;

public class HandcuffTest {
    @Test
    public void nullTest(){
        Item handcuff = new Handcuff();
        assertThrows(UnsupportedOperationException.class, () -> handcuff.useItem(null));
    }
    @Test
    public void testUseItem(){
        Game game = new Game("first", "second");
        Item handcuff = new Handcuff();
        Player notActual = game.getNotActualPlayer();
        ItemUseResult result1 = handcuff.useItem(game);

        assertEquals(ItemUseResult.USE_ITEM_SUCCESS, result1);
        assertTrue(notActual.scipTurn());

        ItemUseResult result2 = handcuff.useItem(game);

        assertEquals(ItemUseResult.USE_ITEM_FAILED, result2);
        assertTrue(notActual.scipTurn());

    }
}