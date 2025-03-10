package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

public class Beer implements Item {

    @Override
    public ItemUseResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Gun gun = game.getGun();
        if (gun.isEmpty())
            return ItemUseResult.USE_ITEM_FAILED;
        if (gun.removeBullet())
            return ItemUseResult.BULLET_WAS_LIVE;
        else
            return ItemUseResult.BULLET_WAS_BLANK;
    }
}
