package hr.fer.zemris.optjava.dz4.part1.crossover;

import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

import java.util.Random;

/**
 * {@link ICrossover} which crosses two {@link DoubleArraySolution}s by randomly
 * choosing a value in the interval specified by the given parents and extended
 * by some factor {@code ALPHA} in both directions.
 *
 * @author Mate Gašparini
 */
public class BlxAlphaCrossover implements ICrossover<DoubleArraySolution> {

    /** Factor by which the crossover interval is extended. */
    private static final double ALPHA = 0.33;

    /** Single {@link Random} instance. */
    private Random random = new Random();

    @Override
    public DoubleArraySolution cross(DoubleArraySolution firstParent,
                                     DoubleArraySolution secondParent) {
        DoubleArraySolution child = firstParent.newLikeThis();

        for (int i = 0; i < firstParent.values.length; i ++) {
            double lower = firstParent.values[i];
            double upper = secondParent.values[i];

            if (lower > upper) {
                double lowerCopy = lower;
                lower = upper;
                upper = lowerCopy;
            }

            double extension = (upper - lower) * ALPHA;
            lower -= extension;
            upper += extension;
            child.values[i] = randomBetween(lower, upper);
        }

        return child;
    }

    /**
     * Returns a pseudo-randomly generated value from the specified interval.
     *
     * @param lower The specified lower bound (inclusive).
     * @param upper The specified upper bound (exclusive).
     * @return Some random number from the specified interval.
     */
    private double randomBetween(double lower, double upper) {
        return random.nextDouble() * (upper - lower) + lower;
    }
}
