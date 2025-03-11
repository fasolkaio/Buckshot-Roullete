package sk.tuke.kpi.kp.ak.gamelib.core;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
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
        gameState = GameState.FIRST_PLAYER_TURN;
        initRound(firstPlayerName, secondPlayerName);
    }

    public void initRound(String firstPlayerName, String secondPlayerName){
        int playersLifeCount = RandomGenerator.randomIntBetween(3, 5);
        firstPlayer = new Human(firstPlayerName, playersLifeCount);
        secondPlayer = new Human(secondPlayerName, playersLifeCount);
        reloadGun();
    }

    //if old is empty or does not exist crate new gun and return true
    public boolean reloadGun(){
        if(gun != null && !gun.isEmpty())
            return false;
        gun = new Gun();
        return true;
    }

    public ActionResult playTurn(Action action){
        if(gun == null || gun.isEmpty())
            throw new UnsupportedOperationException("Game does not completed, reload gun to continue!");

        return getActualPlayer().doTurn(action);
    }

    public boolean isGameOver(){
        return gameState.equals(GameState.ENDED);
    }

    public String getWinnerName(){
        if(firstPlayer.getLifeCount() == 0)
            return secondPlayer.getName();
        else if(secondPlayer.getLifeCount() == 0)
            return firstPlayer.getName();
        else
            return null;
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
