package sk.tuke.kpi.kp.ak.gamestudio.buckshot_roulette.core.items;


import sk.tuke.kpi.kp.ak.gamestudio.buckshot_roulette.core.Game;
import sk.tuke.kpi.kp.ak.gamestudio.buckshot_roulette.core.players.Player;

public class Cigarettes extends Item {
    public Cigarettes() {
        super(160);
    }

    @Override
    public ItemUseResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Player player = game.getActualPlayer();
        if(player == null)
            return ItemUseResult.ERROR;
        if (player.heal())
            return ItemUseResult.USE_ITEM_SUCCESS;
        else
            return ItemUseResult.USE_ITEM_FAILED;
    }
}
