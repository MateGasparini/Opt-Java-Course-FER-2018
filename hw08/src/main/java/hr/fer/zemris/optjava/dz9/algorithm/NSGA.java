package hr.fer.zemris.optjava.dz9.algorithm;

import hr.fer.zemris.optjava.dz9.crossover.BlxAlphaCrossover;
import hr.fer.zemris.optjava.dz9.crossover.Crossover;
import hr.fer.zemris.optjava.dz9.mutation.Mutation;
import hr.fer.zemris.optjava.dz9.mutation.NormalRandomMutation;
import hr.fer.zemris.optjava.dz9.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz9.selection.RouletteWheelSelection;
import hr.fer.zemris.optjava.dz9.selection.Selection;
import hr.fer.zemris.optjava.dz9.solution.DoubleArraySolution;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>Represents the <i>Non-Dominated Sorting Genetic Algorithm</i>.</p>
 * <p>After running for specified number of iterations, it divides the
 * population into fronts and writes to stdout each of their cardinality.</p>
 * <p>Additionally, it writes all decision values to {@code DECISION_OUT} and
 * all objective values to {@code OBJECTIVE_OUT}.</p>
 *
 * @author Mate Gašparini
 */
public abstract class NSGA implements OptAlgorithm {

    /** Path to the decision values output file. */
    private static final Path DECISION_OUT = Paths.get("izlaz-dec.txt");

    /** Path to the objective values output file. */
    private static final Path OBJECTIVE_OUT = Paths.get("izlaz-obj.txt");

    /**
     * Factor by which the previous front minimum fitness is multiplied to get
     * the current front's initial fitness.
     */
    private static final double FRONT_FACTOR = 0.9;

    /** Exponent used for calculating the sharing function. */
    private static final double ALPHA = 2.0;

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** The specified problem. */
    private MOOPProblem problem;

    /** The specified population size. */
    private int populationSize;

    /** The specified number of iterations. */
    private int maxIteration;

    /** The specified value used for limiting the sharing function. */
    private double sigmaShare;

    /** The specified selection for the genetic algorithm. */
    private Selection<DoubleArraySolution> selection = new RouletteWheelSelection();

    /** The specified crossover for the genetic algorithm. */
    private Crossover<DoubleArraySolution> crossover;

    /** The specified mutation for the genetic algorithm. */
    private Mutation<DoubleArraySolution> mutation;

    /** The current solutions array. */
    protected DoubleArraySolution[] population;

    /** The next solutions array (used for avoiding the need for frequent GC). */
    private DoubleArraySolution[] nextPopulation;

    /** Holds objective values arrays for each solution from the population. */
    protected double[][] objectives;

    /**
     * Holds a {@code List} of dominated solution indexes for each solution
     * (used for avoiding additional GC of nested {@code List} instances).
     */
    private List<List<Integer>> dominations;

    /**
     * Constructor specifying various algorithm parameters.
     *
     * @param problem The specified optimization problem.
     * @param populationSize The specified population size.
     * @param maxIteration The specified number of iterations.
     * @param sigmaShare The specified value used for limiting the sharing function.
     * @param alpha The specified {@link BlxAlphaCrossover} parameter.
     * @param sigma The specified {@link NormalRandomMutation} parameter.
     */
    public NSGA(MOOPProblem problem, int populationSize, int maxIteration,
            double sigmaShare, double alpha, double sigma) {
        this.problem = problem;
        this.populationSize = populationSize;
        this.maxIteration = maxIteration;
        this.sigmaShare = sigmaShare;

        this.crossover = new BlxAlphaCrossover(alpha);
        this.mutation = new NormalRandomMutation(sigma, problem);
        this.population = new DoubleArraySolution[populationSize];
        this.nextPopulation = new DoubleArraySolution[populationSize];
        this.objectives = new double[populationSize][problem.getNumberOfObjectives()];

        this.dominations = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i ++) dominations.add(new ArrayList<>());
    }

    @Override
    public void run() {
        initPopulation();
        evaluatePopulation();
        for (int generation = 1; generation <= maxIteration; generation ++) {
            System.out.format("\rGeneration: %d/%d", generation, maxIteration);
            for (int i = 0; i < populationSize; i ++) {
                DoubleArraySolution first = selection.select(population);
                DoubleArraySolution second = selection.select(population);
                DoubleArraySolution child = crossover.cross(first, second);
                mutation.mutate(child);
                nextPopulation[i] = child;
            }

            DoubleArraySolution[] populationCopy = population;
            population = nextPopulation;
            nextPopulation = populationCopy;

            evaluatePopulation();
        }
        System.out.println("\nDone.\n");
        outputData();
    }

    /**
     * <p>Returns the distance value between the given solutions.</p>
     * <p>Can be calculated in decision or objective space.</p>
     *
     * @param start The first given solution.
     * @param end The second given solution.
     * @return The calculated distance.
     */
    protected abstract double distance(DoubleArraySolution start, DoubleArraySolution end);

    /**
     * Writes the front sizes to stdout and decision/objective values to the
     * corresponding files.
     */
    private void outputData() {
        List<List<DoubleArraySolution>> fronts = nonDominatedSort();
        System.out.println("Number of fronts: " + fronts.size());
        for (int i = 0; i < fronts.size(); i ++) {
            List<DoubleArraySolution> front = fronts.get(i);
            System.out.println("Front " + i + " size: " + front.size());
        }

        try (BufferedWriter decisionsWriter = Files.newBufferedWriter(DECISION_OUT);
             BufferedWriter objectiveWriter = Files.newBufferedWriter(OBJECTIVE_OUT)) {
            for (DoubleArraySolution solution : population) {
                writeVector(decisionsWriter, solution.getValues());
                writeVector(objectiveWriter, solution.getObjectives());
            }
        } catch (IOException ex) {
            System.err.println("A problem occurred while writing to "
                    + DECISION_OUT + " and " + OBJECTIVE_OUT + ".");
        }
    }

    /**
     * <p>Writes the given array of values using the given writer to some
     * destination stream.</p>
     * <p>The array is written to a single line, starting and ending with braces,
     * and with values separated by a single comma.</p>
     *
     * @param writer The given writer.
     * @param vector The given array of values.
     * @throws IOException If an IO error occurs.
     */
    private void writeVector(BufferedWriter writer, double[] vector) throws IOException {
        writer.write('(');
        for (int i = 0; i < vector.length; i ++) {
            writer.write(String.valueOf(vector[i]));
            if (i != vector.length-1) writer.write(',');
        }
        writer.write(')');
        writer.write('\n');
    }

    /**
     * Initializes the current population with randomly generated solutions.
     */
    private void initPopulation() {
        for (int i = 0; i < populationSize; i ++) {
            population[i] = randomSolution();
        }
    }

    /**
     * Evaluates the current population, divides it into fronts accordingly and
     * sets the appropriate solution fitness values.
     */
    private void evaluatePopulation() {
        for (int i = 0; i < populationSize; i ++) {
            DoubleArraySolution solution = population[i];
            problem.evaluateSolution(solution.getValues(), objectives[i]);
            solution.setObjectives(objectives[i]);
        }

        List<List<DoubleArraySolution>> fronts = nonDominatedSort();
        double minFitness = populationSize / FRONT_FACTOR;
        for (List<DoubleArraySolution> front : fronts) {
            double currentMinFitness = Double.MAX_VALUE;
            for (DoubleArraySolution solution : front) {
                double fitness = minFitness * FRONT_FACTOR;
                double nc = nicheDensity(solution, front);
                fitness /= nc;
                solution.setFitness(fitness);
                if (fitness < currentMinFitness) currentMinFitness = fitness;
            }
            minFitness = currentMinFitness;
        }
    }

    /**
     * Sorts the current population into fronts according to the number of
     * solution dominations and returns it.
     *
     * @return The sorted fronts of solutions.
     */
    private List<List<DoubleArraySolution>> nonDominatedSort() {
        List<List<Integer>> fronts = new ArrayList<>();
        List<Integer> firstFront = new ArrayList<>();

        int[] counters = new int[populationSize];
        for (int i = 0; i < populationSize; i ++) {
            counters[i] = 0;
            List<Integer> dominating = dominations.get(i);
            dominating.clear();

            for (int j = 0; j < populationSize; j ++) {
                if (i == j) continue;
                if (dominates(i, j)) {
                    dominating.add(j);
                } else if (dominates(j, i)) {
                    counters[i] ++;
                }
            }

            if (counters[i] == 0) {
                firstFront.add(i);
            }
        }

        List<Integer> currentFront = firstFront;
        while (!currentFront.isEmpty()) {
            fronts.add(currentFront);
            List<Integer> nextFront = new ArrayList<>();
            for (int i : currentFront) {
                List<Integer> dominating = dominations.get(i);
                for (int j : dominating) {
                    counters[j] --;
                    if (counters[j] == 0) {
                        nextFront.add(j);
                    }
                }
            }
            currentFront = nextFront;
        }

        return fronts.stream()
                .map(indexes -> indexes.stream()
                        .map(index -> population[index]).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * Relation of domination.
     *
     * @param first The index of the first solution.
     * @param second The index of the second solution.
     * @return {@code true} if the first solution dominates the second solution,
     *         or {@code false} otherwise.
     */
    private boolean dominates(int first, int second) {
        double[] firstObjectives = objectives[first];
        double[] secondObjectives = objectives[second];

        boolean someBetter = false;
        for (int i = 0; i < firstObjectives.length; i ++) {
            double firstObjective = firstObjectives[i];
            double secondObjective = secondObjectives[i];
            if (firstObjective < secondObjective) someBetter = true;
            if (firstObjective > secondObjective) return false;
        }

        return someBetter;
    }

    /**
     * Calculates the niche density for the given solution from the given front.
     *
     * @param solution The given solution.
     * @param front The given front of solutions.
     * @return The calculated niche density.
     */
    private double nicheDensity(DoubleArraySolution solution,
            List<DoubleArraySolution> front) {
        double nc = 0.0;
        for (DoubleArraySolution other : front) {
            nc += sharingFunction(distance(solution, other));
        }
        return nc;
    }

    /**
     * Calculates the sharing function value of the given distance value.
     *
     * @param distance The given distance value.
     * @return The calculated sharing function value.
     */
    private double sharingFunction(double distance) {
        return distance < sigmaShare
                ? 1 - Math.pow(distance/sigmaShare, ALPHA)
                : 0.0;
    }

    /**
     * Returns a randomly generated solution with decision values from the
     * problem-specific range.
     *
     * @return The randomly generated solution.
     */
    private DoubleArraySolution randomSolution() {
        int size = problem.getNumberOfObjectives();
        double[] min = problem.xMin();
        double[] max = problem.xMax();
        double[] values = new double[size];
        for (int i = 0; i < size; i ++) {
            values[i] = randomInRange(min[i], max[i]);
        }
        return new DoubleArraySolution(values);
    }

    /**
     * Returns a pseudo-randomly generated value from the specified interval.
     *
     * @param min The specified lower bound (inclusive).
     * @param max The specified upper bound (exclusive).
     * @return Some random number from the specified interval.
     */
    private double randomInRange(double min, double max) {
        return min + (max - min)*random.nextDouble();
    }
}
