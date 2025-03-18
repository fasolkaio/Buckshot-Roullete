package sk.tuke.gamestudio.buckshot_roulette.core.actions;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.buckshot_roulette.core.GameState;

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
        shoot.execute();
        assertEquals(GameState.SECOND_PLAYER_TURN,game.getGameState());
    }

    @Test
    public void testShotYourself(){
        Game game = new Game("first player", "second player");
        Action shoot = new Shoot(game, true);
        ShootActionResult actionResult = (ShootActionResult)shoot.execute();
        if(actionResult.isShootResult() || !actionResult.isSelfShoot())
            assertEquals(GameState.SECOND_PLAYER_TURN,game.getGameState());
        else
            assertEquals(GameState.FIRST_PLAYER_TURN,game.getGameState());
    }
}