package sk.tuke.kpi.kp.ak.gamelib.core.players;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void testMakeDamage() {
        Player player = new Human("player", 5);
        player.makeDamage(1);
        assertEquals(player.getLifeCount(), 4);
        player.makeDamage(5);
        assertEquals(player.getLifeCount(), 0);
        player.makeDamage(1);
        assertEquals(player.getLifeCount(), 0);
    }

    @Test
    public void testHeal() {
        Player player = new Human("player", 5);
        player.makeDamage(1);
        assertEquals(player.getLifeCount(), 4);
        player.heal();
        assertEquals(player.getLifeCount(), 5);
        player.heal();
        assertEquals(player.getLifeCount(), 5);
    }

    //TODO add item test
}