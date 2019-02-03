package hr.fer.zemris.generic.ga;

/**
 * Represents an objective solution to some optimization problem.
 *
 * @param <T> The type of solution representation.
 * @author Mate Gašparini
 */
public abstract class GASolution<T> implements Comparable<GASolution<T>> {

    /** The objective fitness value. */
    public double fitness;

    /** The representation of the solution. */
    protected T data;

    /**
     * Returns the solution representation.
     *
     * @return The solution data.
     */
    public T getData() {
        return data;
    }

    /**
     * Returns a duplicate solution.
     *
     * @return The constructed duplicate of the solution.
     */
    public abstract GASolution<T> duplicate();

    @Override
    public int compareTo(GASolution<T> o) {
        return -Double.compare(this.fitness, o.fitness);
    }
}
