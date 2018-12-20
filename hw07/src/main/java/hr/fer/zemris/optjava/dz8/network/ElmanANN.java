package hr.fer.zemris.optjava.dz8.network;

import hr.fer.zemris.optjava.dz8.transfer.ITransferFunction;

/**
 * Represents the <i>Elman artificial neural network</i>.
 *
 * @author Mate Gašparini
 */
public class ElmanANN extends NeuralNetwork {

    /** Array of stored context neurons' values. */
    private double[] context;

    /**
     * Constructor specifying the layer sizes and the transfer function.
     *
     * @param layerSizes The specified number of neurons used in each layer.
     * @param transferFunction The specified transfer function used in neurons
     *                         of each (except the first) layer.
     */
    public ElmanANN(int[] layerSizes, ITransferFunction transferFunction) {
        super(layerSizes, transferFunction);
        context = new double[layerSizes[1]];
    }

    @Override
    public void calcOutputs(double[] inputs, double[] weights, double[] outputs) {
        layerValues[0] = inputs;
        layerValues[layerValues.length - 1] = outputs;

        int weightIndex = 0;
        for (int hiddenNeuron = 0; hiddenNeuron < layerSizes[1]; hiddenNeuron ++) {
            double sum = weights[weightIndex++]; // Neuron's bias value.
            for (int inputNeuron = 0; inputNeuron < layerSizes[0]; inputNeuron ++) {
                sum += layerValues[0][inputNeuron] * weights[weightIndex++];
            }

            for (double contextValue : context) {
                sum += contextValue * weights[weightIndex++];
            }

            layerValues[1][hiddenNeuron] = transferFunction.transfer(sum);
        }

        for (int i = 0; i < context.length; i ++) {
            context[i] = layerValues[1][i];
        }

        for (int i = 2; i < layerSizes.length; i ++) {
            for (int layerNeuron = 0; layerNeuron < layerSizes[i]; layerNeuron ++) {
                double sum = weights[weightIndex++]; // Neuron's bias value.
                for (int j = 0; j < layerSizes[i-1]; j ++) {
                    sum += layerValues[i-1][j] * weights[weightIndex++];
                }
                layerValues[i][layerNeuron] = transferFunction.transfer(sum);
            }
        }
    }

    @Override
    protected void calculateWeightsCount() {
        for (int i = 1; i < layerSizes.length; i ++) {
            weightsCount += layerSizes[i-1]*layerSizes[i]; // Weights.
            weightsCount += layerSizes[i]; // Biases.
        }
        weightsCount += layerSizes[1] * layerSizes[1]; // Context weights.
    }
}
