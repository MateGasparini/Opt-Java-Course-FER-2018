package hr.fer.zemris.optjava.dz10.algorithm;

import hr.fer.zemris.optjava.dz10.crossover.BlxAlphaCrossover;
import hr.fer.zemris.optjava.dz10.mutation.NormalRandomMutation;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.solution.DoubleArraySolution;

/**
 * {@link NSGA2} in which the distance is calculated in objective space (as
 * specified in the default version of the algorithm).
 *
 * @author Mate Gašparini
 */
public class ObjectiveSpaceNSGA2 extends NSGA2 {

    /** The problem-specific number of objectives. */
    private int numberOfObjectives;

    /** The lower bounds of all objective values. */
    private double[] fMin;

    /** The upper bounds of all objective values. */
    private double[] fMax;

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
    public ObjectiveSpaceNSGA2(MOOPProblem problem, int populationSize,
            int maxIteration, double sigmaShare, double alpha, double sigma) {
        super(problem, populationSize, maxIteration, sigmaShare, alpha, sigma);

        this.numberOfObjectives = problem.getNumberOfObjectives();
        this.fMin = problem.fMin();
        this.fMax = problem.fMax();
    }

    @Override
    protected double distance(DoubleArraySolution start, DoubleArraySolution end) {
        double[] startObjectives = start.getObjectives();
        double[] endObjectives = end.getObjectives();

        double distance = 0.0;
        for (int component = 0; component < numberOfObjectives; component ++) {
            double value = (startObjectives[component] - endObjectives[component])
                    / (fMax[component] - fMin[component]);
            distance += value*value;
        }
        distance = Math.sqrt(distance);
        return distance;
    }
}
