package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;

/**
 * {@link IDecoder} which decodes a {@link BitVectorSolution}.
 *
 * @author Mate Gašparini
 */
public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution> {

    /** Minimum value for each variable. */
    protected double[] mins;

    /** Maximum value for each variable. */
    protected double[] maxs;

    /** Number of bits used for each variable. */
    protected int[] bits;

    /** Number of variables. */
    protected int n;

    /** Number of bits used to represent the solution. */
    protected int totalBits;

    /**
     * Constructor specifying the attributes.
     *
     * @param mins The specified minimum value for each variable.
     * @param maxs The specified maximum value for each variable.
     * @param bits The specified number of bits used for each variable.
     * @param n The specified number of variables.
     */
    public BitVectorDecoder(double mins, double maxs, int bits, int n) {
        this.mins = new double[n];
        fillWithValue(this.mins, mins);
        this.maxs = new double[n];
        fillWithValue(this.maxs, maxs);
        this.bits = new int[n];
        fillWithValue(this.bits, bits);
        this.n = n;
        this.totalBits = n*bits;
    }

    /**
     * Constructor specifying the attributes.
     *
     * @param mins The specified variable minimum values.
     * @param maxs The specified variable maximum values.
     * @param bits The specified variable number of bits.
     * @param totalBits The specified total number of bits.
     */
    public BitVectorDecoder(double[] mins, double[] maxs, int[] bits, int totalBits) {
        this.mins = mins;
        this.maxs = maxs;
        this.bits = bits;
        this.n = bits.length;
        this.totalBits = totalBits;
    }

    /**
     * Returns the total number of bits used to represent the solution.
     *
     * @return The total number of bits.
     */
    public int getTotalBits() {
        return totalBits;
    }

    /**
     * Returns the number of variables.
     *
     * @return The vector dimension.
     */
    public int getDimensions() {
        return n;
    }

    @Override
    public abstract double[] decode(BitVectorSolution solution);

    @Override
    public abstract void decode(BitVectorSolution solution, double[] values);

    /**
     * Helper method used to initialize the given array with the given value.
     *
     * @param array The given array.
     * @param value The given value.
     */
    private void fillWithValue(double[] array, double value) {
        for (int i = 0; i < array.length; i ++) {
            array[i] = value;
        }
    }

    /**
     * Helper method used to initialize the given array with the given value.
     *
     * @param array The given array.
     * @param value The given value.
     */
    private void fillWithValue(int[] array, int value) {
        for (int i = 0; i < array.length; i ++) {
            array[i] = value;
        }
    }
}
