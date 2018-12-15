package hr.fer.zemris.optjava.dz7.function;

/**
 * Represents some scalar function which is defined on an n-dimensional real
 * number space.
 *
 * @author Mate Gašparini
 */
public interface Function {

    /**
     * Returns the number of dimensions of the number space the function is
     * defined on.
     *
     * @return The dimensionality of the solution vector.
     */
    int getDimension();

    /**
     * Returns the scalar value at the given point.
     *
     * @param point The given point (n-dimensional real vector).
     * @return The function value at the given point.
     */
    double valueAt(double[] point);
}
