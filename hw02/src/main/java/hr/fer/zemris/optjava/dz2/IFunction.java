package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * Interface that models a scalar function whose domain is an n-dimensional
 * vector of real numbers.
 *
 * @author Mate Gašparini
 */
public interface IFunction {

    /**
     * Returns the number of dimensions of the domain vector.
     *
     * @return The number of variables.
     */
    int getNumberOfVariables();

    /**
     * Returns the value of the function at the given point.
     *
     * @param point The given point.
     * @return The value at the given point.
     */
    double getValue(Matrix point);

    /**
     * Returns the gradient vector of the function at the given point.
     *
     * @param point The given point.
     * @return The gradient at the given point.
     */
    Matrix getGradient(Matrix point);
}
