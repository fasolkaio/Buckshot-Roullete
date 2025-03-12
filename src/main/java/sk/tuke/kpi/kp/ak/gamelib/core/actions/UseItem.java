package sk.tuke.kpi.kp.ak.gamelib.core.actions;

import lombok.AllArgsConstructor;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Item;
import sk.tuke.kpi.kp.ak.gamelib.core.items.ItemUseResult;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

@AllArgsConstructor
public class UseItem implements Action {
    private Game game;
    private Class<? extends Item> itemClass;

    @Override
    public ActionResult execute() {
        if(game == null)
            throw new NullPointerException("Game is null");

        Player player = game.getActualPlayer();

        if(player == null)
            throw new NullPointerException("Player not found");

        ItemUseResult result = player.useItem(itemClass, game);

        return new UseActionResult(player,result, itemClass);
    }
}
