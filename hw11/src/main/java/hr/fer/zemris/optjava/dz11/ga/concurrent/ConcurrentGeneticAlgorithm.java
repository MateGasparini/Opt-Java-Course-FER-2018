package hr.fer.zemris.optjava.dz11.ga.concurrent;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.TemplateProvider;
import hr.fer.zemris.optjava.dz11.ga.crossover.Crossover;
import hr.fer.zemris.optjava.dz11.ga.mutation.Mutation;
import hr.fer.zemris.optjava.dz11.ga.selection.Selection;
import hr.fer.zemris.optjava.rng.EVOThread;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Genetic algorithm which offers the possibility of different degrees of
 * parallelism.
 *
 * @param <T> Type of the solution representation.
 * @author Mate Gašparini
 */
public abstract class ConcurrentGeneticAlgorithm<T> {

    /** Array of worker threads. */
    private EVOThread[] workers;

    /** Queue of worker thread jobs. */
    protected BlockingQueue<Job> jobs = new LinkedBlockingQueue<>();

    /** Queue of worker thread produced children. */
    protected BlockingQueue<GASolution<T>> children = new LinkedBlockingQueue<>();

    /** The current population of solutions. */
    protected List<GASolution<T>> population;

    /** The next population of solutions. */
    protected List<GASolution<T>> nextPopulation;

    /** The number of rectangles in a single solution. */
    protected int rectangleCount;

    /** The number of solutions in the population. */
    protected int populationSize;

    /** The maximum number of iterations. */
    protected int maxIteration;

    /** The maximum fitness value. */
    protected double maxFitness;

    /** The {@link Selection} operator. */
    protected Selection<GASolution<T>> selection;

    /** The {@link Crossover} operator. */
    protected Crossover<GASolution<T>> crossover;

    /** The {@link Mutation} operator. */
    protected Mutation<GASolution<T>> mutation;

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
    public ConcurrentGeneticAlgorithm(Path templatePath, int rectangleCount,
            int populationSize, int maxIteration, double maxFitness) throws IOException {
        this.rectangleCount = rectangleCount;
        this.populationSize = populationSize;
        this.maxIteration = maxIteration;
        this.maxFitness = maxFitness;

        this.population = new ArrayList<>(populationSize);
        this.nextPopulation = new ArrayList<>(populationSize);

        TemplateProvider.setTemplate(GrayScaleImage.load(templatePath.toFile()));
        initWorkers();
    }

    /**
     * Runs the algorithm until maximum number of iterations or maximum fitness
     * is reached.
     *
     * @return The best found solution.
     */
    public abstract GASolution<T> run();

    /**
     * Shuts down all worker threads.
     */
    protected void poisonWorkers() {
        for (int i = 0; i < workers.length; i ++) {
            jobs.offer(Job.PILL);
        }
    }

    /**
     * Creates {@link Runtime#availableProcessors()} of worker threads.
     */
    private void initWorkers() {
        int processors = Runtime.getRuntime().availableProcessors();
        workers = new EVOThread[processors];
        for (int i = 0; i < processors; i ++) {
            EVOThread worker = new Worker();
            workers[i] = worker;
            worker.start();
        }
    }

    /**
     * <p>Models a worker thread.</p>
     * <p>It waits for queued tasks and runs them (or dies of poisoning).</p>
     */
    private class Worker extends EVOThread {

        {
            setDaemon(true); // Shutdown when program is interrupted.
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Job job = jobs.take();
                    if (job == Job.PILL) break;
                    job.runTask();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }
}
