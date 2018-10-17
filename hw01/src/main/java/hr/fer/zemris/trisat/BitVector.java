package hr.fer.zemris.trisat;

import java.util.Objects;
import java.util.Random;

/**
 * <p>Represents an immutable vector of {@code boolean} values.</p>
 * <p>For a mutable version, see the {@link MutableBitVector} subclass.</p>
 *
 * @author Mate Gašparini
 */
public class BitVector {

    /** Internal array of bit values. */
    protected boolean[] bits;

    /**
     * Constructor specifying the {@link Random} instance (used to generate all
     * bit values) and the size of the bits array.
     *
     * @param rand The specified {@link Random} instance.
     * @param numberOfBits The specified array size.
     * @throws NullPointerException If the given {@link Random} instance is
     *         {@code null}.
     */
    public BitVector(Random rand, int numberOfBits) {
        this(numberOfBits);
        Objects.requireNonNull(rand, "Random instance cannot be null.");
        for (int i = 0; i < numberOfBits; i ++) {
            bits[i] = rand.nextBoolean();
        }
    }

    /**
     * Constructor specifying the bit values.
     *
     * @param bits The specified bit values.
     * @throws NullPointerException If the given argument is {@code null}.
     */
    public BitVector(boolean... bits) {
        this.bits = Objects.requireNonNull(bits, "Bits cannot be null.");
    }

    /**
     * <p>Constructor specifying the size of the bit array.</p>
     * <p>Each element of the bit array will be set to {@code false}.</p>
     *
     * @param n The specified size of the bit array.
     */
    public BitVector(int n) {
        this.bits = new boolean[n];
    }

    /**
     * Returns the value of the bit at the given index.
     *
     * @param index The given index.
     * @return {@code true} if the bit at the given index is {@code true}, or
     *         {@code false} otherwise.
     */
    public boolean get(int index) {
        return bits[index];
    }

    /**
     * Returns the length of the internal bit array.
     *
     * @return The size of the vector.
     */
    public int getSize() {
        return bits.length;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (boolean bit : bits) {
            builder.append(bit ? 1 : 0);
        }
        return builder.toString();
    }

    /**
     * Constructs a {@link MutableBitVector} with a copy of the bit values and
     * returns its reference.
     *
     * @return A {@link MutableBitVector} copy of this vector.
     */
    public MutableBitVector copy() {
        return new MutableBitVector(bits.clone());
    }
}
