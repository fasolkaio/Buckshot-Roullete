package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;

public interface Item {
    ItemUseResult useItem(Game game);
}
