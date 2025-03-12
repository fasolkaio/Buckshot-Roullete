package sk.tuke.kpi.kp.ak.gamelib.core.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

@AllArgsConstructor
@Getter
public class SkipTurnActionResult implements ActionResult{
    Player player;
}
