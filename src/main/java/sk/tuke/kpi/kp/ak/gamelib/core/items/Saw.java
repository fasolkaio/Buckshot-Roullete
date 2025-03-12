package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;

public class Saw extends Item{
    @Override
    public ActionResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        if(game.getGun().doubleDamage())
            return ActionResult.USE_ITEM_SUCCESS;
        else
            return ActionResult.USE_ITEM_FAILED;
    }
}
