package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.ga.concurrent.Job;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Elitist genetic algorithm which parallelizes children evaluation.
 *
 * @author Mate Gašparini
 */
public class GeneticAlgorithm1 extends GeneticAlgorithm {

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
    public GeneticAlgorithm1(Path templatePath, int rectangleCount,
            int populationSize, int maxIteration, double maxFitness) throws IOException {
        super(templatePath, rectangleCount, populationSize, maxIteration, maxFitness);
    }

    @Override
    public GASolution<int[]> run() {
        initPopulation();
        for (int iteration = 1; iteration <= maxIteration; iteration ++) {
            Collections.sort(population);
            GASolution<int[]> best = population.get(0);
            System.out.print("\rGeneration: " + iteration
                    + " Best: " + best.fitness);
            if (best.fitness >= maxFitness) break;
            nextPopulation.clear();
            nextPopulation.add(best); // Elitism.

            for (int i = 1; i < populationSize; i ++) {
                GASolution<int[]> child = createChild();
                jobs.offer(new Job(() -> evaluate(child)));
            }
            waitChildren();

            List<GASolution<int[]>> populationCopy = population;
            population = nextPopulation;
            nextPopulation = populationCopy;
        }

        poisonWorkers();
        Collections.sort(population);
        return population.get(0);
    }
}
