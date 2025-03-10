package sk.tuke.kpi.kp.ak.gamelib.core.items;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

import static org.junit.jupiter.api.Assertions.*;

public class CigarettesTest {
    @Test
    public void nullTest(){
        Item cigarettes = new Cigarettes();
        assertThrows(UnsupportedOperationException.class, () -> cigarettes.useItem(null));
    }
    @Test
    public void testUse(){
        Game game = new Game("first", "second");
        Item cigarettes = new Cigarettes();
        Player actual = game.getActualPlayer();
        actual.makeDamage(1);
        int lives = actual.getLifeCount();
        boolean result = cigarettes.useItem(game);
        assertTrue(result);
        assertEquals(lives + 1, actual.getLifeCount());
    }
    @Test
    public void testUseToFull(){
        Game game = new Game("first", "second");
        Item cigarettes = new Cigarettes();
        Player actual = game.getActualPlayer();
        int lives = actual.getLifeCount();
        boolean result = cigarettes.useItem(game);
        assertFalse(result);
        assertEquals(lives, actual.getLifeCount());
    }
}