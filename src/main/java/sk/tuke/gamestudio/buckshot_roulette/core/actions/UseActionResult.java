package sk.tuke.gamestudio.buckshot_roulette.core.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.gamestudio.buckshot_roulette.core.items.Item;
import sk.tuke.gamestudio.buckshot_roulette.core.items.ItemUseResult;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Player;

@AllArgsConstructor
@Getter
public class UseActionResult implements ActionResult {
    Player player;
    ItemUseResult itemUseResult;
    Class<? extends Item> itemClass;
}
