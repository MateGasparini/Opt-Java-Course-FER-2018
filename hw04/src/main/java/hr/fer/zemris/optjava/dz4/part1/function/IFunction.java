package hr.fer.zemris.optjava.dz4.part1.function;

/**
 * Models a mathematical function which returns a scalar and is defined on an
 * n-dimensional real number space.
 *
 * @author Mate Gašparini
 */
public interface IFunction {

    /**
     * Returns the size of the solution (the variable count).
     *
     * @return The solution size.
     */
    int getSolutionSize();

    /**
     * Returns the value at the given point.
     *
     * @param point The given point.
     * @return The value at the given point.
     */
    double valueAt(double[] point);
}
