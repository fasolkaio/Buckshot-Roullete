package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;

public abstract class Item {
    public abstract ItemUseResult useItem(Game game);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
