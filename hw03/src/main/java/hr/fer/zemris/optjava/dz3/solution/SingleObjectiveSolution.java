package hr.fer.zemris.optjava.dz3.solution;

import java.util.Objects;

/**
 * Represents some generic solution, containing the fitness and the literal
 * value.
 *
 * @author Mate Gašparini
 */
public abstract class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution> {

    /** Fitness value of the solution. */
    public double fitness;

    /** Value of the solution. */
    public double value;

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
    public int compareTo(SingleObjectiveSolution other) {
        Objects.requireNonNull(other, "Other solution cannot be null.");
        return Double.compare(this.fitness, other.fitness);
    }
}
