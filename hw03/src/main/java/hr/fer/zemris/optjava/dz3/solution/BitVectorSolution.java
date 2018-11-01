package hr.fer.zemris.optjava.dz3.solution;

import java.util.Arrays;
import java.util.Random;

/**
 * Represents a {@link SingleObjectiveSolution} stored as a bit vector (in form
 * of a {@code byte} array).
 *
 * @author Mate Gašparini
 */
public class BitVectorSolution extends SingleObjectiveSolution {

    /** Array of solution values. */
    public byte[] bits;

    /**
     * Constructor specifying the size of the solution.
     *
     * @param size The specified number of bits.
     */
    public BitVectorSolution(int size) {
        bits = new byte[(size + 7) / 8]; // Worst case - 7 'unused' bits.
    }

    /**
     * Constructs a solution with the same bits array size and returns its
     * reference.
     *
     * @return Reference to the created solution.
     */
    public BitVectorSolution newLikeThis() {
        return new BitVectorSolution(bits.length * 8);
    }

    /**
     * Constructs a duplicate and returns its reference.
     *
     * @return Reference to the duplicate solution.
     */
    public BitVectorSolution duplicate() {
        BitVectorSolution duplicate = newLikeThis();
        duplicate.bits = Arrays.copyOf(this.bits, bits.length);
        duplicate.value = this.value;
        duplicate.fitness = this.fitness;
        return duplicate;
    }

    /**
     * Fills the bits array with pseudo-randomly generated values.
     *
     * @param random The given {@link Random} object.
     */
    public void randomize(Random random) {
        random.nextBytes(this.bits);
    }
}
