package hr.fer.zemris.optjava.dz9.algorithm;

import hr.fer.zemris.optjava.dz9.crossover.BlxAlphaCrossover;
import hr.fer.zemris.optjava.dz9.mutation.NormalRandomMutation;
import hr.fer.zemris.optjava.dz9.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz9.solution.DoubleArraySolution;

/**
 * {@link NSGA} in which the distance is calculated in decision space.
 *
 * @author Mate Gašparini
 */
public class DecisionSpaceNSGA extends NSGA {

    /** The problem-specific number of decision values. */
    private int numberOfSolutions;

    /** The lower bounds of all decision values. */
    private double[] xMin;

    /** The upper bounds of all decision values. */
    private double[] xMax;

    /**
     * Constructor specifying various algorithm parameters.
     *
     * @param problem The specified optimization problem.
     * @param populationSize The specified population size.
     * @param maxIteration The specified number of iterations.
     * @param sigmaShare The specified value used for limiting the sharing function.
     * @param alpha The specified {@link BlxAlphaCrossover} parameter.
     * @param sigma The specified {@link NormalRandomMutation} parameter.
     */
    public DecisionSpaceNSGA(MOOPProblem problem, int populationSize,
            int maxIteration, double sigmaShare, double alpha, double sigma) {
        super(problem, populationSize, maxIteration, sigmaShare, alpha, sigma);

        this.numberOfSolutions = problem.getNumberOfSolutions();
        this.xMin = problem.xMin();
        this.xMax = problem.xMax();
    }

    @Override
    protected double distance(DoubleArraySolution start, DoubleArraySolution end) {
        double[] startValues = start.getValues();
        double[] endValues = end.getValues();

        double distance = 0.0;
        for (int component = 0; component < numberOfSolutions; component ++) {
            double value = (startValues[component] - endValues[component])
                    / (xMax[component] - xMin[component]);
            distance += value*value;
        }
        distance = Math.sqrt(distance);
        return distance;
    }
}
