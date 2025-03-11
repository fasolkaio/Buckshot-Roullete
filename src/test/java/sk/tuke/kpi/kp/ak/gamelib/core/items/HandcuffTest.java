package sk.tuke.kpi.kp.ak.gamelib.core.items;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

import static org.junit.jupiter.api.Assertions.*;

public class HandcuffTest {
    @Test
    public void nullTest(){
        Item handcuff = new Handcuff();
        assertThrows(UnsupportedOperationException.class, () -> handcuff.useItem(null));
    }
    @Test
    public void testUse(){
        Game game = new Game(false,"first", "second");
        Item handcuff = new Handcuff();
        Player notActual = game.getActualPlayer();
        boolean before = notActual.scipTurn();
        ActionResult result = handcuff.useItem(game);
        if(!before) {
            assertEquals(ActionResult.USE_ITEM_SUCCESS, result);
            assertTrue(game.getActualPlayer().scipTurn());
        }
        else {
            assertEquals(ActionResult.USE_ITEM_FAILED, result);
            assertTrue(game.getActualPlayer().scipTurn());
        }
    }
}