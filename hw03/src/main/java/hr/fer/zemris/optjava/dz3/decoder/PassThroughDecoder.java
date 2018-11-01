package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.util.Arrays;

/**
 * {@link IDecoder} which simply passes the given {@link DoubleArraySolution}
 * through.
 *
 * @author Mate Gašparini
 */
public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {

    @Override
    public double[] decode(DoubleArraySolution solution) {
        return Arrays.copyOf(solution.values, solution.values.length);
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException If the given solution and value array
     *         are not of same length.
     */
    @Override
    public void decode(DoubleArraySolution solution, double[] values) {
        if (solution.values.length != values.length) {
            throw new IllegalArgumentException(
                    "Given solution and value array must be of same length."
            );
        }

        for (int i = 0; i < solution.values.length; i ++) {
            values[i] = solution.values[i];
        }
    }
}
