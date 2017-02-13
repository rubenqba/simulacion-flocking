package core;

import lombok.Getter;

import java.security.SecureRandom;
import java.util.Random;


public  class RandomGenerator {
    private static RandomGenerator instance;

    @Getter
    private Random random;

    private RandomGenerator() {
        random = new SecureRandom();
    }

    public static RandomGenerator getInstance() {
        if (instance == null)
            instance = new RandomGenerator();
        return instance;
    }
}

