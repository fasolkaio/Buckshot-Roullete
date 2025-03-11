package sk.tuke.kpi.kp.ak.gamelib.core.players;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;

public class Diller extends Player {
    private Game game;

    public Diller(String name, int maxLifeCount, Game game) {
        super(name, maxLifeCount);
        this.game = game;
    }

    @Override
    public ActionResult doTurn(Action action) {
        //TODO
        return null;
    }
}
