package implementacion.functions;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class SplineFunctionTest {

    private double[] x = {0.55, 0.551, 0.552, 0.553, 0.554, 0.555, 0.556, 0.557, 0.558, 0.559, 0.56};
    private double[] y = {0.995903, 0.996014, 0.996122, 0.996229, 0.996332, 0.996434, 0.996533, 0.996631, 0.996726,
            0.996818, 0.996909};

    @Test
    public void value() throws Exception {
        assertThat(SplineFunction.x.length, equalTo(SplineFunction.y.length));

        assertThat(x.length, equalTo(y.length));
        SplineFunction spline = new SplineFunction();
        for (int i = 1; i < x.length; i++) {
            assertThat(spline.value(x[i]), closeTo(y[i], 0.000001));
        }

        IntegralFunction integral = new IntegralFunction();
        for (int i = 1; i < x.length; i++) {
            double s = spline.value(x[i]);
            double t = integral.value(x[i]);
            assertThat(s, closeTo(t, 0.000001));
        }
    }

}