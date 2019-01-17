package hr.fer.zemris.optjava.dz9.algorithm.mutation;

import hr.fer.zemris.optjava.dz9.algorithm.solution.Solution;

/**
 * Models a mutation operator.
 *
 * @param <T> Type of the solution.
 */
public interface Mutation<T extends Solution> {

    /**
     * Possibly mutates the given parent's copy and returns it.
     *
     * @param parent The given parent.
     * @return The given parent, or its modified copy.
     */
    T reproduce(T parent);
}
