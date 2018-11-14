package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.part2.function.ExpenseFunction;
import hr.fer.zemris.optjava.dz5.part2.parser.ExpenseFunctionParser;
import hr.fer.zemris.optjava.dz5.part2.solution.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>Program which accepts multiple command line arguments and attempts to solve
 * a <i>Quadratic Assignment Problem</i> by using the <i>Self-adaptive
 * Segregative Genetic Algorithm with Simulated Annealing Aspects</i> (SASEGASA).</p>
 * Tested and recommended arguments:
 * <ul>
 *     <li>data/els19.dat 1000 50</li>
 *     <li>data/nug12.dat 1000 50</li>
 *     <li>data/nug25.dat 1000 50</li>
 * </ul>
 *
 * @author Mate Gašparini
 */
public class GeneticAlgorithm {

    /** Number of command line arguments needed to run the algorithm. */
    private static final int ARGS_LENGTH = 3;

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (path to the problem, population size,
     *             subpopulation count).
     */
    public static void main(String[] args) {
        if (args.length != ARGS_LENGTH) {
            System.err.println("Expected " + ARGS_LENGTH + " arguments.");
            return;
        }

        int populationSize;
        try {
            populationSize = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid population size: " + args[1]);
            return;
        }

        int subPopulationCount;
        try {
            subPopulationCount = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid subpopulation count: " + args[2]);
            return;
        }

        Path problemPath = Paths.get(args[0]);
        if (!Files.isReadable(problemPath)) {
            System.err.println("Invalid problem path: " + args[0]);
            return;
        }

        ExpenseFunction function;
        try {
            function = new ExpenseFunctionParser().parse(problemPath);
        } catch (IOException ex) {
            System.err.println("An IO error occurred while reading: " + args[0]);
            return;
        }

        Solution solution = new SASEGASA(function, populationSize, subPopulationCount).run();
        System.out.println(solution);
        System.out.println("Expense: " + solution.value);
    }
}
