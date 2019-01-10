package hr.fer.zemris.optjava.dz9.solution;

/**
 * {@link Solution} which represents the decision values as a {@code double}
 * array.
 *
 * @author Mate Gašparini
 */
public class DoubleArraySolution extends Solution {

    /** Array of decision values. */
    private double[] values;

    /**
     * Constructor specifying the decision values array.
     *
     * @param values The specified decision values.
     */
    public DoubleArraySolution(double[] values) {
        this.values = values;
    }

    /**
     * Returns the decision values array.
     *
     * @return The decision values.
     */
    public double[] getValues() {
        return values;
    }
}
