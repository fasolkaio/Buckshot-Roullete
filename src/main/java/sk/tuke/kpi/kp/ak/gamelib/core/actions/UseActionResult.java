package sk.tuke.kpi.kp.ak.gamelib.core.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.kpi.kp.ak.gamelib.core.items.Item;
import sk.tuke.kpi.kp.ak.gamelib.core.items.ItemUseResult;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

@AllArgsConstructor
@Getter
public class UseActionResult implements ActionResult {
    Player player;
    ItemUseResult itemUseResult;
    Class<? extends Item> itemClass;
}
