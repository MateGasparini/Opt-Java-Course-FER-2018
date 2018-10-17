package hr.fer.zemris.trisat.algorithms;

import java.util.Random;

/**
 * Helper superclass which contains some resources that are shared between all
 * of its subclasses.
 *
 * @author Mate Gašparini
 */
public abstract class EvolutionaryAlgorithm implements Algorithm {

    /** The maximum number of iterations (tries) of the algorithm. */
    protected static final int MAX_ITERATION = 100_000;

    /** The single {@link Random} instance used for random number generation. */
    protected static Random random = new Random();
}
