package sk.tuke.kpi.kp.ak.gamelib.core.items;


import sk.tuke.kpi.kp.ak.gamelib.core.Game;

public class Cigarettes implements Item {
    @Override
    public boolean useItem(Game game) {
        if(game == null)
            return false;
        return game.getActualPlayer().heal();
    }
}
