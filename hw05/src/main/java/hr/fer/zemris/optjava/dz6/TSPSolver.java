package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>Command line program which tries to solve some specified traveling
 * salesman problem (TSP) using an improved version of the ant colony algorithm
 * ({@link MMASAlgorithm}).</p>
 * <p>It accepts 4 arguments:</p>
 * <ul>
 * <li>The first argument is the path to the file specifying the TSP.</li>
 * <li>The second argument is the size of the candidate list for each node.</li>
 * <li>The third argument is the maximum generation (number of iterations).</li>
 * </ul>
 *
 * @author Mate Gašparini
 */
public class TSPSolver {

    /** Minimum value of all integer command line arguments. */
    private static final int MIN_VALUE = 1;

    /** Used for exponentiation of pheromone values. */
    private static final double ALPHA = 1;

    /** Used for exponentiation in heuristic information. */
    private static final double BETA = 2;

    /** Used to calculate the parameter <i>a</i> (used for minimum pheromone). */
    private static final double P = 0.9;

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (problem file path, candidate count,
     *        ant count and max generation).
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Expected 4 arguments.");
            return;
        }

        Path problemPath = Paths.get(args[0]);
        if (!Files.isReadable(problemPath)) {
            System.err.println("Invalid file path: '" + args[0] + "'.");
            return;
        }

        int candidateCount;
        int antCount;
        int maxGeneration;
        try {
            candidateCount = checkInt(args[1], "candidate count");
            antCount = checkInt(args[2], "ant count");
            maxGeneration = checkInt(args[3], "max generation");
        } catch (NumberFormatException ex) {
            System.err.println(ex.getMessage());
            return;
        }

        double[][] distances;
        try {
            distances = new TSPParser(problemPath).getDistances();
        } catch (IOException e) {
            System.err.println("There was a problem reading " + args[0] + ".");
            return;
        }

        if (candidateCount > distances.length) {
            System.err.println("Candidate length must be less or equal to "
                    + distances.length + ".");
            return;
        }

        new MMASAlgorithm(
                distances, ALPHA, BETA, calculateA(distances.length),
                candidateCount, antCount, maxGeneration
        ).run();
    }

    /**
     * Tries to parse the given argument as an {@code int} and returns it.
     *
     * @param argument The given argument.
     * @param name The given argument name (used for exception messages).
     * @return Parsed value from the given argument.
     * @throws NumberFormatException If the given argument is not parsable.
     */
    private static int checkInt(String argument, String name) {
        int value;
        try {
            value = Integer.parseInt(argument);
            if (value < MIN_VALUE) {
                throw new NumberFormatException("Expected at least " + MIN_VALUE
                        + " for argument " + name + ", but was " + value + ".");
            }
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("'" + argument
                    + "' is not a valid " + name + ".");
        }
        return value;
    }

    /**
     * Calculates the constant <i>a</i>, which represents the ratio between the
     * maximum and the minimum pheromone value.
     *
     * @param n The number of nodes.
     * @return The value of the constant <i>a</i>.
     */
    private static double calculateA(double n) {
        double mu = (n - 1) / (n * (-1 + Math.pow(P, -1.0 / n)));
        return mu * n;
    }
}
