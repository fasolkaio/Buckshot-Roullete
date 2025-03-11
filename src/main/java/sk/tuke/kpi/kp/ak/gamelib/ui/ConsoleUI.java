package sk.tuke.kpi.kp.ak.gamelib.ui;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;

public class ConsoleUI implements GameUI{
    private Game game;

    @Override
    public void play(Game game) {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");
        this.game = game;
        //TODO
    }

    @Override
    public void show() {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");
        //TODO
    }

    @Override
    public void handleInput() {
        if (game == null)
            throw new UnsupportedOperationException("Game is null");
        //TODO
    }
}
