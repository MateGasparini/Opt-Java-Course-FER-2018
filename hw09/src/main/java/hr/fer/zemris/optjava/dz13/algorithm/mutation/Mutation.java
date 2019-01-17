package hr.fer.zemris.optjava.dz13.algorithm.mutation;

import hr.fer.zemris.optjava.dz13.algorithm.solution.ScoreSolution;

/**
 * Models a mutation operator.
 *
 * @param <T> Type of the solution.
 */
public interface Mutation<T extends ScoreSolution> {

    /**
     * Possibly mutates the given parent's copy and returns it.
     *
     * @param parent The given parent.
     * @return The given parent, or its modified copy.
     */
    T reproduce(T parent);
}
