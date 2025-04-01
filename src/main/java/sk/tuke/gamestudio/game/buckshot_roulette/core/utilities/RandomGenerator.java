package sk.tuke.gamestudio.game.buckshot_roulette.core.utilities;

import java.util.Random;

public class RandomGenerator {
    private static final Random RANDOM = new Random();
    private RandomGenerator(){
        throw new UnsupportedOperationException("Utility class cannot be created");
    }

    public static int randomIntBetween(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than max");
        }
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    public static boolean tossCoin() {
        return RANDOM.nextBoolean();
    }
}
