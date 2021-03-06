package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;

/**
 * {@link BitVectorDecoder} which decodes the bits to real values in Gray
 * (reflected binary code) ordering.
 *
 * @author Mate Gašparini
 */
public class GrayBinaryDecoder extends BitVectorDecoder {

    /**
     * Constructor specifying the attributes.
     *
     * @param mins The specified minimum value for each variable.
     * @param maxs The specified maximum value for each variable.
     * @param bits The specified number of bits used for each variable.
     * @param n The specified number of variables.
     */
    public GrayBinaryDecoder(double mins, double maxs, int bits, int n) {
        super(mins, maxs, bits, n);
    }

    /**
     * Constructor specifying the attributes.
     *
     * @param mins The specified variable minimum values.
     * @param maxs The specified variable maximum values.
     * @param bits The specified variable number of bits.
     * @param totalBits The specified total number of bits.
     */
    public GrayBinaryDecoder(double[] mins, double[] maxs, int[] bits, int totalBits) {
        super(mins, maxs, bits, totalBits);
    }

    @Override
    public double[] decode(BitVectorSolution solution) {
        double[] values = new double[n];
        decode(solution, values);
        return values;
    }

    @Override
    public void decode(BitVectorSolution solution, double[] values) {
        int readPart = 0;
        int currentVariable = 0;
        int k = 0;
        for (int i = 0; i < totalBits; i ++) {
            k <<= 1;
            int byteIndex = i / 8;
            int bitIndex = i % 8;
            k += (solution.bits[byteIndex] >> bitIndex) & 0x01;
            readPart ++;
            if (readPart == bits[currentVariable]) {
                k = grayToBinary(k);
                values[currentVariable] = mins[currentVariable]
                        + (double) k / (1 << bits[currentVariable] - 1)
                        * (maxs[currentVariable] - mins[currentVariable]);
                readPart = 0;
                k = 0;
                currentVariable ++;
            }
        }
    }

    /**
     * Converts the given Gray-coded number to the actual value.
     *
     * @param number The given number.
     * @return The converted (binary) value.
     */
    private int grayToBinary(int number) {
        int mask;
        for (mask = number >> 1; mask != 0; mask = mask >> 1) {
            number ^= mask;
        }
        return number;
    }
}
