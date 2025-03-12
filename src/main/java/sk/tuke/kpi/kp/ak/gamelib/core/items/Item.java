package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;

public abstract class Item {
    public abstract ActionResult useItem(Game game);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
