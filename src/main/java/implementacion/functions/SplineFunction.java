package implementacion.functions;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

public class SplineFunction implements UnivariateFunction {

    protected static final double[] x = {0.00, 0.01, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.10, 0.11, 0.12,
            0.13, 0.14, 0.15, 0.16, 0.17, 0.18, 0.19, 0.20, 0.21, 0.22, 0.23, 0.24, 0.25, 0.26, 0.27, 0.28, 0.29,
            0.30, 0.31, 0.32, 0.33, 0.34, 0.35, 0.36, 0.37, 0.38, 0.39, 0.40, 0.41, 0.42, 0.43, 0.44, 0.45, 0.46,
            0.47, 0.48, 0.49, 0.50, 0.51, 0.52, 0.53, 0.54, 0.55, 0.56, 0.57, 0.58, 0.59, 0.60, 0.61, 0.62, 0.63,
            0.64, 0.65, 0.66, 0.67, 0.68, 0.69, 0.70, 0.71, 0.72, 0.73, 0.74, 0.75, 0.76, 0.77, 0.78, 0.79, 0.80,
            0.81, 0.82, 0.83, 0.84, 0.85, 0.86, 0.87, 0.88, 0.89, 0.90, 0.91, 0.92, 0.93, 0.94, 0.95, 0.96, 0.97,
            0.98, 0.99, 1.00};
    protected static final double[] y = {0., 0.0320675, 0.0640965, 0.0960487, 0.127886, 0.159569, 0.191062,
            0.222326, 0.253324, 0.284019, 0.314376, 0.344359, 0.373932, 0.403061, 0.431714, 0.459856, 0.487458,
            0.514488, 0.540917, 0.566716, 0.591859, 0.61632, 0.640076, 0.663103, 0.685382, 0.706893, 0.727618,
            0.747544, 0.766657, 0.784947, 0.802404, 0.819024, 0.834801, 0.849735, 0.863828, 0.877084, 0.889509,
            0.901113, 0.911909, 0.921912, 0.93114, 0.939613, 0.947355, 0.954392, 0.960752, 0.966466, 0.971566,
            0.976087, 0.980064, 0.983536, 0.98654, 0.989115, 0.991301, 0.993135, 0.994657, 0.995903, 0.996909,
            0.997709, 0.998334, 0.998814, 0.999175, 0.999441, 0.999631, 0.999764, 0.999854, 0.999914, 0.999951,
            0.999973, 0.999986, 0.999993, 0.999997, 0.999999, 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
            1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.};

    private PolynomialSplineFunction function;

    public SplineFunction() {
        SplineInterpolator interpolator = new SplineInterpolator();
        function = interpolator.interpolate(x, y);
    }

    @Override
    public double value(double x) {
        return function.value(x);
    }
}