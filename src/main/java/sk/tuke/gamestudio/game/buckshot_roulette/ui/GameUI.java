package sk.tuke.gamestudio.game.buckshot_roulette.ui;

import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;

public interface GameUI {
    void play(Game game);

    void show();

    void handleInput();
}
