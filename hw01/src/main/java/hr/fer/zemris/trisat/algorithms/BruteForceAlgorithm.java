package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

import java.io.PrintStream;

/**
 * <p>Algorithm 1 from the homework.</p>
 * <p>It performs good for easy problems, and really bad for hard problems.</p>
 * <p>Its weakness is that it tests every single possibility.</p>
 *
 * @author Mate Gašparini
 */
public class BruteForceAlgorithm implements Algorithm {

    /** The specified output stream. */
    private PrintStream printStream;

    /**
     * Constructs the algorithm with the specified {@link PrintStream} for
     * writing all found solutions.
     *
     * @param printStream The specified {@link PrintStream}.
     */
    public BruteForceAlgorithm(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public BitVector solve(SATFormula formula) {
        int length = formula.getNumberOfVariables();
        BitVector solution = null;
        MutableBitVector assignment = new MutableBitVector(length);

        for (long counter = 0L; counter < 1L << length; counter ++) {
            for (int i = 0; i < length; i ++) {
                boolean value = assignment.get(i);
                assignment.set(i, !value);
                if (!value) break;
            }

            if (formula.isSatisfied(assignment)) {
                printStream.println(assignment);
                solution = assignment;
            }
        }

        return solution;
    }
}
