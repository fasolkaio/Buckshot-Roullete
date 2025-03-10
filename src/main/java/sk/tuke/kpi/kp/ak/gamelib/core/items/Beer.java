package sk.tuke.kpi.kp.ak.gamelib.core.items;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

public class Beer implements Item {

    @Override
    public boolean useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        Gun gun = game.getGun();
        if (gun.isEmpty())
            throw new UnsupportedOperationException("Unsupported operation. Gun is empty");
        return gun.removeBullet();
    }
}
