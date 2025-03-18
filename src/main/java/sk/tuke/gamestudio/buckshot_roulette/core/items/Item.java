package sk.tuke.gamestudio.buckshot_roulette.core.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.gamestudio.buckshot_roulette.core.Game;

@AllArgsConstructor
public abstract class Item {
    @Getter
    private int cost;

    public abstract ItemUseResult useItem(Game game);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
