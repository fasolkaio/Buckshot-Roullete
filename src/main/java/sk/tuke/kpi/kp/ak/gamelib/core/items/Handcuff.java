package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

public class Handcuff extends Item{
    @Override
    public ItemUseResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Player player = game.getActualPlayer();
        if(player == null)
            return ItemUseResult.ERROR;
        if (player.cuff())
            return ItemUseResult.USE_ITEM_SUCCESS;
        else
            return ItemUseResult.USE_ITEM_FAILED;
    }
}
