package sk.tuke.gamestudio.game.buckshot_roulette.core.weapon;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Human;
import sk.tuke.gamestudio.game.buckshot_roulette.core.players.Player;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;

public class GunTest {
    @Test
    public void testGunGenerate() {
        Gun gun = new Gun();
        assertTrue(() -> {
            return gun.getBulletsCount() >= 2
                    && gun.getLiveBulletsCount() <= 8
                    && gun.getLiveBulletsCount() >= 1
                    && gun.getLiveBulletsCount() <= ((int) round(gun.getBulletsCount()) * 0.7);
        });
    }

    @Test
    public void testBullets() {
        Gun gun = new Gun();
        assertTrue(() -> {
            int liveCount = 0;
            for (int i = 0; i < gun.getBulletsCount(); i++) {
                if (!gun.getBullets().get(i).isBlank()) {
                    liveCount++;
                }
            }
            return gun.getBulletsCount() == gun.getBullets().size()
                    && liveCount == gun.getLiveBulletsCount();
        });
    }

    @Test
    public void testRemoveBullet() {
        Gun gun = new Gun();
        int sizeBefore = gun.getBullets().size();
        int bulletsCountBerfore = gun.getBulletsCount();
        int liveBulletsCountBefore = gun.getLiveBulletsCount();
        boolean result = gun.removeBullet();
        assertEquals(sizeBefore - 1, gun.getBullets().size());
        assertEquals(bulletsCountBerfore - 1, gun.getBulletsCount());
        if (result)
            assertEquals(liveBulletsCountBefore - 1, gun.getLiveBulletsCount());

    }

    @Test
    public void testEmptyGunCheck() {
        Gun gun = new Gun();
        while (!gun.getBullets().isEmpty()) {
            gun.getBullets().remove(0);
        }
        assertThrows(UnsupportedOperationException.class, gun::checkBullet);
    }

    @Test
    public void testCheckBullet() {
        Gun gun = new Gun();
        assertEquals(!gun.getBullets().get(0).isBlank(), gun.checkBullet());
    }

    @Test
    public void tetShootNull() {
        Gun gun = new Gun();
        assertThrows(NullPointerException.class, () -> gun.shoot(null));
    }

    @Test
    public void testShoot() {
        Gun gun = new Gun();
        int bulletsBefore = gun.getBulletsCount();
        boolean iLive = gun.checkBullet();
        Player player = new Human("player", 5);
        boolean result = gun.shoot(player);
        assertEquals(bulletsBefore - 1, gun.getBulletsCount());
        assertEquals(iLive, result);
        if (result)
            assertEquals(4, player.getLifeCount());
        else
            assertEquals(5, player.getLifeCount());
    }
}