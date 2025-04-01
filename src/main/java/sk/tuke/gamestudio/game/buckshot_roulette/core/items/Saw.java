package sk.tuke.gamestudio.game.buckshot_roulette.core.items;

import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.weapon.Gun;

public class Saw extends Item{
    public Saw() {
        super(190);
    }

    @Override
    public ItemUseResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Gun gun = game.getGun();
        if (gun == null)
            return ItemUseResult.ERROR;
        if(game.getGun().doubleDamage())
            return ItemUseResult.USE_ITEM_SUCCESS;
        else
            return ItemUseResult.USE_ITEM_FAILED;
    }
}
