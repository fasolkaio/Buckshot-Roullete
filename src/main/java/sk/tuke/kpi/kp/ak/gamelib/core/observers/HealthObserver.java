package sk.tuke.kpi.kp.ak.gamelib.core.observers;

import lombok.AllArgsConstructor;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.GameState;

@AllArgsConstructor
public class HealthObserver implements GameObserver {
    private Game game;

    @Override
    public void notifyGame() {
        game.setGameState(GameState.ROUND_ENDED);
    }
}
