package sk.tuke.kpi.kp.ak.gamelib.core.players;

import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;

public class Human extends Player {
    public Human(String name, int maxLifeCount) {
        super(name, maxLifeCount);
    }

    @Override
    public ActionResult doTurn(Action action) {
        return action.execute();
    }
}
