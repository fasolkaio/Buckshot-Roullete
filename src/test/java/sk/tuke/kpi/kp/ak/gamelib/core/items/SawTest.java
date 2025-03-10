package sk.tuke.kpi.kp.ak.gamelib.core.items;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;

import static org.junit.jupiter.api.Assertions.*;

public class SawTest {
    @Test
    public void nullTest(){
        Item saw = new Saw();
        assertThrows(UnsupportedOperationException.class, () -> saw.useItem(null));
    }
    @Test
    public void testUse(){
        Game game = new Game("first player", "second player");
        Item saw = new Saw();
        int damageBefore = game.getGun().getDamage();
        boolean result = saw.useItem(game);
        if(result)
            assertEquals(damageBefore * 2, game.getGun().getDamage());
        else
            assertEquals(damageBefore, game.getGun().getDamage());
    }
}