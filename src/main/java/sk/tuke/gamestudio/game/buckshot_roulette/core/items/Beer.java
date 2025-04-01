package sk.tuke.gamestudio.game.buckshot_roulette.core.items;

import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.weapon.Gun;

public class Beer extends Item {

    public Beer() {
        super(140);
    }

    @Override
    public ItemUseResult useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Gun gun = game.getGun();
        if (gun == null || gun.isEmpty())
            return ItemUseResult.ERROR;
        if (gun.removeBullet())
            return ItemUseResult.BULLET_WAS_LIVE;
        else
            return ItemUseResult.BULLET_WAS_BLANK;
    }
}
