package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * Models a generic optimization algorithm.
 *
 * @param <T> Type of the solution to the problem.
 * @author Mate Gašparini
 */
public interface IOptAlgorithm<T extends SingleObjectiveSolution> {

    /**
     * Runs the algorithm and returns the best solution found.
     */
    T run();
}
