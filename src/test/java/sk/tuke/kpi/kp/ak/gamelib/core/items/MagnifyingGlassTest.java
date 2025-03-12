package sk.tuke.kpi.kp.ak.gamelib.core.items;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;

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
        ActionResult result = glass.useItem(game);
        if(isLive)
            assertEquals(ActionResult.BULLET_WAS_LIVE, result);
        else
            assertEquals(ActionResult.BULLET_WAS_BLANK, result);
    }
    @Test
    public void testUseWithEmptyGun() {
        Game game = new Game("first player", "second player");
        Item glass = new MagnifyingGlass();
        while (!game.getGun().isEmpty()) {
            game.getGun().removeBullet();
        }
        assertEquals(ActionResult.USE_ITEM_FAILED, glass.useItem(game));
    }
}