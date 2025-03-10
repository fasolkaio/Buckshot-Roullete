package sk.tuke.kpi.kp.ak.gamelib.core.items;


import sk.tuke.kpi.kp.ak.gamelib.core.Game;

public class Cigarettes implements Item {
    @Override
    public ItemUseResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        if (game.getActualPlayer().heal())
            return ItemUseResult.USE_ITEM_SUCCESS;
        else
            return ItemUseResult.USE_ITEM_FAILED;
    }
}
