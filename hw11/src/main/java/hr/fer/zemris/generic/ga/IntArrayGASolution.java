package hr.fer.zemris.generic.ga;

/**
 * Represents an objective solution which is represented as an {@code int} array.
 *
 * @author Mate Gašparini
 */
public class IntArrayGASolution extends GASolution<int[]> {

    /**
     * Constructor specifying the data representation.
     *
     * @param data The specified data.
     */
    public IntArrayGASolution(int[] data) {
        this.data = data;
    }

    @Override
    public IntArrayGASolution duplicate() {
        return new IntArrayGASolution(this.data);
    }
}
