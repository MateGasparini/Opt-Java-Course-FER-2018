package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.INeighborhood;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * When running, chooses the random neighbor only if it is better than the
 * current solution. Tends to get stuck in local optima.
 *
 * @param <T> The type of the solution.
 * @author Mate Gašparini
 */
public class GreedyAlgorithm<T extends SingleObjectiveSolution>
        implements IOptAlgorithm<T> {

    /** Maximum number of iterations. */
    private static final int MAX_ITERATION = 1000;

    /** The specified solution decoder. */
    private IDecoder<T> decoder;

    /** The specified solution neighbor generator. */
    private INeighborhood<T> neighborhood;

    /** The specified starting solution. */
    private T startWith;

    /** The problem function. */
    private IFunction function;

    /** Proportionality between solution value and fitness. */
    private boolean minimize;

    /**
     * Constructor specifying the attributes.
     *
     * @param decoder The specified solution decoder.
     * @param neighborhood The specified solution neighbor generator.
     * @param startWith The specified starting solution.
     * @param function The problem function.
     * @param minimize The specified proportionality between solution value and
     *                 fitness.
     */
    public GreedyAlgorithm(IDecoder<T> decoder, INeighborhood<T> neighborhood,
                           T startWith, IFunction function, boolean minimize) {
        this.decoder = decoder;
        this.neighborhood = neighborhood;
        this.startWith = startWith;
        this.function = function;
        this.minimize = minimize;
    }

    @Override
    public T run() {
        T solution = startWith;
        solution.value = value(solution);
        solution.fitness = fitness(solution);

        for (int i = 0; i < MAX_ITERATION; i ++) {
            T neighbor = neighborhood.randomNeighbor(solution);
            neighbor.value = value(neighbor);
            neighbor.fitness = fitness(neighbor);

            if (neighbor.compareTo(solution) > 0) {
                solution = neighbor;
            }
        }

        return solution;
    }

    /**
     * Calculates the given solution's fitness.
     *
     * @param solution The given solution.
     * @return The calculated fitness.
     */
    private double fitness(T solution) {
        return minimize ? 1.0/solution.value : solution.value;
    }

    /**
     * Calculates the given solution's value.
     *
     * @param solution The given solution.
     * @return The calculated function value.
     */
    private double value(T solution) {
        return function.valueAt(decoder.decode(solution));
    }
}
