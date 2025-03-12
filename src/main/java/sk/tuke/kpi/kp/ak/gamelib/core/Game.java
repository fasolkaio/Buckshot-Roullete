package sk.tuke.kpi.kp.ak.gamelib.core;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.items.*;
import sk.tuke.kpi.kp.ak.gamelib.core.observers.HealthObserver;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Dealer;
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
    @Getter
    private final boolean singleGame;

    public Game(String firstPlayerName, String secondPlayerName) {
        gameState = GameState.FIRST_PLAYER_TURN;
        this.singleGame = false;
        initRound(firstPlayerName, secondPlayerName);
    }

    public Game(String firstPlayerName) {
        gameState = GameState.FIRST_PLAYER_TURN;
        this.singleGame = true;
        initRound(firstPlayerName, "Dealer");
    }

    public void initRound(String firstPlayerName, String secondPlayerName){
        int playersLifeCount = RandomGenerator.randomIntBetween(3, 5);
        firstPlayer = new Human(firstPlayerName, playersLifeCount);
        if(singleGame)
            secondPlayer = new Dealer(secondPlayerName, playersLifeCount, this);
        else
            secondPlayer = new Human(secondPlayerName, playersLifeCount);
        firstPlayer.addObserver(new HealthObserver(this));
        secondPlayer.addObserver(new HealthObserver(this));
        generateItems();
        reloadGun();
    }

    public void reinitRound(){
        int playersLifeCount = RandomGenerator.randomIntBetween(3, 5);
        firstPlayer = new Human(firstPlayer.getName(), playersLifeCount);
        if(singleGame)
            secondPlayer = new Dealer(secondPlayer.getName(), playersLifeCount, this);
        else
            secondPlayer = new Human(secondPlayer.getName(), playersLifeCount);
        firstPlayer.addObserver(new HealthObserver(this));
        secondPlayer.addObserver(new HealthObserver(this));
        generateItems();
        reloadGun();
    }

    public void generateItems(){
        int itemsCount = RandomGenerator.randomIntBetween(2, 4);
        for (int i = 0; i < itemsCount; i++) {
            firstPlayer.addItem(generateRandomItem());
            secondPlayer.addItem(generateRandomItem());
        }
    }

    private Item generateRandomItem() {
        Item[] items = {new Cigarettes(), new Handcuff(), new MagnifyingGlass(), new Beer(), new Saw()};
        return items[RandomGenerator.randomIntBetween(0, items.length - 1)];
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
        ActionResult result = null;
        if(!getActualPlayer().scipTurn())
            result = getActualPlayer().doTurn(action);
        else
            getActualPlayer().setSkipTurn(false);
        return result;
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

    public boolean isBotTurn(){
        return getActualPlayer() instanceof Dealer;
    }

    public boolean isRoundEnded(){
        return gameState.equals(GameState.ROUND_ENDED);
    }

    public boolean isEnded(){
        return gameState.equals(GameState.GAME_ENDED);
    }
}
