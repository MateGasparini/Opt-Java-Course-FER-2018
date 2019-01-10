package hr.fer.zemris.optjava.dz9.selection;

import hr.fer.zemris.optjava.dz9.solution.Solution;

/**
 * Models a {@link Solution} selection.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface Selection<T extends Solution> {

    /**
     * Selects some solution from the given population and returns it.
     *
     * @param population The given population.
     * @return The selected solution.
     */
    T select(T[] population);
}
