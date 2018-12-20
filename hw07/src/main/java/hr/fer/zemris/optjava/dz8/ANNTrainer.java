package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.algorithm.DifferentialEvolution;
import hr.fer.zemris.optjava.dz8.algorithm.OptAlgorithm;
import hr.fer.zemris.optjava.dz8.dataset.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz8.dataset.LoadedDataSet;
import hr.fer.zemris.optjava.dz8.function.ErrorFunction;
import hr.fer.zemris.optjava.dz8.network.ElmanANN;
import hr.fer.zemris.optjava.dz8.network.FFANN;
import hr.fer.zemris.optjava.dz8.network.NeuralNetwork;
import hr.fer.zemris.optjava.dz8.transfer.HyperbolicTangentTransferFunction;
import hr.fer.zemris.optjava.dz8.transfer.ITransferFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Command line program which loads some time series dataset, constructs the
 * specified neural network and evolves it using the {@link DifferentialEvolution}
 * (DE) algorithm.
 *
 * @author Mate Gašparini
 */
public class ANNTrainer {

    /** <i>Time Delay Neural Network</i> label. */
    private static final String TDNN = "tdnn-";

    /** <i>Elman Neural Network</i> label. */
    private static final String ELMAN = "elman-";

    /** Number of samples to load into the dataset. */
    private static final int ELEMENT_COUNT = 600;

    /** Transfer function used by the neurons. */
    private static final ITransferFunction TRANSFER_FUNCTION =
            new HyperbolicTangentTransferFunction();

    /** Mutant factor value (typically, a value between 0 and 2). */
    private static final double F = 0.3;

    /** Crossover chance value (between 0 and 1). */
    private static final double C_R = 0.6;

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (dataset filepath, network
     *             label/architecture), population size, minimum error and
     *             maximum iteration.
     */
    public static void main(String[] args) {
        if (args.length != 5) {
            System.err.println("Expected 5 arguments.");
            return;
        }

        Path dataPath = Paths.get(args[0]);
        if (!Files.isReadable(dataPath)) {
            System.err.println(args[0] + " could not be read.");
            return;
        }

        int populationSize;
        try {
            populationSize = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            System.err.println(args[2] + " is not a valid population size.");
            return;
        }

        double minError;
        try {
            minError = Double.parseDouble(args[3]);
        } catch (NumberFormatException ex) {
            System.err.println(args[3] + " is not a valid minimum error.");
            return;
        }

        int maxIteration;
        try {
            maxIteration = Integer.parseInt(args[4]);
        } catch (NumberFormatException ex) {
            System.err.println(args[4] + " is not a valid max iteration.");
            return;
        }

        IReadOnlyDataset dataset;
        NeuralNetwork network;
        try {
            if (args[1].startsWith(TDNN)) {
                int[] layerSizes = parseLayerSizes(args[1].substring(TDNN.length()));
                if (layerSizes == null) {
                    System.err.println("Network architecture must be given as A1xA2x...xAn.");
                    return;
                } else if (layerSizes[layerSizes.length-1] != 1) {
                    System.err.println("Output layer must be of size 1.");
                    return;
                }

                network = new FFANN(layerSizes, TRANSFER_FUNCTION);
                dataset = LoadedDataSet.loadFromFile(dataPath, layerSizes[0], ELEMENT_COUNT);
            } else if (args[1].startsWith(ELMAN)) {
                int[] layerSizes = parseLayerSizes(args[1].substring(ELMAN.length()));
                if (layerSizes == null) {
                    System.err.println("Network architecture must be given as A1xA2x...xAn.");
                    return;
                } else if (layerSizes[layerSizes.length-1] != 1) {
                    System.err.println("Output layer must be of size 1.");
                    return;
                } else if (layerSizes[0] != 1) {
                    System.err.println("Elman NN's input layer must be of size 1.");
                    return;
                }

                network = new ElmanANN(layerSizes, TRANSFER_FUNCTION);
                dataset = LoadedDataSet.loadFromFile(dataPath, ELEMENT_COUNT);
            } else {
                System.err.println(args[1] + " is not a valid neural network label.");
                return;
            }
        } catch (IOException ex) {
            System.err.println("A problem occurred while reading " + args[0] + ".");
            return;
        } catch (NumberFormatException ex) {
            System.err.println(args[0] + " contains invalid content.");
            return;
        }

        OptAlgorithm algorithm = new DifferentialEvolution(
                new ErrorFunction(network, dataset),
                populationSize,
                maxIteration,
                minError,
                F,
                C_R
        );
        double[] solution = algorithm.run();
        System.out.println(Arrays.toString(solution));
    }

    /**
     * Parses the given architecture into an array of layer sizes.
     *
     * @param architecture The given architecture ("A1xA2x...xAn").
     * @return The parsed layer sizes.
     */
    private static int[] parseLayerSizes(String architecture) {
        String[] parts = architecture.split("x");
        if (parts.length < 2) return null;

        int[] layerSizes = new int[parts.length];
        try {
            for (int i = 0; i < layerSizes.length; i++) {
                layerSizes[i] = Integer.parseInt(parts[i]);
            }
        } catch (NumberFormatException ex) {
            return null;
        }

        return layerSizes;
    }
}
