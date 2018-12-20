package hr.fer.zemris.optjava.dz8.function;

import hr.fer.zemris.optjava.dz8.dataset.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.network.NeuralNetwork;

/**
 * Represents an error function of some neural network's weights for some
 * specified dataset.
 *
 * @author Mate Gašparini
 */
public class ErrorFunction implements Function {

    /** The neural network used for calculating the outputs. */
    private NeuralNetwork network;

    /** Dataset for the given problem. Contains expected input-output pairs. */
    private IReadOnlyDataset dataset;

    /**
     * Constructor specifying the neural network and the problem dataset.
     *
     * @param network The specified neural network used for calculating the outputs.
     * @param dataset The specified problem dataset with expected input-output pairs.
     */
    public ErrorFunction(NeuralNetwork network, IReadOnlyDataset dataset) {
        this.network = network;
        this.dataset = dataset;
    }

    @Override
    public int getDimension() {
        return network.getWeightsCount();
    }

    @Override
    public double valueAt(double[] point) {
        double error = 0.0;
        for (int sampleIndex = 0; sampleIndex < dataset.getSampleSize(); sampleIndex ++) {
            double[] inputs = dataset.getInputsAt(sampleIndex);
            double[] outputs = new double[dataset.getOutputSize()];
            network.calcOutputs(inputs, point, outputs);

            double[] expected = dataset.getOutputsAt(sampleIndex);
            for (int i = 0; i < dataset.getOutputSize(); i ++) {
                double currentError = expected[i] - outputs[i];
                error += currentError * currentError;
            }
        }

        return error / dataset.getSampleSize();
    }
}
