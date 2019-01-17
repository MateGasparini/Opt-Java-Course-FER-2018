package hr.fer.zemris.optjava.dz9.algorithm.selection;

import hr.fer.zemris.optjava.dz9.algorithm.solution.Solution;

import java.util.List;

/**
 * Models a solution selection.
 *
 * @param <T> Type of the solution.
 */
public interface Selection<T extends Solution> {

    /**
     * Selects a solution from the given population.
     *
     * @param population The given population.
     * @return The selected solution.
     */
    T select(List<T> population);
}
