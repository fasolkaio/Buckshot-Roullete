package sk.tuke.kpi.kp.ak.gamelib.core.actions;

import lombok.AllArgsConstructor;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.GameState;

@AllArgsConstructor
public class Shoot implements Action {
    private final boolean selfShoot;
    private final Game game;

    @Override
    public ActionResult execute() {
        if(game == null)
            throw new NullPointerException("Game is null");

        boolean result;
        if(selfShoot)
            result = game.getGun().shoot(game.getActualPlayer());
        else
            result = game.getGun().shoot(game.getNotActualPlayer());

        if(result || !selfShoot)
            game.setGameState(game.getGameState() == GameState.FIRST_PLAYER_TURN
                                        ? GameState.SECOND_PLAYER_TURN
                                        : GameState.FIRST_PLAYER_TURN);

        if(result)
            return ActionResult.HIT_SUCCESS;
        else
            return ActionResult.HIT_FAILED;
    }
}