package hr.fer.zemris.optjava.dz3.neighborhood;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;

import java.util.Random;

/**
 * Calculates the random neighbor by changing a single bit somewhere in the
 * solution's array of {@code byte} values.
 *
 * @author Mate Gašparini
 */
public class BitVectorNeighborhood implements INeighborhood<BitVectorSolution> {

    /** Internal {@link Random} instance. */
    private Random random = new Random();

    @Override
    public BitVectorSolution randomNeighbor(BitVectorSolution point) {
        BitVectorSolution neighbor = point.duplicate();
        byte[] bits = neighbor.bits;
        int octet = random.nextInt(bits.length);
        byte mask = (byte) (1 << random.nextInt(8));
        bits[octet] ^= mask;
        return neighbor;
    }
}
