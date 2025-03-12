package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

public class Saw extends Item{
    @Override
    public ItemUseResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Gun gun = game.getGun();
        if (gun == null)
            return ItemUseResult.ERROR;
        if(game.getGun().doubleDamage())
            return ItemUseResult.USE_ITEM_SUCCESS;
        else
            return ItemUseResult.USE_ITEM_FAILED;
    }
}
