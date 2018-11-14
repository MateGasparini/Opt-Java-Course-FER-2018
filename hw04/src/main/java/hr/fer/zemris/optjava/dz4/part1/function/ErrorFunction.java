package hr.fer.zemris.optjava.dz4.part1.function;

import Jama.Matrix;

/**
 * <p>Error function for some equation system whose left side can be represented
 * as:</p>
 * {@code a*x1 + b*x1^3*x2 + c*e^(d*x3)*(1 + cos(e*x4)) + f*x4*x5^2}.
 *
 * @author Mate Gašparini
 */
public class ErrorFunction implements IFunction {

    /** Number of variables (a-f). */
    public static final int VARIABLE_COUNT = 6;

    /** Number of coefficients (x1-x5). */
    public static final int COEFFICIENT_COUNT = 5;

    /** Specified system coefficients matrix. */
    private Matrix coefficients;

    /** Specified system right side vector. */
    private Matrix rightSide;

    /**
     * Constructor specifying the coefficients and the right side.
     *
     * @param coefficients The specified system coefficients matrix.
     * @param rightSide The specified system right side vector.
     * @throws IllegalArgumentException If the dimensions are invalid.
     */
    public ErrorFunction(Matrix coefficients, Matrix rightSide) {
        if (coefficients.getColumnDimension() != COEFFICIENT_COUNT) {
            throw new IllegalArgumentException("Coefficient size must be "
                    + COEFFICIENT_COUNT + ".");
        }

        if (coefficients.getRowDimension() != rightSide.getRowDimension()) {
            throw new IllegalArgumentException("Number of lines must be consistent.");
        }

        this.coefficients = coefficients;
        this.rightSide = rightSide;
    }

    @Override
    public int getSolutionSize() {
        return VARIABLE_COUNT;
    }

    @Override
    public double valueAt(double[] point) {
        if (point.length != VARIABLE_COUNT) {
            throw new IllegalArgumentException("Point size must be " + VARIABLE_COUNT + ".");
        }

        double value = 0.0;
        for (int row = 0; row < rightSide.getRowDimension(); row ++) {
            double calculated = calculateValue(point, row);
            double error = rightSide.get(row, 0) - calculated;
            value += error*error;
        }
        return value;
    }

    /**
     * Calculates the value of the left side of the equation at the given row
     * using the given point.
     *
     * @param point The given point.
     * @param row The given row.
     * @return The calculated value.
     */
    private double calculateValue(double[] point, int row) {
        double[] x = new double[COEFFICIENT_COUNT];
        for (int i = 0; i < COEFFICIENT_COUNT; i ++) {
            x[i] = coefficients.get(row, i);
        }

        return point[0] * x[0]
                + point[1] * Math.pow(x[0], 3) * x[1]
                + point[2] * Math.exp(point[3] * x[2]) * (1 + Math.cos(point[4] * x[3]))
                + point[5] * x[3] * x[4] * x[4];
    }
}
