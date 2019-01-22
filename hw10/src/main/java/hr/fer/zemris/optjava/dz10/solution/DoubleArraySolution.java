package hr.fer.zemris.optjava.dz10.solution;

/**
 * {@link Solution} which represents the decision values as a {@code double}
 * array.
 *
 * @author Mate Gašparini
 */
public class DoubleArraySolution extends Solution {

    /** Array of decision values. */
    private double[] values;

    /** The crowding-distance value. */
    private double distance;

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

    /**
     * Returns the accumulated crowding-distance value.
     *
     * @return The crowding-distance.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Increases the crowding-distance value by the given amount.
     *
     * @param value The given amount.
     */
    public void increaseDistance(double value) {
        distance += value;
    }
}
