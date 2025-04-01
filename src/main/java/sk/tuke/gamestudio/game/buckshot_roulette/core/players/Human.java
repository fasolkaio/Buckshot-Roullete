package sk.tuke.gamestudio.game.buckshot_roulette.core.players;

import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.Action;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.ActionResult;

public class Human extends Player {
    public Human(String name, int maxLifeCount) {
        super(name, maxLifeCount);
    }

    @Override
    public ActionResult doTurn(Action action) {
        return action.execute();
    }
}
