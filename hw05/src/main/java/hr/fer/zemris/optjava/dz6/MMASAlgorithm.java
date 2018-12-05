package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.pow;
import static java.lang.Math.random;

/**
 * <p>Class which represents the <i>Max-Min Ant System Algorithm</i>, an
 * improved version of the <i>Ant System Algorithm</i>.</p>
 * <p>Ant colony algorithms are nature inspired, as the name suggests. These
 * algorithms simulate the pheromone-driven movement of ants and are often used
 * to find the shortest cycle in a node graph.</p>
 *
 * @author Mate Gašparini
 */
public class MMASAlgorithm {

    /** Evaporation rate. */
    private static final double RHO = 0.02;

    /** Constant used for stagnation detection. */
    private static final int STAGNATION_MAX = 500;

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** The candidate and non-candidate lists. */
    private Nodes nodes;

    /** The pheromone values on the vertices. */
    private Pheromones pheromones;

    /** The heuristic information matrix. */
    private HeuristicInformation heuristic;

    /** The distance matrix. */
    private double[][] distances;

    /** Used for exponentiation of pheromone values. */
    private double alpha;

    /** Number of ants in a single generation (iteration). */
    private int antCount;

    /** Number of generations (iterations). */
    private int maxGeneration;

    /**
     * Constructor specifying all needed algorithm parameters.
     *
     * @param distances The specified distance matrix.
     * @param alpha The specified <i>alpha</i> value (used for pheromones).
     * @param beta The specified <i>beta</i> value (used for heuristic info).
     * @param a The specified <i>a</i> value (pheromone bounds ratio).
     * @param candidateCount The specified candidate list size.
     * @param antCount The specified number of ants in a single generation.
     * @param maxGeneration The specified number of generations.
     */
    public MMASAlgorithm(double[][] distances, double alpha, double beta, double a,
                         int candidateCount, int antCount, int maxGeneration) {
        this.distances = distances;
        this.alpha = alpha;
        this.antCount = antCount;
        this.maxGeneration = maxGeneration;

        this.nodes = new Nodes(distances, candidateCount);
        this.pheromones = new Pheromones(distances.length, RHO, a, calculateTau0());
        this.heuristic = new HeuristicInformation(distances, beta);
    }

    /**
     * Runs the algorithm and, after {@code maxGeneration} iterations, prints
     * out the best (shortest) found graph cycle.
     */
    public void run() {
        double iterationBestChance = 1.0;
        double iterationBestChanceDelta = 1.0 / maxGeneration;

        double bestSoFar = Double.MAX_VALUE;
        int[] bestSoFarPath = null;

        int stagnationCounter = 0;
        for (int generation = 0; generation < maxGeneration; generation ++) {
            double iterationBest = Double.MAX_VALUE;
            int[] iterationBestPath = null;
            for (int ant = 0; ant < antCount; ant ++) {
                List<Integer> path = simulateAntPath();
                double length = calculatePathLength(path);

                if (length < iterationBest) {
                    iterationBest = length;
                    iterationBestPath = listToArray(path);
                }
            }

            System.out.println("Iteration: " + generation + ", best: " + iterationBest);

            if (iterationBest < bestSoFar) {
                bestSoFar = iterationBest;
                bestSoFarPath = iterationBestPath;
                pheromones.setTauMax(1.0 / (RHO * bestSoFar));
                stagnationCounter = 0;
            } else {
                stagnationCounter ++;
                if (stagnationCounter == STAGNATION_MAX) {
                    pheromones.resetValues();
                    stagnationCounter = 0;
                }
            }

            pheromones.evaporate();

            if (random() < iterationBestChance) {
                pheromones.update(iterationBestPath, 1.0 / iterationBest);
            } else {
                pheromones.update(bestSoFarPath, 1.0 / bestSoFar);
            }
            iterationBestChance -= iterationBestChanceDelta;
        }

        System.out.println("Overall best: " + bestSoFar);
        printPath(bestSoFarPath);
    }

    /**
     * Calculates the starting maximum pheromone value (using the formula given
     * mentioned during the lecture).
     *
     * @return The calculated starting maximum pheromone value.
     */
    private double calculateTau0() {
        return antCount / nearestNeighborLength();
    }

    /**
     * Calculates the length of the path between all nearest neighbor nodes.
     *
     * @return The length of the nearest neighbor cycle.
     */
    private double nearestNeighborLength() {
        double length = 0.0;
        for (int row = 0; row < distances.length; row ++) {
            double rowMin = Double.MAX_VALUE;
            for (int col = 0; col < distances.length; col++) {
                if (row == col) continue;
                double value = distances[row][col];
                if (value < rowMin) rowMin = value;
            }
            length += rowMin;
        }
        return length;
    }

    /**
     * Simulates the pheromone-driven movement of a single ant and returns a
     * {@code List} of all visited nodes.
     *
     * @return The {@code List} containing the nodes ordered as they have been
     * visited by the ant.
     */
    private List<Integer> simulateAntPath() {
        List<Integer> path = new ArrayList<>(distances.length);
        path.add(random.nextInt(distances.length));

        for (int pathIndex = 0; pathIndex < distances.length - 1; pathIndex ++) {
            int currentNode = path.get(pathIndex);
            List<Integer> possibilities = Arrays.stream(
                    nodes.getCandidates(currentNode)
            ).boxed().filter(node -> !path.contains(node)).collect(Collectors.toList());

            if (possibilities.size() == 1) {
                path.add(possibilities.get(0));
                continue;
            } else if (possibilities.size() == 0) {
                possibilities = Arrays.stream(
                        nodes.getNonCandidates(currentNode)
                ).boxed().filter(node -> !path.contains(node)).collect(Collectors.toList());
            }

            double denominator = 0.0;
            for (int possibility : possibilities) {
                denominator += calculateComponent(currentNode, possibility);
            }

            double randomValue = random();
            double limitValue = 0.0;

            for (int possibility : possibilities) {
                limitValue += calculateComponent(currentNode, possibility) / denominator;
                if (randomValue < limitValue) {
                    path.add(possibility);
                    break;
                }
            }
        }

        return path;
    }

    /**
     * <p>Calculates the component of probability for the specified vertex.</p>
     * <p>Uses the pheromone value and the heuristic information for the
     * specified vertex.</p>
     *
     * @param from The starting node index.
     * @param to The target node index.
     * @return The calculated component.
     */
    private double calculateComponent(int from, int to) {
        return pow(pheromones.get(from, to), alpha) * heuristic.get(from, to);
    }

    /**
     * Calculates the length of the given path.
     *
     * @param path {@code List} containing the nodes of the path.
     * @return The calculated path length.
     */
    private double calculatePathLength(List<Integer> path) {
        double length = 0.0;
        for (int i = 0; i < path.size(); i ++) {
            int currentNode = path.get(i);
            int nextNode = path.get((i+1) % path.size());
            length += distances[currentNode][nextNode];
        }
        return length;
    }

    /**
     * Helper method which converts the given {@code List} of {@code Integer}s
     * to an {@code int} array.
     *
     * @param list The given {@code List}.
     * @return The corresponding {@code int} array.
     */
    private int[] listToArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * Helper method which comma-formats and prints the given path.
     *
     * @param path The nodes of the given path.
     */
    private void printPath(int[] path) {
        StringBuilder sb = new StringBuilder();
        String comma = ", ";

        int startingIndex = -1;
        for (int i = 0; i < path.length; i ++) {
            if (startingIndex == -1) {
                if (path[i] == 0) {
                    startingIndex = i;
                    sb.append(path[i] + 1).append(comma);
                }
            } else {
                sb.append(path[i] + 1).append(comma);
            }
        }

        for (int i = 0; i < startingIndex; i ++) {
            sb.append(path[i] + 1).append(comma);
        }

        System.out.println(sb.substring(0, sb.length() - comma.length()));
    }
}
