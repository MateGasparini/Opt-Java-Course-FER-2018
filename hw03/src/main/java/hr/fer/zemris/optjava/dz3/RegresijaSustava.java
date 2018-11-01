package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.dz3.decoder.GrayBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoder.NaturalBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoder.PassThroughDecoder;
import hr.fer.zemris.optjava.dz3.function.ErrorFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.BitVectorNeighborhood;
import hr.fer.zemris.optjava.dz3.neighborhood.DoubleArrayNormNeighborhood;
import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.tempschedule.GeometricTempSchedule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * <p>Command line program which expects two arguments and tries to solve the
 * specified problem (system of equations) by using some {@link IOptAlgorithm}
 * (specifically, the {@link SimulatedAnnealing} algorithm).</p>
 * <p>The first argument is the path to the file containing the definition of
 * the problem.</p>
 * <p>The second argument specifies the representation of the solution inside
 * the algorithm (decimal or binary).</p>
 *
 * @author Mate Gašparini
 */
public class RegresijaSustava {

    /** Double array solution method label. */
    private static final String DECIMAL = "decimal";

    /** Bit vector solution method label. */
    private static final String BINARY = "binary:";

    /** Minimum bits per variable. */
    private static final int MIN_BINARY = 5;

    /** Maximum bits per variable. */
    private static final int MAX_BINARY = 30;

    /**
     * Main method which is called when the program starts.
     *
     * @param args Command line arguments (file path and algorithm options).
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Expected 2 arguments.");
            return;
        }

        Path filePath = Paths.get(args[0]);
        if (!Files.isRegularFile(filePath)) {
            System.err.println("Invalid file path: '" + args[0] + "'.");
            return;
        }

        String method = args[1];
        if (method.equals(DECIMAL)) {
            DoubleArraySolution solution = doubleArraySolve(filePath);
            if (solution == null) {
                System.out.println("Solution not found.");
                return;
            }
            printSolution(solution.values, solution.value);
        } else if (method.startsWith(BINARY)) {
            String binaryOption = method.substring(BINARY.length());
            int bitsPerVariable;
            try {
                bitsPerVariable = Integer.parseInt(binaryOption);
            } catch (NumberFormatException ex) {
                System.err.println("Bits per variable value '" + binaryOption
                        + "' not parsable as an integer.");
                return;
            }

            if (bitsPerVariable < MIN_BINARY || bitsPerVariable > MAX_BINARY) {
                System.err.println("Bits per variable value must be from ["
                        + MIN_BINARY + ", " + MAX_BINARY + "].");
                return;
            }

            BitVectorSolution solution = bitVectorSolve(filePath, bitsPerVariable);
            if (solution == null) {
                System.out.println("Solution not found.");
                return;
            }

            double[] solutionArray = new NaturalBinaryDecoder(-5, 10, bitsPerVariable, 6)
                    .decode(solution);
            printSolution(solutionArray, solution.value);
        } else {
            System.err.println("Invalid solving method: '" + method + "'.");
            return;
        }
    }

    /**
     * Tries to solve the problem by using the {@link DoubleArraySolution}
     * representation.
     *
     * @param filePath The path to the problem definition.
     * @return The last calculated solution.
     */
    private static DoubleArraySolution doubleArraySolve(Path filePath) {
        ErrorFunction function = parseErrorFunction(filePath);
        if (function == null) return null;

        DoubleArraySolution start = new DoubleArraySolution(6);
        start.randomize(new Random(),
                new double[] {-5, -5, -5, -5, -5, -5},
                new double[] {5, 5, 5, 5, 5, 5}
        );

        IOptAlgorithm<DoubleArraySolution> algorithm = new SimulatedAnnealing<>(
                new PassThroughDecoder(),
                new DoubleArrayNormNeighborhood(
                        new double[] {0.2, 0.2, 0.2, 0.2, 0.2, 0.2}
                ),
                start,
                function,
                new GeometricTempSchedule(4_000, 0, 1_000, 2_000),
                true
        );
        return algorithm.run();
    }

    /**
     * Tries to solve the problem by using the {@link BitVectorSolution}
     * representation.
     *
     * @param filePath The path to the problem definition.
     * @param bitsPerVariable The specified number of bits used to represent
     *                        each variable.
     * @return The last calculated solution.
     */
    private static BitVectorSolution bitVectorSolve(Path filePath, int bitsPerVariable) {
        ErrorFunction function = parseErrorFunction(filePath);
        if (function == null) return null;

        BitVectorSolution start = new BitVectorSolution(6*bitsPerVariable);
        start.randomize(new Random());

        IOptAlgorithm<BitVectorSolution> algorithm = new SimulatedAnnealing<>(
                new GrayBinaryDecoder(-5, 10, bitsPerVariable, 6),
                new BitVectorNeighborhood(),
                start,
                function,
                new GeometricTempSchedule(4_000, 0, 1_000, 2_000),
                true
        );
        return algorithm.run();
    }

    /**
     * <p>Tries to parse the {@link ErrorFunction} from the specified file path.</p>
     * <p>If an IO error occurs, an error message is written to stderr and
     * {@code null} is returned.</p>
     *
     * @param filePath The specified file path.
     * @return The parsed {@link ErrorFunction} (or {@code null}).
     */
    private static ErrorFunction parseErrorFunction(Path filePath) {
        try {
            return new ErrorFunctionParser().parse(filePath);
        } catch (IOException ex) {
            System.err.println("IO error occurred while trying to read from '"
                    + filePath + "'.");
            return null;
        }
    }

    /**
     * Writes to stdout the given solution values, as well as the given error.
     *
     * @param solution The given solution values.
     * @param error The given error.
     */
    private static void printSolution(double[] solution, double error) {
        System.out.println("====================");
        for (int i = 0; i < solution.length; i ++) {
            System.out.println((char) ('a'+i) + ": " + solution[i]);
        }
        System.out.println("====================");
        System.out.println("Error: " + error);
    }
}
