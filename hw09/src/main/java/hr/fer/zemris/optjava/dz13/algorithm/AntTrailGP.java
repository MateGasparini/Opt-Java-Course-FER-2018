package hr.fer.zemris.optjava.dz13.algorithm;

import hr.fer.zemris.optjava.dz13.algorithm.crossover.Crossover;
import hr.fer.zemris.optjava.dz13.algorithm.crossover.SubTreeCrossover;
import hr.fer.zemris.optjava.dz13.algorithm.initialization.Initialization;
import hr.fer.zemris.optjava.dz13.algorithm.initialization.RampedHalfAndHalf;
import hr.fer.zemris.optjava.dz13.algorithm.mutation.Mutation;
import hr.fer.zemris.optjava.dz13.algorithm.mutation.Reproduction;
import hr.fer.zemris.optjava.dz13.algorithm.mutation.SubTreeMutation;
import hr.fer.zemris.optjava.dz13.algorithm.selection.Selection;
import hr.fer.zemris.optjava.dz13.algorithm.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz13.algorithm.solution.AntEvaluator;
import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;
import hr.fer.zemris.optjava.dz13.algorithm.solution.food.FoodMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Genetic programming (genetic algorithm applied to trees) algorithm which
 * tries to solve the Santa Fe ant trail problem.
 *
 * @author Mate Gašparini
 */
public class AntTrailGP {

    private static final int MAX_DEPTH = 7;

    private static final int MAX_NODES = 200;

    private static final int N = 7;

    private static final double CROSSOVER_P = 0.85;

    private static final double MUTATION_P = 0.14;

    private Random random = new Random();

    private int populationSize;

    private int maxGeneration;

    private int stopScore;

    private AntEvaluator evaluator;

    private Initialization<TreeSolution> initialization;

    private Selection<TreeSolution> selection;

    private Crossover<TreeSolution> crossover;

    private Mutation<TreeSolution> mutation;

    private Mutation<TreeSolution> reproduction;

    private List<TreeSolution> population;

    private TreeSolution allTimeBest;

    public AntTrailGP(int populationSize, int maxGeneration, int stopScore,
            FoodMap foodMap) {
        this.populationSize = populationSize;
        this.maxGeneration = maxGeneration;
        this.stopScore = stopScore;
        evaluator = new AntEvaluator(foodMap);
        initialization = new RampedHalfAndHalf(MAX_DEPTH, MAX_NODES);
        selection = new TournamentSelection(N);
        crossover = new SubTreeCrossover(MAX_DEPTH, MAX_NODES);
        mutation = new SubTreeMutation(MAX_DEPTH, MAX_NODES);
        reproduction = new Reproduction();
    }

    /**
     * <p>Runs the algorithm.</p>
     * <p>Each time a new best solution is found, it is printed to stdout.</p>
     *
     * @return The best found solution.
     */
    public TreeSolution run() {
        population = initialization.getPopulation(populationSize);

        for (int generation = 0; generation < maxGeneration; generation ++) {
            evaluator.evaluatePopulation(population);
            Collections.sort(population);

            TreeSolution populationBest = population.get(population.size()-1);
            if (populationBest.getScore() >= stopScore) {
                return populationBest;
            }
            if (allTimeBest == null || populationBest.getScore() > allTimeBest.getScore()) {
                allTimeBest = populationBest;
                System.out.println("Found new best in generation " + generation + ":");
                System.out.println(allTimeBest.getRoot());
                System.out.println("Score: " + allTimeBest.getScore());
                System.out.println();
            }
            List<TreeSolution> nextPopulation = new ArrayList<>(populationSize);
            nextPopulation.add(populationBest); // Elitism.

            while (nextPopulation.size() < populationSize) {
                double randomValue = random.nextDouble();
                if (randomValue < CROSSOVER_P) {
                    TreeSolution first = selection.select(population);
                    TreeSolution second = selection.select(population);
                    TreeSolution child = crossover.cross(first, second);
                    nextPopulation.add(child);
                } else if (randomValue < CROSSOVER_P + MUTATION_P) {
                    nextPopulation.add(mutation.reproduce(randomFromPopulation()));
                } else {
                    nextPopulation.add(reproduction.reproduce(randomFromPopulation()));
                }
            }

            population = nextPopulation;
        }

        return allTimeBest;
    }

    private TreeSolution randomFromPopulation() {
        return population.get(random.nextInt(populationSize));
    }
}
