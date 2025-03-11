package sk.tuke.kpi.kp.ak.gamelib.ui;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;

public interface GameUI {
    void play(Game game);
    void show();
    void handleInput();
}
