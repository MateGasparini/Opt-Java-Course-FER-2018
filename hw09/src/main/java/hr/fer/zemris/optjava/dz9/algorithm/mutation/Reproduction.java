package hr.fer.zemris.optjava.dz9.algorithm.mutation;

import hr.fer.zemris.optjava.dz9.algorithm.solution.SymbolicTreeSolution;

/**
 * Returns the (unmodified) given parent.
 *
 * @author Mate Gašparini
 */
public class Reproduction implements Mutation<SymbolicTreeSolution> {

    @Override
    public SymbolicTreeSolution reproduce(SymbolicTreeSolution parent) {
        return parent;
    }
}
