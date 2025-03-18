package sk.tuke.gamestudio.buckshot_roulette.core;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.buckshot_roulette.core.Game;
import sk.tuke.gamestudio.buckshot_roulette.core.GameMode;
import sk.tuke.gamestudio.buckshot_roulette.core.GameState;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Dealer;
import sk.tuke.gamestudio.buckshot_roulette.core.players.Human;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testSingleGameCreated() {
        Game game = new Game("first");
        assertNotNull(game.getGun());
        assertEquals(GameState.FIRST_PLAYER_TURN, game.getGameState());
        assertInstanceOf(Human.class, game.getActualPlayer());
        assertInstanceOf(Dealer.class, game.getNotActualPlayer());
        assertEquals(GameMode.Single, game.getGameMode());
    }

    @Test
    public void testP2PGameCreated() {
        Game game = new Game("first", "second");
        assertNotNull(game.getGun());
        assertEquals(GameState.FIRST_PLAYER_TURN, game.getGameState());
        assertInstanceOf(Human.class, game.getActualPlayer());
        assertInstanceOf(Human.class, game.getNotActualPlayer());
        assertEquals(GameMode.P2P, game.getGameMode());
    }

    @Test
    public void testB2BGameCreated() {
        Game game = new Game(GameMode.B2B);
        assertNotNull(game.getGun());
        assertEquals(GameState.FIRST_PLAYER_TURN, game.getGameState());
        assertInstanceOf(Dealer.class, game.getActualPlayer());
        assertInstanceOf(Dealer.class, game.getNotActualPlayer());
        assertEquals(GameMode.B2B, game.getGameMode());
    }

    @Test
    public void testTestingGameCreated() {
        Game game = new Game(GameMode.Testing);
        assertNotNull(game.getGun());
        assertEquals(GameState.FIRST_PLAYER_TURN, game.getGameState());
        assertInstanceOf(Human.class, game.getActualPlayer());
        assertInstanceOf(Dealer.class, game.getNotActualPlayer());
        assertEquals(GameMode.Testing, game.getGameMode());
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
        game.getActualPlayer().getDamage(10);
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
        game.getActualPlayer().getDamage(10);
        assertEquals(GameState.ROUND_ENDED, game.getGameState());
    }
}