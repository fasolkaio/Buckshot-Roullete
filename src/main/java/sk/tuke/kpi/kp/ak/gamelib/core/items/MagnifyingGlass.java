package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

public class MagnifyingGlass implements Item {
    @Override
    public ActionResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Gun gun = game.getGun();
        if (gun.isEmpty())
            return ActionResult.USE_ITEM_FAILED;
        if(gun.checkBullet())
            return ActionResult.BULLET_WAS_LIVE;
        else
            return ActionResult.BULLET_WAS_BLANK;
    }
}
