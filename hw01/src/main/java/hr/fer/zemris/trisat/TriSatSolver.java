package hr.fer.zemris.trisat;

import hr.fer.zemris.trisat.algorithms.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Command line program which expects 2 arguments.</p>
 * <p>The first argument is the index of the chosen algorithm (as specified in
 * {@code ALGORITHMS}, but starting at index 1).</p>
 * <p>The second argument is the path to the file containing the {@link SATFormula}
 * definition.</p>
 * <p>The program tries to solve the defined problem using the chosen algorithm
 * and, if a solution was found, it is written to the standard output.</p>
 *
 * @author Mate Gašparini
 */
public class TriSatSolver {

    /** {@code List} of all available algorithms. */
    private static final List<Algorithm> ALGORITHMS = Arrays.asList(
            new BruteForceAlgorithm(System.out),
            new GreedyHillClimbingAlgorithm(),
            new SmartHillClimbingAlgorithm(),
            new GSATAlgorithm(),
            new RandomWalkSATAlgorithm(),
            new IteratedLocalSearchAlgorithm()
    );

    /**
     * Main method which is called when the program starts.
     *
     * @param args Command line arguments - algorithm index and file path.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Expected 2 arguments.");
            return;
        }

        int algorithmIndex;
        try {
            algorithmIndex = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            System.err.println(args[0] + " is not a valid integer.");
            return;
        }
        if (algorithmIndex < 1 || algorithmIndex > ALGORITHMS.size()) {
            System.err.println("Valid algorithm indexes are: [1, " + ALGORITHMS.size() + "]");
            return;
        }

        Path filePath = Paths.get(args[1]);
        if (!Files.isRegularFile(filePath)) {
            System.err.println(args[1] + " is not a valid file path.");
            return;
        }

        try {
            SATFormula formula = new TriSatParser(filePath).parse();
            Algorithm algorithm = ALGORITHMS.get(algorithmIndex - 1);

            BitVector solution = algorithm.solve(formula);
            if (solution == null) {
                System.out.println("Nije pronađeno rješenje.");
            } else {
                System.out.println("Zadovoljivo: " + solution);
            }
        } catch (IOException ex) {
            System.err.println("I/O problem occurred while trying to read the file.");
        } catch (NumberFormatException ex) {
            System.err.println("File contains integer parsing errors.");
        }
    }
}
