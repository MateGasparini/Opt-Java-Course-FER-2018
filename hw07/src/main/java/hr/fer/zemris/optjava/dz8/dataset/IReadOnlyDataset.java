package hr.fer.zemris.optjava.dz8.dataset;

/**
 * Represents some dataset of samples (input-output vector pairs).
 *
 * @author Mate Gašparini
 */
public interface IReadOnlyDataset {

    /**
     * Returns the number of stored samples.
     *
     * @return The total sample size.
     */
    int getSampleSize();

    /**
     * Returns the number of outputs in each sample.
     *
     * @return The output vector size.
     */
    int getOutputSize();

    /**
     * Returns the input vector at the given index.
     *
     * @param sampleIndex The given index.
     * @return The corresponding input vector.
     */
    double[] getInputsAt(int sampleIndex);

    /**
     * Returns the output vector at the given index.
     *
     * @param sampleIndex The given index.
     * @return The corresponding output vector.
     */
    double[] getOutputsAt(int sampleIndex);
}
