package hr.fer.zemris.optjava.dz7.network;

import hr.fer.zemris.optjava.dz7.network.transfer.ITransferFunction;

/**
 * Represents a <i>feedforward artificial neural network</i> which contains
 * various information about layer dimensions, transfer functions etc.
 *
 * @author Mate Gašparini
 */
public class FFANN {

    /** Contains the number of neurons used in each layer. */
    private int[] layerSizes;

    /** Contains transfer function used in neurons of each (except the first) layer. */
    private ITransferFunction[] transferFunctions;

    /** Stores the number of weights the network needs to provide output properly. */
    private int weightsCount;

    /** Single instance of all layers and their calculated neuron values. */
    private double[][] layerValues;

    /**
     * Constructor specifying the layer sizes and the transfer functions.
     *
     * @param layerSizes The specified number of neurons used in each layer.
     * @param transferFunctions The specified transfer functions used in neurons
     *                          of each (except the first) layer.
     */
    public FFANN(int[] layerSizes, ITransferFunction[] transferFunctions) {
        this.layerSizes = layerSizes;
        this.transferFunctions = transferFunctions;
        calculateWeightsCount();
        this.layerValues = new double[layerSizes.length][];
        for (int i = 1; i < layerValues.length - 1; i ++) {
            layerValues[i] = new double[layerSizes[i]];
        }
    }

    /**
     * Returns the number of weights the network needs to provide output properly.
     *
     * @return Number of all weights and biases (constant value for some network).
     */
    public int getWeightsCount() {
        return weightsCount;
    }

    /**
     * Propagates the given inputs using the given weights through the network
     * and fills the given outputs with the calculated values.
     *
     * @param inputs The given inputs.
     * @param weights The given weights.
     * @param outputs The given outputs.
     */
    public void calcOutputs(double[] inputs, double[] weights, double[] outputs) {
        layerValues[0] = inputs;
        layerValues[layerValues.length - 1] = outputs;

        int weightIndex = 0;
        for (int i = 1; i < layerSizes.length; i ++) {
            for (int layerNeuron = 0; layerNeuron < layerSizes[i]; layerNeuron ++) {
                double value = weights[weightIndex++]; // Neuron's bias value.
                for (int j = 0; j < layerSizes[i-1]; j ++) {
                    value += layerValues[i-1][j] * weights[weightIndex++];
                }
                value = transferFunctions[i-1].transfer(value);
                layerValues[i][layerNeuron] = value;
            }
        }
    }

    /**
     * Calculates the number of all weights and biases for this network.
     */
    private void calculateWeightsCount() {
        for (int i = 1; i < layerSizes.length; i ++) {
            weightsCount += layerSizes[i-1]*layerSizes[i]; // Weights.
            weightsCount += layerSizes[i]; // Biases.
        }
    }
}
