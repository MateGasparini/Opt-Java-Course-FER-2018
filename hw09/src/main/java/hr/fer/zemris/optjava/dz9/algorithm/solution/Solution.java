package hr.fer.zemris.optjava.dz9.algorithm.solution;

/**
 * Solution to some real value optimization problem.
 *
 * @author Mate Gašparini
 */
public abstract class Solution implements Comparable<Solution> {

    /** Value of the solution. */
    private double value;

    /** Calculated fitness. */
    private double fitness;

    /**
     * Returns the value of the solution.
     *
     * @return The value.
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the solution to the given value.
     *
     * @param value The given value.
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Sets the fitness value to the given value.
     *
     * @param fitness The given value.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Solution other) {
        return Double.compare(this.fitness, other.fitness);
    }
}
