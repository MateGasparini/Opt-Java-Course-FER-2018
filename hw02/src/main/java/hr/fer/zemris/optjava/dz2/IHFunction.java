package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * {@link IFunction} that additionally has the capability of returning the
 * Hessian matrix at some point.
 *
 * @author Mate Gašparini
 */
public interface IHFunction extends IFunction {

    /**
     * Returns the Hessian matrix at the given point.
     *
     * @param point The given point.
     * @return The Hessian matrix at the given point.
     */
    Matrix getHessianMatrix(Matrix point);
}
