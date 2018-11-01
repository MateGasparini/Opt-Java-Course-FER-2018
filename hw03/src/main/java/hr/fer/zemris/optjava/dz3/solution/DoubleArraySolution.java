package hr.fer.zemris.optjava.dz3.solution;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents a {@link SingleObjectiveSolution} stored as an array of
 * {@code double} values.
 *
 * @author Mate Gašparini
 */
public class DoubleArraySolution extends SingleObjectiveSolution {

    /** Array of solution values. */
    public double[] values;

    /**
     * Constructor specifying the size of the solution array.
     *
     * @param size The specified solution array size.
     */
    public DoubleArraySolution(int size) {
        values = new double[size];
    }

    /**
     * Constructs a solution with the same values array size and returns its
     * reference.
     *
     * @return Reference to the created solution.
     */
    public DoubleArraySolution newLikeThis() {
        return new DoubleArraySolution(values.length);
    }

    /**
     * Constructs a duplicate and returns its reference.
     *
     * @return Reference to the duplicate solution.
     */
    public DoubleArraySolution duplicate() {
        DoubleArraySolution duplicate = newLikeThis();
        duplicate.values = Arrays.copyOf(this.values, values.length);
        duplicate.value = this.value;
        duplicate.fitness = this.fitness;
        return duplicate;
    }

    /**
     * Generates pseudo-random real values between bounds specified in the given
     * bound arrays for each element of the solution array.
     *
     * @param random The given {@link Random} object.
     * @param lower The given lower bounds array.
     * @param upper The given upper bounds array.
     * @throws IllegalArgumentException If the given arrays are not of same
     *         length as the solution array.
     */
    public void randomize(Random random, double[] lower, double[] upper) {
        if (lower.length != values.length || upper.length != values.length) {
            throw new IllegalArgumentException(
                    "Given arrays must be of same length as the solution array."
            );
        }

        for (int i = 0; i < values.length; i ++) {
            double low = lower[i];
            double high = upper[i];
            if (low > high) {
                double lowCopy = low;
                low = high;
                high = lowCopy;
            }
            values[i] = low + random.nextDouble() * (high - low);
        }
    }
}
