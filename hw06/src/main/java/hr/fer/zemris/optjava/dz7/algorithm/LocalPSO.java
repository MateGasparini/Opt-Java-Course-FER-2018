package hr.fer.zemris.optjava.dz7.algorithm;

import hr.fer.zemris.optjava.dz7.function.Function;

/**
 * {@link PSO} which uses the locally best solutions as the neighborhood.
 *
 * @author Mate Gašparini
 */
public class LocalPSO extends PSO {

    /** The locally best solutions' position vectors. */
    private double[][] localBestPosition;

    /** The locally best solutions' fitness values. */
    private double[] localBestFitness;

    /** Number of neighbors from each side of each solution. */
    private int halfWidth;

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
    public LocalPSO(Function function, int populationSize, int maxIteration,
                    double minError, int halfWidth,
                    double positionsMax, double velocitiesMax,
                    double c1, double c2) {
        super(function, populationSize, maxIteration, minError,
                positionsMax, velocitiesMax, c1, c2);
        this.halfWidth = halfWidth;

        this.localBestPosition = new double[populationSize][dimension];
        this.localBestFitness = new double[populationSize];
    }

    @Override
    protected void updateNeighborhoodBests() {
        for (int i = 0; i < populationSize; i ++) {
            for (int offset = -halfWidth; offset <= halfWidth; offset ++) {
                if (offset == 0) continue;
                int index = Math.floorMod(i + offset, populationSize);
                if (fitness[index] > localBestFitness[i]) {
                    localBestFitness[i] = fitness[index];
                    localBestPosition[i] = positions[index];
                }
            }
        }
    }

    @Override
    protected double neighborhoodBest(int i, int d) {
        return localBestPosition[i][d];
    }
}
