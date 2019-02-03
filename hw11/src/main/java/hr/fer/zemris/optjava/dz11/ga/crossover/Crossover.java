package hr.fer.zemris.optjava.dz11.ga.crossover;

import hr.fer.zemris.generic.ga.GASolution;

/**
 * Models a crossover operator.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface Crossover<T extends GASolution> {

    /**
     * Creates a child solution from the given parent solutions and returns it.
     *
     * @param first The first parent.
     * @param second The second parent.
     * @return The created child.
     */
    T cross(T first, T second);
}
