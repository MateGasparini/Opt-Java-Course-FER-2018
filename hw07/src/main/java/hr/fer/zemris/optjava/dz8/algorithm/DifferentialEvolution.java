package hr.fer.zemris.optjava.dz8.algorithm;

import hr.fer.zemris.optjava.dz8.function.Function;

/**
 * Represents the <i>Differential Evolution Algorithm</i> (DE), more
 * specifically - DE/best/1/bin. That means the base vector is always the best
 * vector from the current generation (the increased selection pressure is
 * compensated through dynamic value of the mutant factor), there is a single
 * linear combination used to generate mutant vectors and the crossover is based
 * on random choice of values.
 *
 * @author Mate Gašparini
 */
public class DifferentialEvolution extends OptAlgorithm {

    /** Minimum random vector component value. */
    private static final double MIN = -1.0;

    /** Maximum random vector component value. */
    private static final double MAX = 1.0;

    /** Used to scale the shift vector when creating the mutant vector. */
    private double mutantFactor;

    /** Used to choose between components of either the base or the mutant vector. */
    private double crossoverChance;

    /** Current generation vectors. */
    private double[][] targetVectors;

    /** Current generation function values. */
    private double[] targetValues;

    /** Current trial vectors. */
    private double[][] trialVectors;

    /** Current trial function values. */
    private double[] trialValues;

    /** Best function value. */
    private double bestValue;

    /** Index of the best target vector/value. */
    private int bestIndex;

    /**
     * Constructor which specifies the needed algorithm parameters.
     *
     * @param function The specified function which needs to be minimized.
     * @param populationSize The specified size of the population of solutions.
     * @param maxIteration The maximum number of iterations before the algorithm stops.
     * @param minError The minimum error value needed to stop.
     * @param mutantFactor Used to scale the shift vector when creating the mutant vector.
     * @param crossoverChance Used to choose between components for the trial vector.
     */
    public DifferentialEvolution(Function function, int populationSize,
                                 int maxIteration, double minError,
                                 double mutantFactor, double crossoverChance) {
        super(function, populationSize, maxIteration, minError);
        this.mutantFactor = mutantFactor;
        this.crossoverChance = crossoverChance;

        this.targetVectors = new double[populationSize][dimension];
        this.targetValues = new double[populationSize];
        this.trialVectors = new double[populationSize][dimension];
        this.trialValues = new double[populationSize];
    }

    @Override
    public double[] run() {
        randomizeTargetVectors();
        evaluateTarget();

        for (int generation = 0; generation < maxIteration; generation ++) {
            System.out.println("Gen: " + generation + ", Best: " + targetValues[bestIndex]);
            if (targetValues[bestIndex] < minError) {
                System.out.println("Finished after reaching min error.");
                return targetVectors[bestIndex];
            }

            for (int i = 0; i < targetVectors.length; i ++) {
                int r0 = bestIndex;
                int r1 = findR1(i, r0);
                int r2 = findR2(i, r0, r1);
                createMutantVector(i, r0, r1, r2);
                createTrialVector(i);
            }

            evaluateTrial();
            pickNewTarget();
        }

        System.out.println("Finished after reaching max iteration.");
        return targetVectors[bestIndex];
    }

    /**
     * Randomizes the starting generation of target vectors.
     */
    private void randomizeTargetVectors() {
        for (int i = 0; i < targetVectors.length; i ++) {
            for (int j = 0; j < targetVectors[i].length; j ++) {
                targetVectors[i][j] = randomInRange(MIN, MAX);
            }
        }
    }

    /**
     * Evaluates the current target vector generation and updates the best value.
     */
    private void evaluateTarget() {
        bestValue = function.valueAt(targetVectors[0]);
        bestIndex = 0;
        targetValues[0] = bestValue;

        for (int i = 1; i < targetVectors.length; i ++) {
            double value = function.valueAt(targetVectors[i]);
            if (value < bestValue) {
                bestValue = value;
                bestIndex = i;
            }
            targetValues[i] = value;
        }
    }

    /**
     * Evaluates the current trial vectors.
     */
    private void evaluateTrial() {
        for (int i = 0; i < trialVectors.length; i ++) {
            trialValues[i] = function.valueAt(trialVectors[i]);
        }
    }

    /**
     * Finds the first random index different than the given arguments.
     *
     * @param i The current vector index.
     * @param r0 The best vector index.
     * @return Some random index different than the given arguments.
     */
    private int findR1(int i, int r0) {
        int r1;
        do {
            r1 = randomInRange(0, targetVectors.length);
        } while (r1 == i || r1 == r0);
        return r1;
    }

    /**
     * Finds the first random index different than the given arguments.
     *
     * @param i The current vector index.
     * @param r0 The best vector index.
     * @param r1 The already found random index.
     * @return Some random index different than the given arguments.
     */
    private int findR2(int i, int r0, int r1) {
        int r2;
        do {
            r2 = randomInRange(0, targetVectors.length);
        } while (r2 == i || r2 == r0 || r2 == r1);
        return r2;
    }

    /**
     * Creates the {@code i}-ith mutant vector by using the vectors at the given
     * indexes.
     *
     * @param i The current vector index.
     * @param r0 The index of the best vector.
     * @param r1 The index of some random vector.
     * @param r2 The index of some random vector.
     */
    private void createMutantVector(int i, int r0, int r1, int r2) {
        double[] mutant = trialVectors[i];
        double[] base = targetVectors[r0];
        double[] random1 = targetVectors[r1];
        double[] random2 = targetVectors[r2];

        for (int d = 0; d < dimension; d ++) {
            double dMutantFactor = mutantFactor + 0.001 * (randomInRange(0.0, 1.0) - 0.5);
            mutant[d] = base[d] + dMutantFactor * (random1[d] - random2[d]);
        }
    }

    /**
     * Creates the {@code i}-th trial vector by crossing over the target and
     * trial vectors specified by the given index.
     *
     * @param i The given index.
     */
    private void createTrialVector(int i) {
        double[] target = targetVectors[i];
        double[] trial = trialVectors[i];

        int mandatoryComponent = randomInRange(0, dimension);
        for (int d = 0; d < dimension; d ++) {
            if (d != mandatoryComponent && random.nextDouble() >= crossoverChance) {
                trial[d] = target[d];
            }
        }
    }

    /**
     * Refreshes the target vector generation by adding better (or equally good)
     * trial vectors into the population (and removing the ones that are worse).
     */
    private void pickNewTarget() {
        for (int i = 0; i < trialValues.length; i ++) {
            double trialValue = trialValues[i];
            if (trialValue <= targetValues[i]) {
                targetValues[i] = trialValue;

                double[] targetVectorCopy = targetVectors[i];
                targetVectors[i] = trialVectors[i];
                trialVectors[i] = targetVectorCopy;

                if (trialValue < bestValue) {
                    bestValue = trialValue;
                    bestIndex = i;
                }
            }
        }
    }
}
