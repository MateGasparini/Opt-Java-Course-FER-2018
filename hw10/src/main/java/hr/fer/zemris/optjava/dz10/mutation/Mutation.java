package hr.fer.zemris.optjava.dz10.mutation;

import hr.fer.zemris.optjava.dz10.solution.Solution;

/**
 * Models a {@link Solution} mutator.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface Mutation<T extends Solution> {

    /**
     * Mutates the given solution in some way.
     *
     * @param solution The given solution.
     */
    void mutate(T solution);
}
