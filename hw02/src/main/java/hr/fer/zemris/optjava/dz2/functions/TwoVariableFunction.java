package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IHFunction;

/**
 * Models some mathematical function with two variables and contains helper
 * methods for easier variable access.
 *
 * @author Mate Gašparini
 */
public abstract class TwoVariableFunction implements IHFunction {

    @Override
    public int getNumberOfVariables() {
        return 2;
    }

    protected double x1(Matrix point) {
        return point.get(0, 0);
    }

    protected double x2(Matrix point) {
        return point.get(1, 0);
    }
}
