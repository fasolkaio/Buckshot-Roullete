package sk.tuke.kpi.kp.ak.gamestudio.buckshot_roulette.core.actions;

import lombok.AllArgsConstructor;
import sk.tuke.kpi.kp.ak.gamestudio.buckshot_roulette.core.Game;
import sk.tuke.kpi.kp.ak.gamestudio.buckshot_roulette.core.items.Item;
import sk.tuke.kpi.kp.ak.gamestudio.buckshot_roulette.core.items.ItemUseResult;
import sk.tuke.kpi.kp.ak.gamestudio.buckshot_roulette.core.players.Player;

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
