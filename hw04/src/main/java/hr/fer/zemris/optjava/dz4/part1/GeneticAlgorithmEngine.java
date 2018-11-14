package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.dz4.part1.crossover.BlxAlphaCrossover;
import hr.fer.zemris.optjava.dz4.part1.crossover.ICrossover;
import hr.fer.zemris.optjava.dz4.part1.function.IFunction;
import hr.fer.zemris.optjava.dz4.part1.mutator.IMutator;
import hr.fer.zemris.optjava.dz4.part1.mutator.NormalRandomMutator;
import hr.fer.zemris.optjava.dz4.part1.selection.ISelection;
import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

import java.util.*;

/**
 * Simple elitist generational genetic algorithm.
 *
 * @author Mate Gašparini
 */
public class GeneticAlgorithmEngine {

    /** Lower bound used for generation of the starting solution. */
    private static final double STARTING_SOLUTION_LOWER = -2.0;

    /** Upper bound used for generation of the starting solution. */
    private static final double STARTING_SOLUTION_UPPER = 2.0;

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** Function which needs to be minimized. */
    private IFunction function;

    /** Size of the solution population. */
    private int populationSize;

    /** Minimum error value (when reached, the algorithm stops). */
    private double minError;

    /** Maximum generation (when reached, the algorithm stops). */
    private int maxGeneration;

    /** The specified selection type (used to select parents for crossover). */
    private ISelection<DoubleArraySolution> selection;

    /** The current solution population. */
    private List<DoubleArraySolution> population;

    /** The specified crossover operator. */
    private ICrossover<DoubleArraySolution> crossover = new BlxAlphaCrossover();

    /** The mutator used for mutation of generated children. */
    private IMutator<DoubleArraySolution> mutator;

    /**
     * Constructor specifying the algorithm parameters.
     *
     * @param function The specified function to minimize.
     * @param populationSize The specified population size.
     * @param minError The specified minimum error value.
     * @param maxGeneration The specified maximum generation.
     * @param selection The specified parent selection strategy.
     * @param sigma The specified standard deviation used for mutations.
     */
    public GeneticAlgorithmEngine(IFunction function, int populationSize,
                                  double minError, int maxGeneration,
                                  ISelection<DoubleArraySolution> selection,
                                  double sigma) {
        this.function = function;
        this.populationSize = populationSize;
        this.minError = minError;
        this.maxGeneration = maxGeneration;
        this.selection = selection;
        this.mutator = new NormalRandomMutator(sigma);
    }

    /**
     * Runs the algorithm until the maximum generation or minimum error is
     * reached and returns the best found solution.
     *
     * @return The best found solution.
     */
    public DoubleArraySolution run() {
        initRandomPopulation();
        for (int generation = 0; generation < maxGeneration; generation ++) {
            System.out.println(population.get(0));
            System.out.println("Generation: " + generation
                    + ", Error: " + population.get(0).value);
            if (population.get(0).value <= minError) {
                System.out.println("Stopping after reaching minimum error...");
                return population.get(0);
            }

            List<DoubleArraySolution> newPopulation = new ArrayList<>(populationSize);
            newPopulation.add(population.get(0));
            newPopulation.add(population.get(1));

            while (newPopulation.size() < populationSize) {
                DoubleArraySolution firstParent = selection.getSolution(population);
                DoubleArraySolution secondParent = selection.getSolution(population);

                DoubleArraySolution child = crossover.cross(firstParent, secondParent);
                mutator.mutate(child);
                newPopulation.add(child);
            }

            population = newPopulation;
            evaluatePopulationFitness();
        }

        System.out.println("Stopping after reaching " + maxGeneration + "th generation...");
        return population.get(0);
    }

    /**
     * Initializes the starting population with solutions with random values.
     */
    private void initRandomPopulation() {
        population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i ++) {
            DoubleArraySolution solution = new DoubleArraySolution(function.getSolutionSize());
            solution.randomize(random, STARTING_SOLUTION_LOWER, STARTING_SOLUTION_UPPER);
            population.add(solution);
        }
        evaluatePopulationFitness();
    }

    /**
     * Evaluates the fitness of each solution in the current population and
     * sorts the population according to the fitness values.
     */
    private void evaluatePopulationFitness() {
        for (DoubleArraySolution solution : population) {
            solution.value = value(solution);
            solution.fitness = fitness(solution);
        }

        population.sort(Comparator.reverseOrder());
        double worstFitness = population.get(population.size()-1).fitness;
        for (DoubleArraySolution solution : population) {
            solution.fitness -= worstFitness;
        }
    }

    /**
     * Returns the calculated value of the function for the given solution.
     *
     * @param solution The given solution.
     * @return The calculated function value.
     */
    private double value(DoubleArraySolution solution) {
        return function.valueAt(solution.values);
    }

    /**
     * Returns the fitness of the given solution.
     *
     * @param solution The given solution.
     * @return The calculated fitness value.
     */
    private double fitness(DoubleArraySolution solution) {
        return 1.0/solution.value;
    }
}
