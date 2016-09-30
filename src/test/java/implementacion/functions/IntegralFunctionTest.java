package implementacion.functions;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class IntegralFunctionTest {
    IntegralFunction f = new IntegralFunction();

    private double[] x = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1};
    private double[] y = {0., 0.314376, 0.591859, 0.802404, 0.93114, 0.98654, 0.999175, 0.999997, 1., 1., 1.};

    @Test
    public void value() throws Exception {
        assertThat(x.length, equalTo(y.length));
        for (int i = 0; i < x.length; i++) {
            double y1 = f.value(x[i]);
            assertThat(y1, closeTo(y[i], 0.000001));
        }
    }

}