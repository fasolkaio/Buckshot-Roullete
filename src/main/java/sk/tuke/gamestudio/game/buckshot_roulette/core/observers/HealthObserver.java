package sk.tuke.gamestudio.game.buckshot_roulette.core.observers;

import lombok.AllArgsConstructor;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.GameState;

@AllArgsConstructor
public class HealthObserver implements GameObserver {
    private Game game;

    @Override
    public void notifyGame() {
        game.setGameState(GameState.ROUND_ENDED);
        if (game.getActualPlayer().getLifeCount() == 0)
            game.setScore(0);
    }

    @Override
    public void notifyGame(int data) {
        throw new UnsupportedOperationException("Not supported action");
    }
}
