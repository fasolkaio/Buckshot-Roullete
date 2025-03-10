package sk.tuke.kpi.kp.ak.gamelib.core.players;

import lombok.Getter;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public abstract class Player {
    private int lifeCount;
    private final int maxLifeCount;
    private final String name;
    private boolean skipTurn;
    private final List<Item> items;
    private final int itemsCapacity;

    public Player(String name, int maxLifeCount) {
        this.name = name;
        this.maxLifeCount = maxLifeCount;
        lifeCount = maxLifeCount;
        skipTurn = false;
        itemsCapacity = 8;
        items = new ArrayList<>();
    }

    public abstract void doTurn();

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
        //TODO obeserver for death
    }

    public boolean addItem(Item item){
        if(items.size() < itemsCapacity){
            items.add(item);
            return true;
        }
        return false;
    }

    //TODO
    public <I extends Item> boolean useItem(Class<I> itemClass, Game game) {
        AtomicBoolean result = new AtomicBoolean(false);
        items.stream()
                .filter(item -> item.getClass()
                .equals(itemClass))
                .findFirst().
                ifPresent(item ->
                {
                    result.set(item.useItem(game));
                    items.remove(item);
                });
        return result.get();
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
