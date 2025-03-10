package sk.tuke.kpi.kp.ak.gamelib.core.items;


import sk.tuke.kpi.kp.ak.gamelib.core.Game;

public class Cigarettes implements Item {
    @Override
    public boolean useItem(Game game) {
        if(game == null)
            throw new UnsupportedOperationException("Unsupported operation. Game not exist");
        return game.getActualPlayer().heal();
    }
}
