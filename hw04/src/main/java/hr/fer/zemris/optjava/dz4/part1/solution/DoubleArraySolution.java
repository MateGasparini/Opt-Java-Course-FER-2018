package hr.fer.zemris.optjava.dz4.part1.solution;

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
     * Generates pseudo-random real values between bounds specified in the given
     * bounds for each element of the solution array.
     *
     * @param random The given {@link Random} object.
     * @param lower The given lower bound value.
     * @param upper The given upper bound value.
     */
    public void randomize(Random random, double lower, double upper) {
        for (int i = 0; i < values.length; i ++) {
            if (lower > upper) {
                double lowerCopy = lower;
                lower = upper;
                upper = lowerCopy;
            }
            values[i] = lower + random.nextDouble() * (upper - lower);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
