package hr.fer.zemris.optjava.dz13.algorithm.initialization;

import hr.fer.zemris.optjava.dz13.algorithm.solution.ScoreSolution;

import java.util.List;

/**
 * Models a population initialization.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface Initialization<T extends ScoreSolution> {

    /**
     * Returns an initialized population of the given size.
     *
     * @param populationSize The given size.
     * @return The created population.
     */
    List<T> getPopulation(int populationSize);
}
