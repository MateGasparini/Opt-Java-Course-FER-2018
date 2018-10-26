package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.ThirdFunction;
import hr.fer.zemris.optjava.dz2.util.NumOptUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;

/**
 * Program which solves some equation system using gradient descent and Newton's
 * method.
 *
 * @author Mate Gašparini
 */
public class Sustav {

    /** Start of a comment. */
    private static final String COMMENT_START = "#";

    /** Start of the equation vector. */
    private static final String START = "[";

    /** End of the equation vector. */
    private static final String END = "]";

    /** Size of the equation system. */
    private static final int VECTOR_SIZE = 10;

    /** Map containing algorithms, indexed by the algorithm label. */
    private static final Map<String, TriFunction<IHFunction, Matrix, Integer, Matrix>>
            algorithms = Map.ofEntries(
                    Map.entry("grad", (f, p, m) -> NumOptAlgorithms.gradientDescent(f, m, p)),
                    Map.entry("newt", (f, p, m) -> NumOptAlgorithms.newtonsMethod(f, m, p))
    );

    /**
     * Main method which is called when the program starts.
     *
     * @param args Command line arguments (algorithm label, max iteration, path
     *        to the file containing equation system definition).
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Expected 3 arguments.");
            return;
        }

        var algorithm = algorithms.get(args[0]);
        if (algorithm == null) {
            System.err.println("No algorithm found for '" + args[0] + "'.");
            return;
        }

        int maxIteration;
        try {
            maxIteration = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            System.err.println("Maximum number of iterations not parsable.");
            return;
        }

        Path filePath = Paths.get(args[2]);
        if (!Files.isRegularFile(filePath)) {
            System.err.println("Invalid file path: " + args[2] + ".");
            return;
        }

        ThirdFunction function;
        try {
            function = parseFunction(filePath);
            if (function == null) return;
        } catch (IOException ex) {
            System.err.println("Error reading from: " + args[2] + ".");
            return;
        }

        Random random = new Random();
        double[][] startArray = new double[10][1];
        for (int i = 0; i < VECTOR_SIZE; i ++) {
            startArray[i][0] = random.nextDouble();
        }
        Matrix start = new Matrix(startArray);

        Matrix result = algorithm.apply(function, start, maxIteration);
        System.out.println("====================");
        if (result == null) {
            System.out.println("Solution not found.");
        } else {
            System.out.print("Solution found at: ");
            NumOptUtil.printPoint(result, System.out);
            System.out.println("Error: " + function.getValue(result));
        }
    }

    /**
     * Parses the file specified by the given file path and returns the
     * corresponding {@link ThirdFunction}.
     *
     * @param filePath The given file path.
     * @return The parsed {@link ThirdFunction}.
     * @throws IOException If an I/O error occurs.
     */
    private static ThirdFunction parseFunction(Path filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(filePath)))) {
            double[][] coefficients = new double[VECTOR_SIZE][VECTOR_SIZE];
            double[][] rightSide = new double[VECTOR_SIZE][1];
            int current = 0;
            while (current < VECTOR_SIZE) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) break;
                if (line.startsWith(COMMENT_START)) continue;
                if (!line.startsWith(START) || !line.endsWith(END)) {
                    System.err.println("Input file contains errors.");
                    return null;
                }

                line = line.substring(1, line.length() - 1);
                String[] parts = line.split("\\s*,\\s*");
                if (parts.length != VECTOR_SIZE+1) {
                    System.err.println("Input file must contain groups of "
                            + (VECTOR_SIZE+1) + " values.");
                    return null;
                }

                try {
                    for (int i = 0; i < parts.length - 1; i++) {
                        coefficients[current][i] = Double.parseDouble(parts[i]);
                    }
                    rightSide[current][0] = Double.parseDouble(parts[parts.length-1]);
                } catch (NumberFormatException ex) {
                    System.err.println("Input file contains invalid numbers.");
                    return null;
                }
                current ++;
            }

            Matrix coefficientMatrix = new Matrix(coefficients);
            Matrix rightSideMatrix = new Matrix(rightSide);
            return new ThirdFunction(coefficientMatrix, rightSideMatrix);
        }
    }
}
