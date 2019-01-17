package hr.fer.zemris.optjava.dz13.algorithm.crossover;

import hr.fer.zemris.optjava.dz13.algorithm.solution.ScoreSolution;

/**
 * Models a crossover operator.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface Crossover<T extends ScoreSolution> {

    /**
     * Crosses the given parent solutions and returns the resulting child
     * solution.
     *
     * @param first The first given parent.
     * @param second The second given parent.
     * @return The resulting child.
     */
    T cross(T first, T second);
}
