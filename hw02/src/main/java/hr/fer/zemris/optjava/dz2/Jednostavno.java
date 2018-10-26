package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.FirstFunction;
import hr.fer.zemris.optjava.dz2.functions.SecondFunction;
import hr.fer.zemris.optjava.dz2.util.NumOptUtil;

import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;

/**
 * Program which solves some homework problem (by using gradient descent or
 * Newton's method).
 *
 * @author Mate Gašparini
 */
public class Jednostavno {

    /** Minimum random point coordinate. */
    private static final double MIN = -5;

    /** Maximum random point coordinate. */
    private static final double MAX = 5;

    /** Single instance of the {@link FirstFunction}. */
    private static final IHFunction FIRST_FUNCTION = new FirstFunction();

    /** Single instance of the {@link SecondFunction}. */
    private static final IHFunction SECOND_FUNCTION = new SecondFunction();

    /** Map of problems, indexed by the problem labels. */
    private static final Map<String, BiFunction<Matrix, Integer, Matrix>> problems = Map.ofEntries(
            Map.entry("1a", (p, m) -> NumOptAlgorithms.gradientDescent(FIRST_FUNCTION, m, p)),
            Map.entry("1b", (p, m) -> NumOptAlgorithms.newtonsMethod(FIRST_FUNCTION, m, p)),
            Map.entry("2a", (p, m) -> NumOptAlgorithms.gradientDescent(SECOND_FUNCTION, m, p)),
            Map.entry("2b", (p, m) -> NumOptAlgorithms.newtonsMethod(SECOND_FUNCTION, m, p))
    );

    /**
     * Main method which is called when the program starts.
     *
     * @param args Command line arguments (problem label, maximum iteration,
     *        optional starting point coordinates).
     */
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 4) {
            System.err.println("Expected 2 or 4 arguments.");
            return;
        }

        String problemLabel = args[0];
        BiFunction<Matrix, Integer, Matrix> problem = problems.get(problemLabel);
        if (problem == null) {
            System.err.println("No problem found for '" + problemLabel + "'.");
            return;
        }

        int maxIteration;
        try {
            maxIteration = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            System.err.println("Maximum number of iterations not parsable.");
            return;
        }

        double x;
        double y;
        if (args.length == 2) {
            Random random = new Random();
            x = MIN + (MAX-MIN) * random.nextDouble();
            y = MIN + (MAX-MIN) * random.nextDouble();
        } else {
            try {
                x = Double.parseDouble(args[2]);
                y = Double.parseDouble(args[3]);
            } catch (NumberFormatException ex) {
                System.err.println("Starting point coordinates not parsable.");
                return;
            }
        }
        Matrix start = new Matrix(new double[][] {{x}, {y}});

        Matrix result = problem.apply(start, maxIteration);
        if (result == null) {
            System.out.println("Minimum not found.");
        } else {
            System.out.println("Minimum found at:");
            NumOptUtil.printPoint(result, System.out);
        }
    }
}
