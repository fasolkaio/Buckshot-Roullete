package sk.tuke.kpi.kp.ak.gamelib.core.actions;

import lombok.AllArgsConstructor;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Item;

@AllArgsConstructor
public class UseItem implements Action {
    private Game game;
    private Class<? extends Item> itemClass;

    @Override
    public ActionResult execute() {
        if(game == null)
            throw new NullPointerException("Game is null");

        return game.getActualPlayer().useItem(itemClass, game);
    }
}
