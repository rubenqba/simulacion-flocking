package implementacion.functions;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class ExponentialFunction implements UnivariateFunction {

    /**
     * @see {tesis de Armando, pp 49}
     * @param x
     * @return
     */
    @Override
    public double value(double x) {
        return 1 / 1 + Math.exp(-10 * (x - 0.4));
    }
}
