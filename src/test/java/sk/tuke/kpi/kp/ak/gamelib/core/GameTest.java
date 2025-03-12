package sk.tuke.kpi.kp.ak.gamelib.core;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Dealer;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Human;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testSingleGameCreated() {
        Game game = new Game("first");
        assertNotNull(game.getGun());
        assertEquals(GameState.FIRST_PLAYER_TURN, game.getGameState());
        assertInstanceOf(Human.class, game.getActualPlayer());
        assertInstanceOf(Dealer.class, game.getNotActualPlayer());
    }

    @Test
    public void testMultiGameCreated() {
        Game game = new Game("first", "second");
        assertNotNull(game.getGun());
        assertEquals(GameState.FIRST_PLAYER_TURN, game.getGameState());
        assertInstanceOf(Human.class, game.getActualPlayer());
        assertInstanceOf(Human.class, game.getNotActualPlayer());
    }

    @Test
    public void testGetPlayer(){
        Game game = new Game("first", "second");
        assertEquals("first", game.getActualPlayer().getName());
        assertEquals("second", game.getNotActualPlayer().getName());
        game.setGameState(GameState.SECOND_PLAYER_TURN);
        assertEquals("second", game.getActualPlayer().getName());
        assertEquals("first", game.getNotActualPlayer().getName());
    }

    @Test
    public void testGetWinner(){
        Game game = new Game("first", "second");
        assertNull(game.getWinnerName());
        game.getActualPlayer().makeDamage(10);
        assertEquals("second", game.getWinnerName());
    }

    @Test
    public void testReloadGun(){
        Game game = new Game("first", "second");
        assertFalse(game.reloadGun());
        while (!game.getGun().isEmpty()){
            game.getGun().removeBullet();
        }
        assertTrue(game.reloadGun());
        assertFalse(game.getGun().isEmpty());
    }

    @Test
    public void testRoundOver(){
        Game game = new Game("first", "second");
        game.getActualPlayer().makeDamage(10);
        assertEquals(GameState.ROUND_ENDED, game.getGameState());
    }
}