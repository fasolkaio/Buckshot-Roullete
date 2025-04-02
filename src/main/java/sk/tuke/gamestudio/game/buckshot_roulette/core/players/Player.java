package sk.tuke.gamestudio.game.buckshot_roulette.core.players;

import lombok.Getter;
import lombok.Setter;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.Action;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.ActionResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.Item;
import sk.tuke.gamestudio.game.buckshot_roulette.core.items.ItemUseResult;
import sk.tuke.gamestudio.game.buckshot_roulette.core.observers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
public abstract class Player {
    private int lifeCount;
    private final int maxLifeCount;
    private final String name;
    private final List<Item> items;
    private final int itemsCapacity;
    @Setter
    private boolean skipTurn;
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

    public boolean heal() {
        if (lifeCount < maxLifeCount) {
            lifeCount++;
            return true;
        }
        return false;
    }

    public void getDamage(int damage) {
        lifeCount -= damage;
        observers.stream()
                .filter(o -> o instanceof GetDamageObserver || o instanceof MakeDamageObserver)
                .forEach(o -> {
                    o.notifyGame(damage);
                });
        if (lifeCount < 0)
            lifeCount = 0;
        if (lifeCount == 0)
            observers.stream()
                    .filter(o -> o instanceof HealthObserver)
                    .findFirst()
                    .ifPresent(GameObserver::notifyGame);
    }

    public boolean cuff() {
        if (skipTurn) {
            return false;
        }
        skipTurn = true;
        return true;
    }

    public void addItem(Item item) {
        if (items.size() < itemsCapacity) {
            items.add(item);
        }
    }

    public <I extends Item> ItemUseResult useItem(Class<I> itemClass, Game game) {
        Item firstItem = items.stream()
                .filter(item -> item.getClass()
                        .equals(itemClass))
                .findFirst().orElse(null);
        ItemUseResult result = ItemUseResult.USE_ITEM_FAILED;
        if (firstItem != null) {
            result = firstItem.useItem(game);
            if (!(result == ItemUseResult.USE_ITEM_FAILED)) {
                items.remove(firstItem);
                observers.stream()
                        .filter(o -> o instanceof UseItemObserver)
                        .findFirst()
                        .ifPresent(o -> {
                            o.notifyGame(firstItem.getCost());
                        });
            }
        }

        return result;
    }

    public boolean scipTurn() {
        return skipTurn;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
}
