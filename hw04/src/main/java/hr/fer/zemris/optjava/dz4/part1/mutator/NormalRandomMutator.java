package hr.fer.zemris.optjava.dz4.part1.mutator;

import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

import java.util.Random;

/**
 * {@link IMutator} which mutates each {@link DoubleArraySolution}'s value by a
 * pseudo-random Gaussian distributed value.
 *
 * @author Mate Gašparini
 */
public class NormalRandomMutator implements IMutator<DoubleArraySolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** Gaussian standard deviation. */
    private double sigma;

    /**
     * Constructor specifying the standard deviation.
     *
     * @param sigma The specified standard deviation.
     */
    public NormalRandomMutator(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public void mutate(DoubleArraySolution solution) {
        for (int i = 0; i < solution.values.length; i ++) {
            solution.values[i] += random.nextGaussian() * sigma;
        }
    }
}
