package hr.fer.zemris.trisat;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * <p>Represents the {@link BitVector} neighborhood function.</p>
 * <p>For some assignment x, it is commonly denoted as &#960;(x).</p>
 *
 * @author Mate Gašparini
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector> {

    /** The specified assignment vector. */
    private BitVector assignment;

    /**
     * Constructor specifying the assignment vector.
     *
     * @param assignment The specified assignment vector.
     * @throws NullPointerException If the specified assignment vector is
     *         {@code null}.
     */
    public BitVectorNGenerator(BitVector assignment) {
        this.assignment = Objects.requireNonNull(assignment, "Assignment cannot be null.");
    }

    @Override
    public Iterator<MutableBitVector> iterator() {
        return new NeighborIterator();
    }

    /**
     * Returns an array filled with all assignment vector's neighbors.
     *
     * @return An array of all assignment's neighbors.
     */
    public MutableBitVector[] createNeighborhood() {
        int size = assignment.getSize();
        MutableBitVector[] neighborhood = new MutableBitVector[size];
        for (int i = 0; i < size; i ++) {
            neighborhood[i] = generateMutatedVector(i);
        }
        return neighborhood;
    }

    /**
     * Generates a {@link MutableBitVector} with a bit flipped at the specified
     * mutation index.
     *
     * @param mutationIndex The specified mutation index.
     * @return The corresponding mutated {@link MutableBitVector}.
     */
    private MutableBitVector generateMutatedVector(int mutationIndex) {
        MutableBitVector neighbor = assignment.copy();
        neighbor.set(mutationIndex, !neighbor.get(mutationIndex));
        return neighbor;
    }

    /**
     * <p><i>Iterator</i> which iterates through all of the assignment's neighbors.</p>
     * <p>The neighbors are generated one-by-one, with each {@code next()} method
     * call.</p>
     */
    private class NeighborIterator implements Iterator<MutableBitVector> {

        /** Number of already generated neighbors. */
        private int counter;

        /** Number of all possible neighbors. */
        private int size = assignment.getSize();

        @Override
        public boolean hasNext() {
            return counter < size;
        }

        @Override
        public MutableBitVector next() {
            if (!hasNext()) {
                throw new NoSuchElementException("End of neighbors reached.");
            }
            return generateMutatedVector(counter ++);
        }
    }
}
