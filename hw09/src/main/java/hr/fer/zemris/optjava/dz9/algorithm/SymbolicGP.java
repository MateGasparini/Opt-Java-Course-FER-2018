package hr.fer.zemris.optjava.dz9.algorithm;

import hr.fer.zemris.optjava.dz9.algorithm.crossover.Crossover;
import hr.fer.zemris.optjava.dz9.algorithm.dataset.Dataset;
import hr.fer.zemris.optjava.dz9.algorithm.initialization.Initialization;
import hr.fer.zemris.optjava.dz9.algorithm.mutation.Mutation;
import hr.fer.zemris.optjava.dz9.algorithm.selection.Selection;
import hr.fer.zemris.optjava.dz9.algorithm.solution.SymbolicEvaluator;
import hr.fer.zemris.optjava.dz9.algorithm.solution.SymbolicTreeSolution;

import java.util.*;

/**
 * Genetic programming (genetic algorithm applied to trees) algorithm which
 * tries to solve symbolic regression problems.
 *
 * @author Mate Gašparini
 */
public class SymbolicGP {

    private Random random = new Random();

    private int populationSize;

    private int maxGeneration;

    private SymbolicEvaluator evaluator;

    private Initialization<SymbolicTreeSolution> initialization;

    private Selection<SymbolicTreeSolution> selection;

    private Crossover<SymbolicTreeSolution> crossover;

    private Mutation<SymbolicTreeSolution> mutation;

    private Mutation<SymbolicTreeSolution> reproduction;

    private double crossoverProbability;

    private double mutationProbability;

    private List<SymbolicTreeSolution> population;

    private SymbolicTreeSolution allTimeBest;

    public SymbolicGP(int populationSize, int maxGeneration, Dataset dataset,
            Initialization<SymbolicTreeSolution> initialization,
            Selection<SymbolicTreeSolution> selection,
            Crossover<SymbolicTreeSolution> crossover,
            Mutation<SymbolicTreeSolution> mutation,
            Mutation<SymbolicTreeSolution> reproduction,
            double mutationProbability) {
        this.populationSize = populationSize;
        this.maxGeneration = maxGeneration;
        this.evaluator = new SymbolicEvaluator(dataset);
        this.initialization = initialization;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.reproduction = reproduction;
        this.mutationProbability = mutationProbability;
        this.crossoverProbability = 0.99 - mutationProbability;
    }

    /**
     * <p>Runs the algorithm.</p>
     * <p>Each time a new best solution is found, it is printed to stdout.</p>
     */
    public void run() {
        population = initialization.getPopulation(populationSize);

        for (int generation = 0; generation < maxGeneration; generation ++) {
            evaluator.evaluatePopulation(population);
            Collections.sort(population);

            List<SymbolicTreeSolution> nextPopulation = new ArrayList<>(populationSize);
            SymbolicTreeSolution populationBest = population.get(population.size()-1);
            nextPopulation.add(populationBest); // Elitism.
            if (allTimeBest == null || allTimeBest.getValue() > populationBest.getValue()) {
                allTimeBest = populationBest;
                System.out.println("Found new best in generation " + generation + ":");
                System.out.println(allTimeBest.getRoot());
                System.out.println("Error: " + allTimeBest.getValue());
                System.out.println();
            }

            while (nextPopulation.size() < populationSize) {
                double randomValue = random.nextDouble();
                if (randomValue < crossoverProbability) {
                    SymbolicTreeSolution first = selection.select(population);
                    SymbolicTreeSolution second = selection.select(population);
                    SymbolicTreeSolution child = crossover.cross(first, second);
                    nextPopulation.add(child);
                } else if (randomValue < crossoverProbability + mutationProbability) {
                    nextPopulation.add(mutation.reproduce(randomFromPopulation()));
                } else {
                    nextPopulation.add(reproduction.reproduce(randomFromPopulation()));
                }
            }

            population = nextPopulation;
        }
    }

    private SymbolicTreeSolution randomFromPopulation() {
        return population.get(random.nextInt(populationSize));
    }
}
