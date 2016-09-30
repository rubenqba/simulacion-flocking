package implementacion.functions;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class F implements UnivariateFunction {

    public double value(double t) {
        if (t <= -1 || t >= 1) {
            return 0;
        }
        return Math.exp(-Math.pow(t - 1, -2)) * Math.exp(-Math.pow(t + 1, -2));
    }
}