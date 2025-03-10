package sk.tuke.kpi.kp.ak.gamelib.core.items;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;

import static org.junit.jupiter.api.Assertions.*;

public class MagnifyingGlassTest {
    @Test
    public void nullTest(){
        Item glass = new MagnifyingGlass();
        assertThrows(UnsupportedOperationException.class, () -> glass.useItem(null));

    }
    @Test
    public void testUse() {
        Game game = new Game("first player", "second player");
        Item glass = new MagnifyingGlass();
        boolean isLive = !game.getGun().getBullets().get(0).isBlank();
        boolean result = glass.useItem(game);
        assertEquals(result, isLive);
    }
    @Test
    public void testUseWithEmptyGun() {
        Game game = new Game("first player", "second player");
        Item glass = new MagnifyingGlass();
        while (!game.getGun().isEmpty()) {
            game.getGun().removeBullet();
        }
        assertThrows(UnsupportedOperationException.class, () -> glass.useItem(game));
    }
}