package sk.tuke.gamestudio.buckshot_roulette.core;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.Action;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.ActionResult;
import sk.tuke.gamestudio.buckshot_roulette.core.actions.SkipTurnActionResult;
import sk.tuke.gamestudio.buckshot_roulette.core.items.*;
import sk.tuke.gamestudio.buckshot_roulette.core.observers.GetDamageObserver;
import sk.tuke.gamestudio.buckshot_roulette.core.observers.HealthObserver;
import sk.tuke.gamestudio.buckshot_roulette.core.observers.MakeDamageObserver;
import sk.tuke.gamestudio.buckshot_roulette.core.observers.UseItemObserver;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Dealer;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Human;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Player;
import sk.tuke.gamestudio.buckshot_roulette.core.utilities.RandomGenerator;
import sk.tuke.gamestudio.buckshot_roulette.core.weapon.Gun;

import static sk.tuke.gamestudio.buckshot_roulette.core.GameMode.Single;
import static sk.tuke.gamestudio.buckshot_roulette.core.GameMode.Testing;

public class Game {
    @Getter
    private Gun gun;
    @Getter @Setter
    private GameState gameState = GameState.FIRST_PLAYER_TURN;
    @Getter
    private int score = 8000;

    @Getter
    private final GameMode gameMode;

    private Player firstPlayer;
    private Player secondPlayer;

    //generate for P2P Mode
    public Game(String firstPlayerName, String secondPlayerName) {
        gameMode = GameMode.P2P;
        initRound(firstPlayerName, secondPlayerName);

    }

    //generate for Single Mode
    public Game(String firstPlayerName) {
        gameMode = GameMode.Single;
        initRound(firstPlayerName, "Dealer");
    }

    //generate for Testing Mode or B2B Mode
    public Game(GameMode gameMode) {
        this.gameMode = gameMode;
        switch (gameMode) {
            case B2B:
                initRound("Dealer 1", "Dealer 2");
                break;
            case Testing:
                initTestingRound();
                break;
            default:
                throw new UnsupportedOperationException("Incorrect initialization of game");
        }
    }

    //initialization
    private void initRound(String firstPlayerName, String secondPlayerName){
        int playersLifeCount = RandomGenerator.randomIntBetween(2, 5);

        if(gameMode != GameMode.B2B)
            firstPlayer = new Human(firstPlayerName, playersLifeCount);
        else
            firstPlayer = new Dealer(secondPlayerName, playersLifeCount, this);

        if(gameMode != GameMode.P2P)
            secondPlayer = new Dealer(secondPlayerName, playersLifeCount, this);
        else
            secondPlayer = new Human(secondPlayerName, playersLifeCount);

        firstPlayer.addObserver(new HealthObserver(this));
        secondPlayer.addObserver(new HealthObserver(this));
        if(gameMode == GameMode.Single || gameMode == GameMode.Testing){
            firstPlayer.addObserver(new UseItemObserver(this));
            secondPlayer.addObserver(new MakeDamageObserver(this));
            firstPlayer.addObserver(new GetDamageObserver(this));
        }

        generateItems();
        reloadGun();
    }

    public void reinitRound(){
        gun = null;
        if(gameMode == GameMode.Single || gameMode == GameMode.Testing)
            gameState = GameState.FIRST_PLAYER_TURN;
        score = score * 2;
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

    //turn logic
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

    //players
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

    public Player getHumanPlayer() {
        if(singleMod())
            return firstPlayer;
        else
            return null;
    }

    //specific getters
    public String getWinnerName(){
        if(firstPlayer.getLifeCount() == 0)
            return secondPlayer.getName();
        else if(secondPlayer.getLifeCount() == 0)
            return firstPlayer.getName();
        else
            return null;
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

    public boolean continueGame(){
        return (gameMode.equals(GameMode.Single) || gameMode.equals(GameMode.Testing)) && firstPlayer.getLifeCount() != 0;
    }

    public  boolean singleMod(){
        return gameMode == Single || gameMode == Testing;
    }

    //specific setters

    public void setScore(int score){
        this.score = score;
        if(this.score < 0)
            this.score = 0;
    }

    //for testing
    private void initTestingRound() {
        int playersLifeCount = 1;
        firstPlayer = new Human("Tester", playersLifeCount);
        secondPlayer = new Dealer("Dealer", playersLifeCount, this);
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
