package sk.tuke.kpi.kp.ak.gamelib.core.weapon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import sk.tuke.kpi.kp.ak.gamelib.core.players.Player;
import sk.tuke.kpi.kp.ak.gamelib.core.utilities.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Gun {
    private final int liveBulletsCount;
    private final int bulletsCount;
    private final List<Bullet> bullets;
    @Setter(AccessLevel.PRIVATE)
    private int damage;

    public Gun() {
        bullets = new ArrayList<>();
        damage = 1;
        bulletsCount = RandomGenerator.randomIntBetween(2,8);
        liveBulletsCount = RandomGenerator.randomIntBetween(1, bulletsCount - 1);
        generateBullets();
    }

    private void generateBullets() {
        for (int i = 0; i < bulletsCount; i++) {
            bullets.add(new Bullet(true));
        }
        int pos;
        for(int i = 0; i < liveBulletsCount;){
            pos = RandomGenerator.randomIntBetween(0, bulletsCount - 1);
            if(bullets.get(pos).isBlank()) {
                bullets.set(pos, new Bullet(false));
                i++;
            }
        }
    }

    // return true if next bullet is life
    public boolean checkBullet(){
        if(bullets.isEmpty()){
            throw new NullPointerException("bullets is empty");
        }

        return !bullets.get(0).isBlank();
    }

    public boolean removeBullet(){
        boolean isLive = checkBullet();
        bullets.remove(0);
        return isLive;
    }

    public boolean doubleDamage(){
        if(damage != 1)
            return false;
        setDamage(getDamage()*2);
        return true;
    }

    public void normalDamage(){
        setDamage(1);
    }

    public boolean shoot(Player player){
        if (removeBullet()){
            player.makeDamage(damage);
            return true;
        }
        return false;
    }
}
