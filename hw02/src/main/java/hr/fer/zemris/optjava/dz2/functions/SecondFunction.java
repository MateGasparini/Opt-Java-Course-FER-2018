package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IHFunction;

/**
 * Models the function {@code f(x1, x2) = (x1 - 1)^2 + 10*(x2 - 2)^2}.
 *
 * @author Mate Gašparini
 */
public class SecondFunction extends TwoVariableFunction {

    @Override
    public double getValue(Matrix point) {
        double first = x1(point) - 1;
        double second = x2(point) - 2;
        return first*first + 10*second*second;
    }

    @Override
    public Matrix getGradient(Matrix point) {
        return new Matrix(new double[][] {
                {2 * x1(point) - 2},
                {20 * x2(point) - 40}
        });
    }

    @Override
    public Matrix getHessianMatrix(Matrix point) {
        return new Matrix(new double[][] {
                {2, 0},
                {0, 20}
        });
    }
}
