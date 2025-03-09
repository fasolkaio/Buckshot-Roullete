package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

public class MagnifyingGlass implements Item {
    @Override
    public boolean useItem(Game game) {
        if(game == null)
            return false;
        Gun gun = game.getGun();
        if (gun.getBullets().isEmpty())
            throw new UnsupportedOperationException("Unsupported operation. Gun is empty");
        return gun.checkBullet();
    }
}
