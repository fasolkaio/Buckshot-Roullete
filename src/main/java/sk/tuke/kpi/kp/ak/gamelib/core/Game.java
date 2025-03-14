package sk.tuke.kpi.kp.ak.gamelib.core;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.SkipTurnActionResult;
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
    @Getter
    private final GameMode gameMode;

    private Player firstPlayer;
    private Player secondPlayer;


    //generate for P2P Mode
    public Game(String firstPlayerName, String secondPlayerName) {
        gameState = GameState.FIRST_PLAYER_TURN;
        gameMode = GameMode.P2P;
        initRound(firstPlayerName, secondPlayerName);
    }

    //generate for Single Mode
    public Game(String firstPlayerName) {
        gameState = GameState.FIRST_PLAYER_TURN;
        gameMode = GameMode.Single;
        initRound(firstPlayerName, "Dealer");
    }

    //generate for Testing Mode or B2B Mode
    public Game(GameMode gameMode) {
        gameState = GameState.FIRST_PLAYER_TURN;
        this.gameMode = gameMode;
        switch (gameMode) {
            case B2B:
                initRound("Dealer", "Dealer");
                break;
            case Testing:
                initTestingRound();
                break;
            default:
                throw new UnsupportedOperationException("Incorrect initialization of game");
        }

    }


    private void initRound(String firstPlayerName, String secondPlayerName){
        int playersLifeCount = RandomGenerator.randomIntBetween(2, 5);

        if(gameMode != GameMode.B2B)
            firstPlayer = new Human(firstPlayerName, playersLifeCount);
        else
            firstPlayer = new Dealer(secondPlayerName, playersLifeCount, this);

        if(gameMode != GameMode.Single)
            secondPlayer = new Dealer(secondPlayerName, playersLifeCount, this);
        else
            secondPlayer = new Human(secondPlayerName, playersLifeCount);

        firstPlayer.addObserver(new HealthObserver(this));
        secondPlayer.addObserver(new HealthObserver(this));

        generateItems();
        reloadGun();
    }

    public void reinitRound(){
        initRound(firstPlayer.getName(), secondPlayer.getName());
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
        if(!getActualPlayer().scipTurn())
            return getActualPlayer().doTurn(action);
        else{
            getActualPlayer().setSkipTurn(false);
            switchTurn();
            return new SkipTurnActionResult(getNotActualPlayer());
        }
    }

    public void switchTurn(){
        if(gameState.equals(GameState.ROUND_ENDED))
            return;
        gameState = gameState == GameState.FIRST_PLAYER_TURN
                ? GameState.SECOND_PLAYER_TURN
                : GameState.FIRST_PLAYER_TURN;
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
        if (gameState == GameState.FIRST_PLAYER_TURN || gameState == GameState.ROUND_ENDED)
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

    public boolean isDealerTurn(){
        return getActualPlayer() instanceof Dealer;
    }

    public boolean isRoundEnded(){
        return gameState.equals(GameState.ROUND_ENDED);
    }

    public boolean isEnded(){
        return gameState.equals(GameState.GAME_ENDED);
    }


    private void initTestingRound() {
        int playersLifeCount = 1;
        firstPlayer = new Human("Tester", playersLifeCount);
        secondPlayer = new Human("Dealer", playersLifeCount);
        firstPlayer.addObserver(new HealthObserver(this));
        secondPlayer.addObserver(new HealthObserver(this));
        generateTestingItems();
        gun = new Gun(2, 1);
    }

    private void generateTestingItems() {
        firstPlayer.addItem(new Cigarettes());
        firstPlayer.addItem(new Handcuff());
        firstPlayer.addItem(new MagnifyingGlass());
        firstPlayer.addItem(new Beer());
        firstPlayer.addItem(new Saw());
        secondPlayer.addItem(new Cigarettes());
        secondPlayer.addItem(new Handcuff());
        secondPlayer.addItem(new MagnifyingGlass());
        secondPlayer.addItem(new Beer());
        secondPlayer.addItem(new Saw());
    }
}
