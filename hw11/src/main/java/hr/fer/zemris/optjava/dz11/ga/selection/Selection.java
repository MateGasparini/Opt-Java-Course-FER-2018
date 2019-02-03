package hr.fer.zemris.optjava.dz11.ga.selection;

import hr.fer.zemris.generic.ga.GASolution;

import java.util.List;

/**
 * Models a selection operator.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface Selection<T extends GASolution> {

    /**
     * Selects a single solution from the given population.
     *
     * @param population The given population.
     * @return The selected solution.
     */
    T select(List<T> population);
}
