package hr.fer.zemris.optjava.dz8.network;

import hr.fer.zemris.optjava.dz8.transfer.ITransferFunction;

/**
 * Represents the <i>feedforward artificial neural network</i>.
 *
 * @author Mate Gašparini
 */
public class FFANN extends NeuralNetwork {

    /**
     * Constructor specifying the layer sizes and the transfer function.
     *
     * @param layerSizes The specified number of neurons used in each layer.
     * @param transferFunction The specified transfer function used in neurons
     *                         of each (except the first) layer.
     */
    public FFANN(int[] layerSizes, ITransferFunction transferFunction) {
        super(layerSizes, transferFunction);
    }

    @Override
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
                value = transferFunction.transfer(value);
                layerValues[i][layerNeuron] = value;
            }
        }
    }

    @Override
    protected void calculateWeightsCount() {
        for (int i = 1; i < layerSizes.length; i ++) {
            weightsCount += layerSizes[i-1]*layerSizes[i]; // Weights.
            weightsCount += layerSizes[i]; // Biases.
        }
    }
}
