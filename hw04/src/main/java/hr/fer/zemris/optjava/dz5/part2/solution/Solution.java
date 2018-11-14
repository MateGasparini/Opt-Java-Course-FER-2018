package hr.fer.zemris.optjava.dz5.part2.solution;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * Models a solution based on an {@code int} array.
 *
 * @author Mate Gašparini
 */
public class Solution implements Comparable<Solution> {

    /** Single {@link Random} instance. */
    private static Random random = new Random();

    /** Value of the solution. */
    public double value;

    /** Fitness corresponding to the value of the solution. */
    public double fitness;

    /** Array of solution values. */
    public int[] values;

    /**
     * Constructor specifying the size of the randomly generated values array.
     *
     * @param size The specified size;
     */
    public Solution(int size) {
        values = new int[size];
        randomize();
    }

    /**
     * Constructor specifying the values array.
     *
     * @param values The specified values array.
     */
    public Solution(int[] values) {
        this.values = values;
    }

    /**
     * <p>Compares this solution with the specified solution.</p>
     * <p>Returns a negative integer, zero, or a positive integer as this
     * solution's fitness is less than, equal to, or greater than the specified
     * solution's fitness.</p>
     *
     * @param other The specified solution.
     * @return A negative integer, zero, or a positive integer as this solution's
     *         fitness is less than, equal to, or greater than the specified
     *         solution's fitness.
     */
    @Override
    public int compareTo(Solution other) {
        Objects.requireNonNull(other, "Other solution cannot be null.");
        return Double.compare(this.fitness, other.fitness);
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    /**
     * Randomly switches elements in the values array.
     */
    private void randomize() {
        generateOrdered();

        for (int i = 0; i < values.length - 1; i ++) {
            int j = random.nextInt(values.length - i) + i;

            int ithCopy = values[i];
            values[i] = values[j];
            values[j] = ithCopy;
        }
    }

    /**
     * Fills the values array with ascending values starting at 0.
     */
    private void generateOrdered() {
        for (int i = 0; i < values.length; i ++) {
            values[i] = i;
        }
    }
}
