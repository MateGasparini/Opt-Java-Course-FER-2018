package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IHFunction;

/**
 * Models some error function for some system of equations.
 *
 * @author Mate Gašparini
 */
public class ThirdFunction implements IHFunction {

    /** Coefficients for each equation in its row. */
    private Matrix coefficients;

    /** Vector of given right side (y) values. */
    private Matrix rightSide;

    /**
     * Constructor specifying the coefficients matrix and the right side vector.
     *
     * @param coefficients The specified coefficients.
     * @param rightSide The specified right side values.
     */
    public ThirdFunction(Matrix coefficients, Matrix rightSide) {
        this.coefficients = coefficients;
        this.rightSide = rightSide;
    }

    @Override
    public int getNumberOfVariables() {
        return coefficients.getColumnDimension();
    }

    @Override
    public double getValue(Matrix point) {
        double[][] mistakes = coefficients.times(point).minus(rightSide).getArray();
        double value = 0.0;
        for (double[] row : mistakes) {
            double mistake = row[0];
            value += mistake*mistake;
        }
        return value;
    }

    @Override
    public Matrix getGradient(Matrix point) {
        double[] mistakes = coefficients.times(point).minus(rightSide).getColumnPackedCopy();
        int size = mistakes.length;
        double[][] gradientArray = new double[size][1];
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j ++) {
                gradientArray[i][0] += 2.0 * mistakes[j] * coefficients.get(j, i);
            }
        }
        return new Matrix(gradientArray);
    }

    @Override
    public Matrix getHessianMatrix(Matrix point) {
        int size = point.getRowDimension();
        double[][] gradientArray = new double[size][size];
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j ++) {
                for (int k = 0; k < size; k ++) {
                    gradientArray[i][j] += 2.0 * coefficients.get(k, i) * coefficients.get(k, j);
                }
            }
        }
        return new Matrix(gradientArray);
    }
}
