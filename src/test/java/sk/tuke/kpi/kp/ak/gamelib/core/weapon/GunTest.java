package sk.tuke.kpi.kp.ak.gamelib.core.weapon;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GunTest {
    @Test
    public void testGunGenerate(){
        Gun gun = new Gun();
        assertTrue(() -> {
            return gun.getBulletsCount() >= 2
                    && gun.getLiveBulletsCount() <= 8
                    && gun.getLiveBulletsCount() >= 1
                    && gun.getLiveBulletsCount() <= (gun.getBulletsCount() - 1);
        }) ;
    }

    @Test
    public void testBullets(){
        Gun gun = new Gun();
        assertTrue(() -> {
            int liveCount = 0;
            for(int i = 0; i < gun.getBulletsCount(); i++){
                if(!gun.getBullets().get(i).isBlank()){
                    liveCount++;
                }
            }
            return  gun.getBulletsCount() == gun.getBullets().size()
                    && liveCount == gun.getLiveBulletsCount();
        });
    }

    @Test
    public void testRemoveBullet(){
        Gun gun = new Gun();
        int sizeBefore = gun.getBullets().size();
        gun.removeBullet();
        assertEquals(sizeBefore - 1, gun.getBullets().size());
    }

    @Test
    public void testEmptyGunCheck(){
        Gun gun = new Gun();
        while (!gun.getBullets().isEmpty()){
            gun.getBullets().remove(0);
        }
        assertThrows(NullPointerException.class, gun::checkBullet);
    }

    @Test
    public void testCheckBullet(){
        Gun gun = new Gun();
        assertEquals(!gun.getBullets().get(0).isBlank(), gun.checkBullet());
    }
}