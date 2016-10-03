package core;

import lombok.Getter;


public  class Random {
    private static Random instance;

    @Getter
    private java.util.Random random;

    private Random() {
        random = new java.util.Random(0);
    }

    public static Random getInstance() {
        if (instance == null)
            instance = new Random();
        return instance;
    }
}

