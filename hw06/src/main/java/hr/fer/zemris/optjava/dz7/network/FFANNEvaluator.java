package hr.fer.zemris.optjava.dz7.network;

import hr.fer.zemris.optjava.dz7.network.dataset.IReadOnlyDataset;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * Class which is used for evaluation the specified neural network (and its
 * weight values) on some specified dataset.
 *
 * @author Mate Gašparini
 */
public class FFANNEvaluator {

    /** The specified neural network. */
    private FFANN network;

    /** The specified dataset. */
    private IReadOnlyDataset dataset;

    /** The specified weight values. */
    private double[] weights;

    /**
     * Constructor specifying the neural network, the dataset and the weight values.
     *
     * @param network The specified neural network (has to have right dimensions).
     * @param dataset The specified dataset (contains the expected results for given inputs).
     * @param weights The specified weights (the values which are evaluated).
     */
    public FFANNEvaluator(FFANN network, IReadOnlyDataset dataset, double[] weights) {
        this.network = network;
        this.dataset = dataset;
        this.weights = weights;
    }

    /**
     * <p>Iterates through the dataset samples and compares the expected outputs
     * with calculated outputs (using the neural network weights) for each
     * sample input.</p>
     * <p>Prints out the statistics to the given stream.</p>
     *
     * @param stream The given stream.
     */
    public void evaluate(PrintStream stream) {
        int hitCounter = 0;
        for (int i = 0; i < dataset.getSampleSize(); i ++) {
            double[] inputs = dataset.getInputsAt(i);
            double[] outputs = new double[dataset.getOutputSize()];
            network.calcOutputs(inputs, weights, outputs);
            round(outputs);

            double[] expected = dataset.getOutputsAt(i);
            stream.print("Expected: " + Arrays.toString(expected)
                    + " Calculated: " + Arrays.toString(outputs));
            if (Arrays.equals(expected, outputs)) {
                hitCounter ++;
                stream.println(" OK");
            } else {
                stream.println(" NOT OK");
            }
        }

        stream.println();
        stream.println("Hits: " + hitCounter);
        stream.println("Misses: " + (dataset.getSampleSize() - hitCounter));
    }

    /**
     * Used to round the given (output) values (as the dataset samples contain
     * binary outputs).
     *
     * @param values The given (calculated) values.
     */
    private void round(double[] values) {
        for (int i = 0; i < values.length; i ++) {
            values[i] = values[i] < 0.5 ? 0.0 : 1.0;
        }
    }
}
