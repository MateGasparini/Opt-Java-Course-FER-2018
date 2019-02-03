package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.EvaluatorProvider;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IntArrayGASolution;
import hr.fer.zemris.generic.ga.TemplateProvider;
import hr.fer.zemris.optjava.dz11.ga.concurrent.ConcurrentGeneticAlgorithm;
import hr.fer.zemris.optjava.dz11.ga.concurrent.Job;
import hr.fer.zemris.optjava.dz11.ga.crossover.DiscreteCrossover;
import hr.fer.zemris.optjava.dz11.ga.mutation.SimpleMutation;
import hr.fer.zemris.optjava.dz11.ga.selection.TournamentSelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Contains all shared genetic algorithm logic and implementation details.
 *
 * @author Mate Gašparini
 */
public abstract class GeneticAlgorithm extends ConcurrentGeneticAlgorithm<int[]> {

    /**
     * Constructor specifying the algorithm parameters.
     *
     * @param templatePath The path to the specified template image.
     * @param rectangleCount The specified number of rectangles in each solution.
     * @param populationSize The specified number of solutions in the population.
     * @param maxIteration The specified maximum number of iterations.
     * @param maxFitness The specified maximum fitness value.
     * @throws IOException If an IO error occurs while loading the template image.
     */
    public GeneticAlgorithm(Path templatePath, int rectangleCount,
            int populationSize, int maxIteration, double maxFitness) throws IOException {
        super(templatePath, rectangleCount, populationSize, maxIteration, maxFitness);

        selection = new TournamentSelection(50);
        crossover = new DiscreteCrossover();
        mutation = new SimpleMutation(0.01);
    }

    /**
     * Initializes and evaluates the random starting population.
     */
    protected void initPopulation() {
        for (int i = 0; i < populationSize; i ++) {
            GASolution<int[]> solution = randomSolution();
            population.add(solution);
            jobs.offer(new Job(() -> evaluate(solution)));
        }
        waitChildren();
    }

    /**
     * Creates a child solution using the specified algorithm operators.
     *
     * @return The created child.
     */
    protected GASolution<int[]> createChild() {
        GASolution<int[]> first = selection.select(population);
        GASolution<int[]> second = selection.select(population);
        GASolution<int[]> child = crossover.cross(first, second);
        mutation.mutate(child);
        return child;
    }

    /**
     * Evaluates the given solution using the thread-safe evaluator.
     *
     * @param solution The given solution.
     */
    protected void evaluate(GASolution<int[]> solution) {
        EvaluatorProvider.getEvaluator().evaluate(solution);
        children.offer(solution);
    }

    /**
     * Waits until all worker threads have returned the children for the next
     * population.
     */
    protected void waitChildren() {
        try {
            while (nextPopulation.size() < populationSize) {
                nextPopulation.add(children.take());
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Constructs a random solution (with values from reasonable intervals).
     *
     * @return The constructed solution.
     */
    private GASolution<int[]> randomSolution() {
        IRNG rng = RNG.getRNG();
        int[] data = new int[1 + rectangleCount*5];
        data[0] = rng.nextInt(0, 256);

        int width = TemplateProvider.getTemplateWidth();
        int height = TemplateProvider.getTemplateHeight();

        for (int i = 1; i < data.length; i ++) {
            data[i] = rng.nextInt(0, width);
            data[++i] = rng.nextInt(0, height);
            data[++i] = rng.nextInt(0, width);
            data[++i] = rng.nextInt(0, height);
            data[++i] = rng.nextInt(0, 256);
        }
        return new IntArrayGASolution(data);
    }
}
