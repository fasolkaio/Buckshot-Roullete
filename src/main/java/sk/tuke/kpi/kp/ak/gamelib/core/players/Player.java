package sk.tuke.kpi.kp.ak.gamelib.core.players;

import lombok.Getter;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Item;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Player {
    private int lifeCount;
    private final int maxLifeCount;
    private final String name;
    private boolean scipTurn;
    private final List<Item> items;
    private final int itemsCapacity;

    public Player(String name, int maxLifeCount) {
        this.name = name;
        this.maxLifeCount = maxLifeCount;
        lifeCount = maxLifeCount;
        scipTurn = false;
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
    public <I extends Item> boolean useItem(Class<I> itemClass) {
        items.stream().filter(item -> item.getClass().equals(itemClass)).findFirst().ifPresent(item -> {
            //TODO item.use
            items.remove(item);
        });
        return false;
    }
}
