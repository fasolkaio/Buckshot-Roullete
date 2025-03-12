package sk.tuke.kpi.kp.ak.gamelib.core.items;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;

import static org.junit.jupiter.api.Assertions.*;

public class BeerTest {
    @Test
    public void nullTest(){
        Item beer = new Beer();
        assertThrows(UnsupportedOperationException.class, () -> beer.useItem(null));
    }
    @Test
    public void testUse() {
        Game game = new Game("first player", "second player");
        Item beer = new Beer();
        int bulletCountBefore = game.getGun().getBulletsCount();
        int liveBulletCountBefore = game.getGun().getLiveBulletsCount();
        ActionResult result = beer.useItem(game);
        if (result == ActionResult.BULLET_WAS_LIVE)
                assertEquals(liveBulletCountBefore - 1, game.getGun().getLiveBulletsCount());
        assertEquals(bulletCountBefore - 1, game.getGun().getBulletsCount());
    }
    @Test
    public void testUseWithEmptyGun() {
        Game game = new Game("first player", "second player");
        Item beer = new Beer();
        while (!game.getGun().isEmpty()) {
            game.getGun().removeBullet();
        }
        assertEquals(ActionResult.USE_ITEM_FAILED, beer.useItem(game));
    }
}