package implementacion;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;

/**
 * Created by ruben.bressler on 9/4/2016.
 */
public class MovimientoBoidIntegral extends MovimientoBoidMejorado {

    private double epsilon;
    private int maxEval = 100000;

    public MovimientoBoidIntegral(double epsilon) {
        this.epsilon = epsilon;
    }

    /**
     * Esta función permite obtener la cota adecuada para la velocidad
     * usando la funcion integral
     *
     * @param x
     * @return
     */
    protected double f(double x) {
        if (x <= 0)
            return 0d;
        if (x >= epsilon)
            return 1;

        TrapezoidIntegrator integrator = new TrapezoidIntegrator();

        return integrator.integrate(maxEval, new G(), 0, x)/integrator.integrate(maxEval, new G(), 0, epsilon);
    }

    private class G implements UnivariateFunction {

        public double value(double t) {
            if (t >= 1 || t <= -1) {
                return 0;
            }

            return Math.exp(-Math.pow(t - 1, 2)) * Math.exp(-Math.pow(t + 1, 2));
        }
    }

}
