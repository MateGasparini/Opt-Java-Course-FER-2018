package hr.fer.zemris.optjava.dz7.algorithm;

import hr.fer.zemris.optjava.dz7.function.Function;

/**
 * {@link PSO} which uses the globally best solution as the only neighbor.
 *
 * @author Mate Gašparini
 */
public class GlobalPSO extends PSO {

    /** The best solution's position vector. */
    private double[] globalBestPosition;

    /** The best solution's fitness value. */
    private double globalBestFitness;

    /**
     * Constructor which specifies the algorithm parameters.
     *
     * @param function The specified function which needs to be minimized.
     * @param populationSize The specified size of the population of solutions.
     * @param maxIteration The maximum number of iterations before the algorithm stops.
     * @param minError The minimum error value before the algorithm stops.
     * @param positionsMax The maximum (only when generating) position value.
     * @param velocitiesMax The maximum velocity value.
     * @param c1 The factor of individuality.
     * @param c2 The factor of sociability.
     */
    public GlobalPSO(Function function, int populationSize, int maxIteration, double minError,
                     double positionsMax, double velocitiesMax, double c1, double c2) {
        super(function, populationSize, maxIteration, minError,
                positionsMax, velocitiesMax, c1, c2);
        this.globalBestPosition = new double[dimension];
    }

    @Override
    protected void updateNeighborhoodBests() {
        for (int i = 0; i < populationSize; i ++) {
            if (fitness[i] > globalBestFitness) {
                globalBestFitness = fitness[i];
                globalBestPosition = positions[i];
            }
        }
    }

    @Override
    protected double neighborhoodBest(int i, int d) {
        return globalBestPosition[d];
    }

    @Override
    protected double[] bestPosition() {
        return globalBestPosition;
    }
}
