package core;

import org.junit.Test;

/**
 * Created by ruben.bressler on 10/3/2016.
 */
public class RandomGeneratorTest {
    @Test
    public void getRandom() throws Exception {
        for(int i=0; i<5; i++) {
            System.out.println(RandomGenerator.getInstance().getRandom().nextInt(10));
        }
    }

}