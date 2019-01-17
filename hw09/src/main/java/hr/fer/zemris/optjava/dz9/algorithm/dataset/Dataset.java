package hr.fer.zemris.optjava.dz9.algorithm.dataset;

/**
 * Represents collected data about some function inputs and outputs.
 *
 * @author Mate Gašparini
 */
public class Dataset {

    /** The specified inputs. */
    private double[][] inputs;

    /** The acquired function outputs. */
    private double[] expectedOutputs;

    /**
     * Constructor specifying some function inputs and outputs.
     *
     * @param inputs The specified inputs.
     * @param expectedOutputs The acquired function outputs.
     */
    public Dataset(double[][] inputs, double[] expectedOutputs) {
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;
    }

    /**
     * Returns the number of input vectors.
     *
     * @return The number of samples.
     */
    public int getSampleCount() {
        return inputs.length;
    }

    /**
     * Returns the input value at the specified position.
     *
     * @param sample The specified sample (input vector index).
     * @param component The specified input vector component.
     * @return The corresponding input value.
     */
    public double getInput(int sample, int component) {
        return inputs[sample][component];
    }

    /**
     * Calculates the mean squared error between the given outputs and the
     * expected outputs.
     *
     * @param calculatedOutputs The given outputs.
     * @return The calculated error value.
     */
    public double calculateError(double[] calculatedOutputs) {
        double error = 0.0;
        for (int i = 0; i < expectedOutputs.length; i ++) {
            double componentError = expectedOutputs[i] - calculatedOutputs[i];
            error += componentError*componentError;
        }
        return error;
    }
}
