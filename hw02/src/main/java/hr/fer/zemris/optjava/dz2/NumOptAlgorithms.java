package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.util.NumOptUtil;

import java.util.function.BiFunction;

/**
 * Class containing static methods for finding the function minimum using line
 * search based algorithms.
 *
 * @author Mate Gašparini
 */
public class NumOptAlgorithms {

    /**
     * Private default constructor.
     */
    private NumOptAlgorithms() {
    }

    /**
     * Returns the given function minimum using the gradient descent algorithm
     * to calculate it.
     *
     * @param function The given function.
     * @param maxIteration Maximum number of line search iterations.
     * @param point The starting point.
     * @return The calculated function minimum (or {@code null} if not found).
     */
    public static Matrix gradientDescent(IFunction function, int maxIteration, Matrix point) {
        return lineSearch(function, maxIteration, point, NumOptAlgorithms::dGradient);
    }

    /**
     * Returns the given function minimum using the Newton's method algorithm
     * to calculate it.
     *
     * @param function The given function.
     * @param maxIteration Maximum number of line search iterations.
     * @param point The starting point.
     * @return The calculated function minimum (or {@code null} if not found).
     */
    public static Matrix newtonsMethod(IHFunction function, int maxIteration, Matrix point) {
        return lineSearch(function, maxIteration, point, NumOptAlgorithms::dNewton);
    }

    /**
     * Returns the given function minimum using the specified way of calculating
     * the direction vector.
     *
     * @param function The given function.
     * @param maxIteration Maximum number of iterations.
     * @param point The given starting point.
     * @param direction The specified way of calculating the direction vector
     *        from the given function and the current point.
     * @return The calculated function minimum (or {@code null} if not found).
     */
    private static Matrix lineSearch(IFunction function, int maxIteration,
                Matrix point, BiFunction<IFunction, Matrix, Matrix> direction) {
        for (int i = 0; i < maxIteration; i ++) {
            NumOptUtil.printPoint(point, System.out);
            double value = function.getValue(point);
            System.out.println(value);
            if (value < NumOptUtil.EPSILON) {
                return point;
            }

            Matrix d = direction.apply(function, point);
            double lambda = NumOptUtil.getLambda(function, point, d);
            point.plusEquals(d.times(lambda));
        }
        return null;
    }

    /**
     * Returns the direction vector for the gradient descent algorithm.
     *
     * @param function The given function.
     * @param point The given point.
     * @return The calculated direction vector.
     */
    private static Matrix dGradient(IFunction function, Matrix point) {
        Matrix gradient = function.getGradient(point);
        return gradient.times(-1.0);
    }

    /**
     * Returns the direction vector for the Newton's method algorithm.
     *
     * @param function The given function.
     * @param point The given point.
     * @return The calculated direction vector.
     */
    private static Matrix dNewton(IFunction function, Matrix point) {
        Matrix gradient = function.getGradient(point);
        Matrix hessian = ((IHFunction) function).getHessianMatrix(point);
        return hessian.inverse().times(-1.0).times(gradient);
    }
}
