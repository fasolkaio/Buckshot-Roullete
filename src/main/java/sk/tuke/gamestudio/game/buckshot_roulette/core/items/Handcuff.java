package sk.tuke.gamestudio.game.buckshot_roulette.core.items;

import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Player;

public class Handcuff extends Item{
    public Handcuff() {
        super(210);
    }

    @Override
    public ItemUseResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Player player = game.getNotActualPlayer();
        if(player == null)
            return ItemUseResult.ERROR;
        if (player.cuff())
            return ItemUseResult.USE_ITEM_SUCCESS;
        else
            return ItemUseResult.USE_ITEM_FAILED;
    }
}
