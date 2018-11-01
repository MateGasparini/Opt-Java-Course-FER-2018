package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.util.Random;

/**
 * Calculates the random neighbor by changing a single variable for a Gaussian
 * ("normally") distributed random delta value.
 *
 * @author Mate Gašparini
 */
public class DoubleArrayNormNeighborhood implements INeighborhood<DoubleArraySolution> {

    /** Array of standard deviations for random changes. */
    private double[] deltas;

    /** Internal {@link Random} instance. */
    private Random random = new Random();

    /**
     * Constructor specifying the standard deviations for random changes.
     *
     * @param deltas The specified array of standard deviations for random changes.
     */
    public DoubleArrayNormNeighborhood(double[] deltas) {
        this.deltas = deltas;
    }

    @Override
    public DoubleArraySolution randomNeighbor(DoubleArraySolution point) {
        DoubleArraySolution neighbor = point.newLikeThis();
        for (int i = 0; i < neighbor.values.length; i ++) {
            neighbor.values[i] = point.values[i] + random.nextGaussian()*deltas[i];
        }
        return neighbor;
    }
}
