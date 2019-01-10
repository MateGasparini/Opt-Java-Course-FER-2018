package hr.fer.zemris.optjava.dz9.crossover;

import hr.fer.zemris.optjava.dz9.solution.Solution;

/**
 * Models a {@link Solution} crossover operator.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface Crossover<T extends Solution> {

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
