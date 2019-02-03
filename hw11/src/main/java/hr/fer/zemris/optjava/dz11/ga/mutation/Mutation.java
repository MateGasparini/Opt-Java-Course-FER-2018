package hr.fer.zemris.optjava.dz11.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;

/**
 * Models a mutation operator.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface Mutation<T extends GASolution> {

    /**
     * Mutates the given solution in some way.
     *
     * @param solution The given solution.
     */
    void mutate(T solution);
}
