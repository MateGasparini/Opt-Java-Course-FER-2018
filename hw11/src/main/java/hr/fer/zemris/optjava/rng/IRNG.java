package hr.fer.zemris.optjava.rng;

/**
 * Interface which represents some random number generator.
 *
 * @author Mate Gašparini
 */
public interface IRNG {

    /**
     * Returns some uniformly chosen real value from the range 0.0d (inclusive)
     * to 1.0d (exclusive).
     *
     * @return The pseudorandomly generated real value.
     */
    double nextDouble();

    /**
     * Returns some uniformly chosen real value from the range {@code min}
     * (inclusive) to {@code max} (exclusive).
     *
     * @param min The lower bound of the interval.
     * @param max The upper bound of the interval.
     * @return The pseudorandomly generated real value.
     */
    double nextDouble(double min, double max);

    /**
     * Returns some uniformly chosen real value from the range 0.0f (inclusive)
     * to 1.0f (exclusive).
     *
     * @return The pseudorandomly generated real value.
     */
    float nextFloat();

    /**
     * Returns some uniformly chosen real value from the range {@code min}
     * (inclusive) to {@code max} (exclusive).
     *
     * @param min The lower bound of the interval.
     * @param max The upper bound of the interval.
     * @return The pseudorandomly generated real value.
     */
    float nextFloat(float min, float max);

    /**
     * Returns some uniformly chosen integer value from the range 0 (inclusive)
     * to {@link Integer#MAX_VALUE} (inclusive).
     *
     * @return The pseudorandomly generated integer value.
     */
    int nextInt();

    /**
     * Returns some uniformly chosen integer value from the range {@code min}
     * (inclusive) to {@code max} (exclusive).
     *
     * @param min The lower bound of the interval.
     * @param max The upper bound of the interval.
     * @return The pseudorandomly generated integer value.
     */
    int nextInt(int min, int max);

    /**
     * Returns some uniformly chosen boolean value.
     *
     * @return The pseudorandomly generated {@code true} or {@code false} value.
     */
    boolean nextBoolean();

    /**
     * Returns some Gaussian ("normally") distributed real value with mean 0.0
     * and standard deviation 1.0.
     *
     * @return The pseudorandomly generated real value.
     */
    double nextGaussian();
}
