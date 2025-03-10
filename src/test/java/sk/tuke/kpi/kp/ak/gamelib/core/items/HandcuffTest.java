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
    public void testUse(){
        Game game = new Game("first", "second");
        Item handcuff = new Handcuff();
        Player notActual = game.getActualPlayer();
        boolean before = notActual.scipTurn();
        boolean result = handcuff.useItem(game);
        if(!before) {
            assertTrue(result);
            assertTrue(game.getActualPlayer().scipTurn());
        }
        else {
            assertFalse(result);
            assertTrue(game.getActualPlayer().scipTurn());
        }
    }
}