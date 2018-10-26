package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;

/**
 * Models the function {@code f(x1, x2) = x1^2 + (x2 - 1)^2}.
 *
 * @author Mate Gašparini
 */
public class FirstFunction extends TwoVariableFunction {

    @Override
    public double getValue(Matrix point) {
        double first = x1(point);
        double second = x2(point) - 1;
        return first*first + second*second;
    }

    @Override
    public Matrix getGradient(Matrix point) {
        return new Matrix(new double[][] {
                {2 * x1(point)},
                {2 * x2(point) - 2}
        });
    }

    @Override
    public Matrix getHessianMatrix(Matrix point) {
        return new Matrix(new double[][]{
                {2, 0},
                {0, 2}
        });
    }
}
