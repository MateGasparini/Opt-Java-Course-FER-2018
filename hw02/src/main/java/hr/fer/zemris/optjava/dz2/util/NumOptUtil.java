package hr.fer.zemris.optjava.dz2.util;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IFunction;

import java.io.PrintStream;

/**
 * Utility class containing methods used in numeric optimization algorithms and
 * other useful methods.
 *
 * @author Mate Gašparini
 */
public final class NumOptUtil {

    /** Lowest positive real value which can be used instead of 0.0. */
    public static final double EPSILON = 1E-6;

    /**
     * Private default constructor.
     */
    private NumOptUtil() {
    }

    /**
     * Prints the given point to the given stream.
     *
     * @param point The given point.
     * @param stream The given stream.
     * @throws IllegalArgumentException If the given point matrix has multiple
     *         columns.
     */
    public static void printPoint(Matrix point, PrintStream stream) {
        if (point.getColumnDimension() != 1) throw new IllegalArgumentException(
                "Point must have a single column."
        );
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        for (int i = 0; i < point.getRowDimension(); i ++) {
            builder.append(point.get(i, 0)).append(',').append(' ');
        }
        builder.delete(builder.length()-2, builder.length());
        builder.append(')');
        stream.println(builder.toString());
    }

    /**
     * Calculates the lambda factor using the bisection method and returns its
     * value.
     *
     * @param function The given function.
     * @param x The given point.
     * @param d The given direction vector.
     * @return The corresponding lambda factor.
     */
    public static double getLambda(IFunction function, Matrix x, Matrix d) {
        double lower = 0.0;
        double upper = getUpperLambda(function, x, d);
        double bisection;
        while (true) {
            bisection = bisection(lower, upper);
            double derivative = deriveTheta(function, x, bisection, d);
            if (Math.abs(derivative) < EPSILON) {
                break;
            } else if (derivative > 0.0) {
                upper = bisection;
            } else {
                lower = bisection;
            }
        }
        return bisection;
    }

    /**
     * <p>Returns the first lambda for which the derivative of the theta function
     * has a positive value.</p>
     * <p>Lambda is exponentially increased (by a factor of 2).</p>
     *
     * @param function The given function.
     * @param x The given point.
     * @param d The given direction.
     * @return The corresponding upper lambda value.
     */
    private static double getUpperLambda(IFunction function, Matrix x, Matrix d) {
        double upper = 1.0;
        while (true) {
            double derivative = deriveTheta(function, x, upper, d);
            if (derivative > 0.0) break;
            upper *= 2.0;
        }
        return upper;
    }

    /**
     * Returns the bisection between the given real values.
     *
     * @param x The first given value.
     * @param y The second given value.
     * @return The calculated bisection.
     */
    private static double bisection(double x, double y) {
        return (x + y) / 2.0;
    }

    /**
     * Returns the derivative value of the theta function.
     *
     * @param function The given function.
     * @param x The given point.
     * @param lambda The given lambda factor.
     * @param d The given direction.
     * @return The calculated derivative.
     */
    private static double deriveTheta(IFunction function, Matrix x,
                                      double lambda, Matrix d) {
        Matrix point = x.plus(d.times(lambda));
        Matrix gradient = function.getGradient(point);
        return gradient.transpose().times(d).get(0, 0);
    }
}
