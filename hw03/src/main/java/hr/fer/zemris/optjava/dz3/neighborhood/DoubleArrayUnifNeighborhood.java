package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.util.Random;

/**
 * Calculates the random neighbor by changing a single variable for a uniformly
 * distributed random delta value.
 *
 * @author Mate Gašparini
 */
public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution> {

    /** Array of maximum random changes. */
    private double[] deltas;

    /** Internal {@link Random} instance. */
    private Random random = new Random();

    /**
     * Constructor specifying the maximum random changes.
     *
     * @param deltas The specified array of maximum random changes.
     */
    public DoubleArrayUnifNeighborhood(double[] deltas) {
        this.deltas = deltas;
    }

    @Override
    public DoubleArraySolution randomNeighbor(DoubleArraySolution point) {
        DoubleArraySolution neighbor = point.newLikeThis();
        for (int i = 0; i < neighbor.values.length; i ++) {
            neighbor.values[i] = point.values[i] + random.nextDouble()*deltas[i];
        }
        return neighbor;
    }
}
