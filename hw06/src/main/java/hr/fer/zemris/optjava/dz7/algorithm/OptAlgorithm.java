package hr.fer.zemris.optjava.dz7.algorithm;

import hr.fer.zemris.optjava.dz7.function.Function;

import java.util.Random;

/**
 * Represents some generic optimization algorithm.
 *
 * @author Mate Gašparini
 */
public abstract class OptAlgorithm {

    /** The single {@link Random} instance. */
    protected Random random = new Random();

    /** The specified function which needs minimization. */
    protected Function function;

    /** The specified size of the population of solutions. */
    protected int populationSize;

    /** The specified dimension of a single solution/the specified function. */
    protected int dimension;

    /** Maximum number of iterations before the algorithm stops. */
    protected int maxIteration;

    /** Maximum fitness value of some solution before the algorithm stops. */
    protected double maxFitness;

    /**
     * Constructor which specifies the usual algorithm parameters.
     *
     * @param function The specified function which needs to be minimized.
     * @param populationSize The specified size of the population of solutions.
     * @param maxIteration The maximum number of iterations before the algorithm stops.
     * @param minError The minimum error value before the algorithm stops.
     */
    public OptAlgorithm(Function function, int populationSize,
                        int maxIteration, double minError) {
        this.function = function;
        this.populationSize = populationSize;
        this.maxIteration = maxIteration;

        this.maxFitness = fitness(minError);
        this.dimension = function.getDimension();
    }

    /**
     * Runs the algorithm and, after maximum number of iterations or finding the
     * solution with an error less or equal to the minimum specified error,
     * returns the best found solution.
     *
     * @return The solution with the highest fitness/lowest error value.
     */
    public abstract double[] run();

    /**
     * Helper method used to pseudo-randomly generate a real value from the
     * specified interval.
     *
     * @param min The lower bound of the specified interval.
     * @param max The upper bound of the specified interval.
     * @return Some random real value from the interval.
     */
    protected double randomInRange(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    /**
     * Helper method which calculates the fitness for the given error value.
     *
     * @param value The given error value.
     * @return The calculated fitness value.
     */
    protected double fitness(double value) {
        return 1.0/value;
    }
}
