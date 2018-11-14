package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.dz4.part1.function.IFunction;
import hr.fer.zemris.optjava.dz4.part1.parser.ErrorFunctionParser;
import hr.fer.zemris.optjava.dz4.part1.selection.ISelection;
import hr.fer.zemris.optjava.dz4.part1.selection.NTournamentSelection;
import hr.fer.zemris.optjava.dz4.part1.selection.RouletteWheelSelection;
import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * <p>Program which accepts multiple command line arguments and attempts to solve
 * an equation system by minimizing the system's error function.</p>
 * Tested and recommended arguments:
 * <ul>
 *     <li>500 20 20000 rouletteWheel 0.5</li>
 *     <li>500 20 5000 tournament:100 0.5</li>
 * </ul>
 *
 * @author Mate Gašparini
 */
public class GeneticAlgorithm {

    /** Number of command line arguments needed to run the algorithm. */
    private static final int ARGS_LENGTH = 5;

    /** Roulette wheel selection type token. */
    private static final String ROULETTE_WHEEL = "rouletteWheel";

    /** Tournament selection type token. */
    private static final String TOURNAMENT = "tournament:";

    /** Path to the function definition file. */
    private static final String FUNCTION_PATH = "src/main/resources/02-zad-prijenosna.txt";

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (population size, minimum error,
     *             maximum generation, selection type, sigma).
     */
    public static void main(String[] args) {
        if (args.length != ARGS_LENGTH) {
            System.err.println("Expected " + ARGS_LENGTH + " arguments.");
            return;
        }

        int populationSize;
        try {
            populationSize = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid population size: " + args[0]);
            return;
        }

        double minError;
        try {
            minError = Double.parseDouble(args[1]);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid minimum error: " + args[1]);
            return;
        }

        int maxGeneration;
        try {
            maxGeneration = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid maximum generation: " + args[2]);
            return;
        }

        ISelection<DoubleArraySolution> selection;
        if (args[3].equals(ROULETTE_WHEEL)) {
            selection = new RouletteWheelSelection();
        } else if (args[3].startsWith(TOURNAMENT)) {
            String tournamentSize = args[3].substring(TOURNAMENT.length());
            try {
                int n = Integer.parseInt(tournamentSize);
                if (n < NTournamentSelection.MIN_TOURNAMENT) {
                    System.err.println("Tournament size must be at least "
                            + NTournamentSelection.MIN_TOURNAMENT);
                    return;
                }
                selection = new NTournamentSelection(n);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid tournament size: " + tournamentSize);
                return;
            }
        } else {
            System.err.println("Invalid selection type: " + args[3]);
            return;
        }

        double sigma;
        try {
            sigma = Double.parseDouble(args[4]);
        } catch (NumberFormatException ex) {
            System.err.println("Invalid sigma: " + args[4]);
            return;
        }

        try {
            IFunction function = new ErrorFunctionParser().parse(Paths.get(FUNCTION_PATH));
            DoubleArraySolution solution = new GeneticAlgorithmEngine(
                    function, populationSize, minError,
                    maxGeneration, selection, sigma
            ).run();

            System.out.println("====================");
            System.out.println("Best solution found:");
            System.out.println(solution);
            System.out.println("Error value: " + solution.value);
        } catch (IOException ex) {
            System.err.println("An IO error occurred while reading: " + FUNCTION_PATH);
        }
    }
}
