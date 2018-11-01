package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * Models a decoder which has the ability of decoding some input solution to an
 * array of {@code double} values.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface IDecoder<T extends SingleObjectiveSolution> {

    /**
     * Returns a {@code double} array representation of the given solution.
     *
     * @param solution The given solution.
     * @return The decoded array.
     */
    double[] decode(T solution);

    /**
     * Fills the given {@code double} array with decoded values of the given
     * solution.
     *
     * @param solution The given solution.
     * @param values The given array.
     */
    void decode(T solution, double[] values);
}
