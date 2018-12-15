package hr.fer.zemris.optjava.dz7.algorithm;

import hr.fer.zemris.optjava.dz7.function.Function;

/**
 * Represents the <i>Particle Swarm Optimization</i> optimization algorithm
 * (Reynolds, 1987.), inspired by the movement of a flock of birds.
 *
 * @author Mate Gašparini
 */
public abstract class PSO extends OptAlgorithm {

    /** Ending inertia weight factor. */
    private static final double MIN_INERTIA_WEIGHT = 0.4;

    /** Starting inertia weight factor. */
    private static final double MAX_INERTIA_WEIGHT = 0.9;

    /** Maximum (only when generating) position value. */
    private double positionsMax;

    /** Maximum velocity value. */
    private double velocitiesMax;

    /** Factor of individuality. */
    private double c1;

    /** Factor of sociability. */
    private double c2;

    /** Contains position vectors for each solution. */
    protected double[][] positions;

    /** Contains velocity vectors for each solution. */
    private double[][] velocities;

    /** Contains the fitness values for each solution. */
    protected double[] fitness;

    /** Contains the best position vectors from each solution's history. */
    protected double[][] personalBestPositions;

    /** Contains the best fitness values from each solution's history. */
    private double[] personalBestFitness;

    /** Iteration (time) dependent inertia weight factor. */
    private double inertiaWeight;

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
    public PSO(Function function, int populationSize, int maxIteration,
               double minError, double positionsMax, double velocitiesMax,
               double c1, double c2) {
        super(function, populationSize, maxIteration, minError);
        this.positionsMax = positionsMax;
        this.velocitiesMax = velocitiesMax;
        this.c1 = c1;
        this.c2 = c2;

        this.positions = new double[populationSize][dimension];
        this.velocities = new double[populationSize][dimension];
        this.fitness = new double[populationSize];
        this.personalBestPositions = new double[populationSize][dimension];
        this.personalBestFitness = new double[populationSize];
    }

    @Override
    public double[] run() {
        initPopulation();
        inertiaWeight = MAX_INERTIA_WEIGHT;
        double inertiaWeightDelta = (MAX_INERTIA_WEIGHT - MIN_INERTIA_WEIGHT)
                / maxIteration;

        for (int generation = 0; generation < maxIteration; generation ++) {
            evaluatePopulation();
            updatePersonalBests();
            updateNeighborhoodBests();
            if (reachedMaxFitness()) break;
            updatePositions();
            inertiaWeight -= inertiaWeightDelta;
        }

        return bestPosition();
    }

    /**
     * Updates the stored best neighborhood solutions. Depends on the specific
     * implementation.
     */
    protected abstract void updateNeighborhoodBests();

    /**
     * Returns the best neighborhood solution's position.
     *
     * @param i The given index specifying the solution for the neighborhood.
     * @param d The given dimension index of the position vector.
     * @return The corresponding value from the specified position vector.
     */
    protected abstract double neighborhoodBest(int i, int d);

    /**
     * Returns the best found solution's position vector.
     *
     * @return The best position vector.
     */
    protected double[] bestPosition() {
        double bestFitness = personalBestFitness[0];
        double[] bestPosition = personalBestPositions[0];

        for (int i = 1; i < populationSize; i ++) {
            if (personalBestFitness[i] > bestFitness) {
                bestFitness = personalBestFitness[i];
                bestPosition = personalBestPositions[i];
            }
        }

        return bestPosition;
    }

    /**
     * Returns {@code true} if the specified maximum fitness value has been
     * reached by some solution.
     *
     * @return {@code true} if the maximum fitness has been reached, or
     *         {@code false} otherwise.
     */
    protected boolean reachedMaxFitness() {
        for (int i = 0; i < populationSize; i ++) {
            if (personalBestFitness[i] >= maxFitness) return true;
        }
        return false;
    }

    /**
     * Randomly initializes the population's positions and velocities.
     */
    private void initPopulation() {
        for (int i = 0; i < populationSize; i ++) {
            for (int d = 0; d < dimension; d ++) {
                positions[i][d] = randomInRange(-positionsMax, positionsMax);
                velocities[i][d] = randomInRange(-velocitiesMax, velocitiesMax);
            }
        }
    }

    private void evaluatePopulation() {
        for (int i = 0; i < populationSize; i ++) {
            fitness[i] = fitness(function.valueAt(positions[i]));
        }
    }

    private void updatePersonalBests() {
        for (int i = 0; i < populationSize; i ++) {
            if (fitness[i] > personalBestFitness[i]) {
                personalBestFitness[i] = fitness[i];
                personalBestPositions[i] = positions[i];
            }
        }
    }

    private void updatePositions() {
        for (int i = 0; i < populationSize; i ++) {
            for (int d = 0; d < dimension; d ++) {
                velocities[i][d] = inertiaWeight *velocities[i][d] + deltaVelocity(i, d);
                velocities[i][d] = inRange(velocities[i][d], -velocitiesMax, velocitiesMax);
                positions[i][d] += velocities[i][d]; // s <- s + v*1
            }
        }
    }

    private double deltaVelocity(int i, int d) {
        return c1*random.nextDouble() * (personalBestPositions[i][d] - positions[i][d])
                + c2*random.nextDouble() * (neighborhoodBest(i, d) - positions[i][d]);
    }

    private double inRange(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
