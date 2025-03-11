package sk.tuke.kpi.kp.ak.gamelib.core.actions;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.Game;
import sk.tuke.kpi.kp.ak.gamelib.core.GameState;

import static org.junit.jupiter.api.Assertions.*;

public class ShootTest {
    @Test
    public void nullTest(){
        Action shoot = new Shoot(false, null);
        assertThrows(NullPointerException.class, shoot::execute);
    }

    @Test
    public void testShotOpponent(){
        Game game = new Game(false,"first player", "second player");
        Action shoot = new Shoot(false, game);
        shoot.execute();
        assertEquals(GameState.SECOND_PLAYER_TURN,game.getGameState());
    }

    @Test
    public void testShotYourself(){
        Game game = new Game(false,"first player", "second player");
        Action shoot = new Shoot(true, game);
        ActionResult action = shoot.execute();
        if(action == ActionResult.HIT_SUCCESS)
            assertEquals(GameState.SECOND_PLAYER_TURN,game.getGameState());
        else
            assertEquals(GameState.FIRST_PLAYER_TURN,game.getGameState());
    }
}