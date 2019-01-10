package hr.fer.zemris.optjava.dz9.mutation;

import hr.fer.zemris.optjava.dz9.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz9.solution.DoubleArraySolution;

import java.util.Random;

/**
 * <p>{@link Mutation} which mutates each {@link DoubleArraySolution}'s decision
 * value by a pseudo-random Gaussian distributed value.</p>
 * <p>Additionally, values are kept inside their hard constraint intervals.</p>
 *
 * @author Mate Gašparini
 */
public class NormalRandomMutation implements Mutation<DoubleArraySolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** Gaussian standard deviation. */
    private double sigma;

    /** Lower bounds for each decision value. */
    private double[] lowest;

    /** Upper bounds for each decision value. */
    private double[] highest;

    /**
     * Constructor specifying the standard deviation and the problem which
     * contains hard constraint information.
     *
     * @param sigma The specified standard deviation.
     * @param problem The specified problem.
     */
    public NormalRandomMutation(double sigma, MOOPProblem problem) {
        this.sigma = sigma;
        this.lowest = problem.xMin();
        this.highest = problem.xMax();
    }

    @Override
    public void mutate(DoubleArraySolution solution) {
        double[] values = solution.getValues();
        for (int i = 0; i < values.length; i ++) {
            values[i] += random.nextGaussian() * sigma;
            if (values[i] < lowest[i]) values[i] = lowest[i];
            else if (values[i] > highest[i]) values[i] = highest[i];
        }
    }
}
