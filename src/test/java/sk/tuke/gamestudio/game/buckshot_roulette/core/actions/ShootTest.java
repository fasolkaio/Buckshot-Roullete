package sk.tuke.gamestudio.game.buckshot_roulette.core.actions;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.game.buckshot_roulette.core.GameState;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.Action;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.Shoot;
import sk.tuke.gamestudio.game.buckshot_roulette.core.actions.ShootActionResult;

import static org.junit.jupiter.api.Assertions.*;

public class ShootTest {
    @Test
    public void nullTest(){
        Action shoot = new Shoot(null, false);
        assertThrows(NullPointerException.class, shoot::execute);
    }

    @Test
    public void testShotOpponent(){
        Game game = new Game("first player", "second player");
        Action shoot = new Shoot(game, false );
        ShootActionResult actionResult = (ShootActionResult)shoot.execute();
        assertFalse(actionResult.isSelfShoot());
        assertEquals(game.getNotActualPlayer(), actionResult.getPlayer());
        assertEquals(GameState.SECOND_PLAYER_TURN,game.getGameState());
    }

    @Test
    public void testShotYourself(){
        Game game = new Game("first player", "second player");
        Action shoot = new Shoot(game, true);
        ShootActionResult actionResult = (ShootActionResult)shoot.execute();
        assertTrue(actionResult.isSelfShoot());
        if(actionResult.isShootResult()) {
            assertEquals(GameState.SECOND_PLAYER_TURN, game.getGameState());
            assertEquals(game.getNotActualPlayer(), actionResult.getPlayer());
        }
        else {
            assertEquals(GameState.FIRST_PLAYER_TURN, game.getGameState());
            assertEquals(game.getActualPlayer(), actionResult.getPlayer());
        }
    }
}