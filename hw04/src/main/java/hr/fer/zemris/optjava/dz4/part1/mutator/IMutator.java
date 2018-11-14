package hr.fer.zemris.optjava.dz4.part1.mutator;

import hr.fer.zemris.optjava.dz4.part1.solution.SingleObjectiveSolution;

/**
 * Models an object which is able to mutate a {@link SingleObjectiveSolution}.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface IMutator<T extends SingleObjectiveSolution> {

    /**
     * Mutates the given solution in some way.
     *
     * @param solution The given solution.
     */
    void mutate(T solution);
}
