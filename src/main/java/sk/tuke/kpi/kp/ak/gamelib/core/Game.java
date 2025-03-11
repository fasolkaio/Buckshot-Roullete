package sk.tuke.kpi.kp.ak.gamelib.core;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Human;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;
import sk.tuke.kpi.kp.ak.gamelib.core.utilities.RandomGenerator;
import sk.tuke.kpi.kp.ak.gamelib.core.weapon.Gun;

public class Game {
    @Getter
    private Gun gun;
    @Getter @Setter
    private GameState gameState;
    private Player firstPlayer;
    private Player secondPlayer;

    public Game(String firstPlayerName, String secondPlayerName) {
        gun = new Gun();
        gameState = GameState.FIRST_PLAYER_TURN;
        int playersLifeCount = RandomGenerator.randomIntBetween(3, 5);
        firstPlayer = new Human(firstPlayerName, playersLifeCount);
        secondPlayer = new Human(secondPlayerName, playersLifeCount);
    }

    public void startGame(){
        //TODO
    }

    private void initRound(){
        //TODO
    }

    public void playTurn(){
        //TODO
    }

    public boolean isGameOver(){
        //TODO
        return false;
    }

    public Player getWinner(){
        if(firstPlayer.getLifeCount() == 0)
            return secondPlayer;
        else
            return firstPlayer;
    }

    public Player getActualPlayer() {
        if (gameState == GameState.FIRST_PLAYER_TURN)
            return firstPlayer;
        else
            return secondPlayer;
    }

    public Player getNotActualPlayer() {
        if (gameState == GameState.SECOND_PLAYER_TURN)
            return firstPlayer;
        else
            return secondPlayer;
    }

}
