package implementacion.functions;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class FTest {
    private F f = new F();

    private double[] y = {0., 0., 0., 0.0117436, 0.135335, 0.0117436, 0., 0., 0.};
    private double[] x = {-2d, -1.5d, -1d, -0.5d, 0d, 0.5d, 1d, 1.5d, 2d};

    @Test
    public void value() throws Exception {
        assertThat(x.length, equalTo(y.length));
        for(int i=0; i<x.length; i++) {
            assertThat(f.value(x[i]), closeTo(y[i], 0.001));
        }
    }

}