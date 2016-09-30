package implementacion.functions;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;

public class IntegralFunction implements UnivariateFunction {
    private static final int maxEval = 1000000;
    private TrapezoidIntegrator integrator = new TrapezoidIntegrator();

    @Override
    public double value(double x) {
        return x == 0 ? 0 : integrator.integrate(maxEval, new F(), 0, x) / integrator.integrate(maxEval, new
                F(), 0, 1);
    }
}
