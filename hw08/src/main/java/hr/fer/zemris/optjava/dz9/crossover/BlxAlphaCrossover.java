package hr.fer.zemris.optjava.dz9.crossover;

import hr.fer.zemris.optjava.dz9.solution.DoubleArraySolution;

import java.util.Random;

/**
 * {@link Crossover} which crosses two {@link DoubleArraySolution}s by randomly
 * choosing a value in the interval specified by the given parents and extended
 * by some specified factor {@code alpha} in both directions.
 *
 * @author Mate Gašparini
 */
public class BlxAlphaCrossover implements Crossover<DoubleArraySolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** The specified factor. */
    private double alpha;

    /**
     * Constructor specifying the alpha factor which is used for extending the
     * crossover interval.
     *
     * @param alpha The specified factor.
     */
    public BlxAlphaCrossover(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public DoubleArraySolution cross(DoubleArraySolution first,
            DoubleArraySolution second) {
        double[] firstValues = first.getValues();
        double[] secondValues = second.getValues();
        double[] childValues = new double[firstValues.length];

        for (int i = 0; i < childValues.length; i ++) {
            double lower = firstValues[i];
            double upper = secondValues[i];

            if (lower > upper) {
                double lowerCopy = lower;
                lower = upper;
                upper = lowerCopy;
            }

            double extension = (upper - lower)*alpha;
            lower -= extension;
            upper += extension;
            childValues[i] = randomInRange(lower, upper);
        }

        return new DoubleArraySolution(childValues);
    }

    /**
     * Returns a pseudo-randomly generated value from the specified interval.
     *
     * @param min The specified lower bound (inclusive).
     * @param max The specified upper bound (exclusive).
     * @return Some random number from the specified interval.
     */
    private double randomInRange(double min, double max) {
        return min + (max - min)*random.nextDouble();
    }
}
