package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * Models a neighborhood of some solution point.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface INeighborhood<T extends SingleObjectiveSolution> {

    /**
     * Returns a random neighbor point of the given solution.
     *
     * @param point The given solution.
     * @return Some random neighbor.
     */
    T randomNeighbor(T point);
}
