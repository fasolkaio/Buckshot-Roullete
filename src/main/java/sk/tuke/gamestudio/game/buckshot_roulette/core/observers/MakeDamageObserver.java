package sk.tuke.gamestudio.game.buckshot_roulette.core.observers;

import lombok.AllArgsConstructor;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;

@AllArgsConstructor
public class MakeDamageObserver implements GameObserver {
    private Game game;

    @Override
    public void notifyGame() {
        throw new UnsupportedOperationException("Not supported action");
    }

    @Override
    public void notifyGame(int data) {
        game.setScore((int) (game.getScore() + data * game.getScore() * 0.15));
    }
}
