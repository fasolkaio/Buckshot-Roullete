package sk.tuke.gamestudio.game.buckshot_roulette.core.observers;


public interface GameObserver {
    void notifyGame();

    void notifyGame(int data);
}
