package sk.tuke.kpi.kp.ak.gamelib.core.players;

import lombok.Getter;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.Action;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Item;
import sk.tuke.kpi.kp.ak.gamelib.core.actions.ActionResult;
import sk.tuke.kpi.kp.ak.gamelib.core.observers.GameObserver;
import sk.tuke.kpi.kp.ak.gamelib.core.observers.HealthObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
public abstract class Player {
    private int lifeCount;
    private final int maxLifeCount;
    private final String name;
    private boolean skipTurn;
    private final List<Item> items;
    private final int itemsCapacity;
    private final HashSet<GameObserver> observers;

    public Player(String name, int maxLifeCount) {
        this.name = name;
        this.maxLifeCount = maxLifeCount;
        lifeCount = maxLifeCount;
        skipTurn = false;
        itemsCapacity = 8;
        items = new ArrayList<>();
        observers = new HashSet<>();
    }

    public abstract ActionResult doTurn(Action action);

    public boolean heal(){
        if(lifeCount < maxLifeCount){
            lifeCount++;
            return true;
        }
        return false;
    }

    public void makeDamage(int damage){
        lifeCount -= damage;
        if(lifeCount < 0)
            lifeCount = 0;
        if (lifeCount == 0)
            observers.stream().filter(o -> o instanceof HealthObserver).forEach(GameObserver::notifyGame);
    }

    public  void addObserver(GameObserver observer){
        observers.add(observer);
    }

    public boolean addItem(Item item){
        if(items.size() < itemsCapacity){
            items.add(item);
            return true;
        }
        return false;
    }

    public <I extends Item> ActionResult useItem(Class<I> itemClass, Game game) {
        Item firstItem = items.stream()
                .filter(item -> item.getClass()
                .equals(itemClass))
                .findFirst().orElse(null);
        ActionResult result = ActionResult.USE_ITEM_FAILED;
        if(firstItem != null){
            result = firstItem.useItem(game);
            items.remove(firstItem);
        }

        return result;
    }

    public boolean scipTurn(){
        return skipTurn;
    }

    public boolean makeSkipTurn(){
        if(skipTurn){
            return false;
        }
        skipTurn = true;
        return true;
    }
}
