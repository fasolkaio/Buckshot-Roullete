package sk.tuke.kpi.kp.ak.gamelib;

import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.ui.ConsoleUI;

public class BuckshotRoulette {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        Game game = new Game("first", "second");
        ui.play(game);
    }
}
