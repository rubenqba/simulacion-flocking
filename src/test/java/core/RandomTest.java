package core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ruben.bressler on 10/3/2016.
 */
public class RandomTest {
    @Test
    public void getRandom() throws Exception {
        for(int i=0; i<5; i++) {
            System.out.println(Random.getInstance().getRandom().nextInt(10));
        }
    }

}