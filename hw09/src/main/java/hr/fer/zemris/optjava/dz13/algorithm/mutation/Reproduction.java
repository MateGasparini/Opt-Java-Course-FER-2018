package hr.fer.zemris.optjava.dz13.algorithm.mutation;

import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;

/**
 * Returns the (unmodified) given parent.
 *
 * @author Mate Gašparini
 */
public class Reproduction implements Mutation<TreeSolution> {

    @Override
    public TreeSolution reproduce(TreeSolution parent) {
        return parent;
    }
}
