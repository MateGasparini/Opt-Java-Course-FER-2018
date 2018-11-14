package hr.fer.zemris.optjava.dz4.part1.crossover;

import hr.fer.zemris.optjava.dz4.part1.solution.SingleObjectiveSolution;

/**
 * Models some crossover operator.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface ICrossover<T extends SingleObjectiveSolution> {

    /**
     * Crosses the given parent solutions and returns the resulting child
     * solution.
     *
     * @param firstParent The first given parent.
     * @param secondParent The second given parent.
     * @return The resulting child.
     */
    T cross(T firstParent, T secondParent);
}
