package sk.tuke.gamestudio.game.buckshot_roulette.core.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Player;

@AllArgsConstructor
@Getter
public class ShootActionResult implements ActionResult {
    private Player player;
    private boolean shootResult;
    private boolean selfShoot;
}
