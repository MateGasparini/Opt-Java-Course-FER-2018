package hr.fer.zemris.optjava.dz8.network;

import hr.fer.zemris.optjava.dz8.transfer.ITransferFunction;

/**
 * Class which represents some generic neural network model which contains
 * various information about layer dimensions, transfer function etc.
 *
 * @author Mate Gašparini
 */
public abstract class NeuralNetwork {

    /** Contains the number of neurons used in each layer. */
    protected int[] layerSizes;

    /** The transfer function used in neurons of each (except the first) layer. */
    protected ITransferFunction transferFunction;

    /** Stores the number of weights the network needs to provide output properly. */
    protected int weightsCount;

    /** Single instance of all layers and their calculated neuron values. */
    protected double[][] layerValues;

    /**
     * Constructor specifying the layer sizes and the transfer function.
     *
     * @param layerSizes The specified number of neurons used in each layer.
     * @param transferFunction The specified transfer function used in neurons
     *                         of each (except the first) layer.
     */
    public NeuralNetwork(int[] layerSizes, ITransferFunction transferFunction) {
        this.layerSizes = layerSizes;
        this.transferFunction = transferFunction;
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
    public abstract void calcOutputs(double[] inputs, double[] weights, double[] outputs);

    /**
     * Calculates the number of all weights and biases for this network.
     */
    protected abstract void calculateWeightsCount();
}
