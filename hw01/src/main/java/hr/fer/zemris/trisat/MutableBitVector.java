package hr.fer.zemris.trisat;

/**
 * <p>Represents a mutable vector of {@code boolean} values.</p>
 * <p>For a immutable version, see the {@link BitVector} superclass.</p>
 *
 * @author Mate Gašparini
 */
public class MutableBitVector extends BitVector {

    /**
     * Constructor specifying the bit values.
     *
     * @param bits The specified bit values.
     * @throws NullPointerException If the given argument is {@code null}.
     */
    public MutableBitVector(boolean... bits) {
        super(bits);
    }

    /**
     * <p>Constructor specifying the size of the bit array.</p>
     * <p>Each element of the bit array will be set to {@code false}.</p>
     *
     * @param n The specified size of the bit array.
     */
    public MutableBitVector(int n) {
        super(n);
    }

    /**
     * Sets the bit at the given index to the given value.
     *
     * @param index The given index.
     * @param value The given value.
     */
    public void set(int index, boolean value) {
        bits[index] = value;
    }
}
