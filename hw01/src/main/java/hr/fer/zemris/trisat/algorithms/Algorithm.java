package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;

/**
 * Interface which specifies a method used for solving a {@link SATFormula}.
 *
 * @author Mate Gašparini
 */
public interface Algorithm {

    /**
     * Tries to solve the given {@link SATFormula} and return the solution as a
     * {@link BitVector} assignment.
     *
     * @param formula The given {@link SATFormula}.
     * @return The first found solution {@link BitVector}, or {@code null} if no
     *         solution was found.
     */
    BitVector solve(SATFormula formula);
}
