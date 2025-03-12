package sk.tuke.kpi.kp.ak.gamelib.core.actions;

import lombok.AllArgsConstructor;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;

@AllArgsConstructor
public class Shoot implements Action {
    private final Game game;
    private final boolean selfShoot;

    @Override
    public ActionResult execute() {
        if (game == null)
            throw new NullPointerException("Game is null");

        Player actualPlayer = game.getActualPlayer();
        Player anotherPlayer = game.getNotActualPlayer();

        boolean result;
        if (selfShoot)
            result = game.getGun().shoot(actualPlayer);
        else
            result = game.getGun().shoot(anotherPlayer);

        if (result || !selfShoot)
            game.switchTurn();

        return new ShootActionResult(actualPlayer, result, selfShoot);
    }

}