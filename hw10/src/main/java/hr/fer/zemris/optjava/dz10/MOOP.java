package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.algorithm.ObjectiveSpaceNSGA2;
import hr.fer.zemris.optjava.dz10.problem.FirstProblem;
import hr.fer.zemris.optjava.dz10.problem.MOOPProblem;
import hr.fer.zemris.optjava.dz10.problem.SecondProblem;

/**
 * Command line program which optimizes the specified problem using the
 * specified type of the <i>Non-Dominated Sorting Genetic Algorithm 2</i>.
 *
 * @author Mate Gašparini
 */
public class MOOP {

    /** The default value used for limiting the sharing function. */
    private static final double SIGMA_SHARE = 1.0;

    /** The BlxAlpha crossover parameter. */
    private static final double ALPHA = 0.33;

    /** The random uniform mutation parameter. */
    private static final double SIGMA = 0.1;

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (problem index, population size and
     *             number of iterations).
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Expected 3 arguments.");
            return;
        }

        MOOPProblem problem;
        try {
            int problemIndex = Integer.parseInt(args[0]);
            if (problemIndex == 1) {
                problem = new FirstProblem();
            } else if (problemIndex == 2) {
                problem = new SecondProblem();
            } else {
                System.err.println(problemIndex + " is not a valid problem index.");
                return;
            }
        } catch (NumberFormatException ex) {
            System.err.println(args[0] + " is not a valid problem index.");
            return;
        }

        int populationSize;
        try {
            populationSize = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            System.err.println(args[1] + " is not a valid population size.");
            return;
        }

        int maxIteration;
        try {
            maxIteration = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            System.err.println(args[2] + " is not a valid max iteration.");
            return;
        }

        new ObjectiveSpaceNSGA2(problem, populationSize, maxIteration,
                SIGMA_SHARE, ALPHA, SIGMA).run();
    }
}
