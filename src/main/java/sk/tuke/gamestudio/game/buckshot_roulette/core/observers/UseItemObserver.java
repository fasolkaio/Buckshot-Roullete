package sk.tuke.gamestudio.game.buckshot_roulette.core.observers;

import lombok.AllArgsConstructor;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;

@AllArgsConstructor
public class UseItemObserver implements GameObserver {
    private Game game;

    @Override
    public void notifyGame() {
        throw new UnsupportedOperationException("Not supported action");
    }

    @Override
    public void notifyGame(int data) {
        game.setScore(game.getScore() - data);
    }
}
