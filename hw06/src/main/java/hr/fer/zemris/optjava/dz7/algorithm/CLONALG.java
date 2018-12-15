package hr.fer.zemris.optjava.dz7.algorithm;

import hr.fer.zemris.optjava.dz7.function.Function;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Represents the <i>Clonal Selection Algorithm</i> (de Castro and Zuben, 1999.,
 * 2000., 2002.), inspired by the immunological system.
 *
 * @author Mate Gašparini
 */
public class CLONALG extends OptAlgorithm {

    /** Maximum change value for the hypermutation of clones. */
    private static final double DELTA = 0.5;

    /** Factor used for the number of clones generated for each selected solution. */
    private double beta;

    /** Number of solutions selected for cloning. */
    private int selectionSize;

    /** Factor which affects the slope of the hypermutation probability function. */
    private double rho;

    /** Maximum value used for generating the initial solutions. */
    private double maxValue;

    /** The main population of antibodies. */
    private Antibody[] population;

    /** The population of clones. */
    private Antibody[] clones;

    /** The population of randomly generated children. */
    private Antibody[] children;

    /**
     * Constructor which specifies the algorithm parameters.
     *
     * @param function The specified function which needs to be minimized.
     * @param populationSize The specified size of the population of solutions.
     * @param maxIteration The maximum number of iterations before the algorithm stops.
     * @param minError The minimum error value before the algorithm stops.
     * @param beta Factor used for calculating the number of clones.
     * @param selectionSize Number of solutions selected for cloning.
     * @param rho Factor used for the hypermutation probability function.
     * @param maxValue Maximum value used for generating the initial solutions.
     * @param d Number of randomly generated children in each iteration.
     */
    public CLONALG(Function function, int populationSize,
                   int maxIteration, double minError, double beta,
                   int selectionSize, double rho, double maxValue, int d) {
        super(function, populationSize, maxIteration, minError);
        this.beta = beta;
        this.selectionSize = selectionSize;
        this.rho = rho;
        this.maxValue = maxValue;

        this.population = new Antibody[populationSize];
        initClones();
        this.children = new Antibody[d];
    }

    @Override
    public double[] run() {
        randomizePopulation();
        for (int generation = 0; generation < maxIteration; generation ++) {
            evaluate(population);
            if (population[0].affinity >= maxFitness) return population[0].solution;

            generateClones();
            hypermutateClones();
            evaluate(clones);

            selectClones();
            randomizeChildren();
            insertChildren();
        }
        evaluate(population);
        return population[0].solution;
    }

    /**
     * Creates the empty clone population.
     */
    private void initClones() {
        int cloneCount = 0;
        for (int i = 1; i <= selectionSize; i ++) {
            cloneCount += (int) (beta*selectionSize / i);
        }
        clones = new Antibody[cloneCount];
    }

    /**
     * Initializes the population of randomly generated antibodies.
     */
    private void randomizePopulation() {
        for (int i = 0; i < populationSize; i ++) {
            population[i] = new Antibody(randomSolution());
        }
    }

    /**
     * Returns a random {@code double} array solution in range specified by
     * {@code maxValue}.
     *
     * @return The randomly generated array of real values.
     */
    private double[] randomSolution() {
        double[] solution = new double[dimension];
        for (int i = 0; i < solution.length; i ++) {
            solution[i] = randomInRange(-maxValue, maxValue);
        }
        return solution;
    }

    /**
     * Calculates the fitness (affinity) for each antibody of the given
     * population of antibodies, stores its value in each antibody and sorts the
     * population according to its value (in descending order).
     *
     * @param population The given population of antibodies.
     */
    private void evaluate(Antibody[] population) {
        for (Antibody antibody : population) {
            antibody.affinity = fitness(function.valueAt(antibody.solution));
        }
        Arrays.sort(population, Comparator.reverseOrder());
    }

    /**
     * <p>Generates the population of clones.</p>
     * <p>Each antibody of the original population is cloned proportionally to
     * its affinity value.</p>
     */
    private void generateClones() {
        int cloneIndex = 0;
        for (int i = 0; i < selectionSize; i ++) {
            Antibody original = population[i];
            int cloneCount = (int) (beta*selectionSize / (i+1));
            for (int cloneCounter = 0; cloneCounter < cloneCount; cloneCounter ++) {
                Antibody clone = original.copy();
                clones[cloneIndex++] = clone;
            }
        }
    }

    /**
     * Hypermutates each solution from the clone population inversely
     * proportional to its (normalized) affinity.
     */
    private void hypermutateClones() {
        double averageFitness = 0.0;
        for (Antibody clone : clones) {
            averageFitness += clone.affinity;
        }
        averageFitness /= clones.length;

        // Never mutate the first clone.
        for (int cloneIndex = 1; cloneIndex < clones.length; cloneIndex ++) {
            Antibody clone = clones[cloneIndex];
            double proportionalFitness = clone.affinity / averageFitness;

            double mutationProbability = Math.exp(-rho * proportionalFitness);
            for (int i = 0; i < clone.solution.length; i ++) {
                if (random.nextDouble() < mutationProbability) {
                    clone.solution[i] += randomInRange(-DELTA, DELTA);
                }
            }
        }
    }

    /**
     * Fills the population with first few (best) clones.
     */
    private void selectClones() {
        for (int i = 0; i < populationSize; i ++) {
            population[i] = clones[i];
        }
    }

    /**
     * Randomizes the children population solutions.
     */
    private void randomizeChildren() {
        for (int i = 0; i < children.length; i ++) {
            children[i] = new Antibody(randomSolution());
        }
    }

    /**
     * Replaces the worst solutions from the main population with solutions from
     * the children population.
     */
    private void insertChildren() {
        for (int i = 0; i < children.length; i ++) {
            population[populationSize-1 - i] = children[i];
        }
    }

    /**
     * Class which represents some solution and its affinity (fitness) value.
     */
    private static class Antibody implements Comparable<Antibody> {

        /** Solution values. */
        double[] solution;

        /** Fitness value. */
        double affinity;

        /**
         * Constructor specifying the solution values.
         *
         * @param solution The specified solution values.
         */
        Antibody(double[] solution) {
            this.solution = solution;
        }

        /**
         * Constructor specifying the solution values and the affinity value.
         *
         * @param solution The specified solution values.
         * @param affinity The specified affinity value.
         */
        Antibody(double[] solution, double affinity) {
            this.solution = solution;
            this.affinity = affinity;
        }

        /**
         * Returns a copy of the antibody (containing both the solution values,
         * and the affinity).
         *
         * @return Reference to the copy of the antibody.
         */
        Antibody copy() {
            double[] solution = new double[this.solution.length];
            System.arraycopy(this.solution, 0, solution, 0, solution.length);
            return new Antibody(solution, this.affinity);
        }

        @Override
        public int compareTo(Antibody o) {
            return Double.compare(this.affinity, o.affinity);
        }
    }
}
