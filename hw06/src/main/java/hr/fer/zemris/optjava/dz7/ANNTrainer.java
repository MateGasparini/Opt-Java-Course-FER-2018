package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.dz7.algorithm.CLONALG;
import hr.fer.zemris.optjava.dz7.algorithm.GlobalPSO;
import hr.fer.zemris.optjava.dz7.algorithm.LocalPSO;
import hr.fer.zemris.optjava.dz7.algorithm.OptAlgorithm;
import hr.fer.zemris.optjava.dz7.function.ErrorFunction;
import hr.fer.zemris.optjava.dz7.function.Function;
import hr.fer.zemris.optjava.dz7.network.FFANN;
import hr.fer.zemris.optjava.dz7.network.FFANNEvaluator;
import hr.fer.zemris.optjava.dz7.network.dataset.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.network.dataset.LoadedDataSet;
import hr.fer.zemris.optjava.dz7.network.transfer.ITransferFunction;
import hr.fer.zemris.optjava.dz7.network.transfer.SigmoidTransferFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Program which tries to solve some specified classification problem with some
 * specified optimization algorithm and prints out the evaluation of the best
 * found solution.
 *
 * @author Mate Gašparini
 */
public class ANNTrainer {

    /** {@link GlobalPSO} label. */
    private static final String PSO_A = "pso-a";

    /** {@link LocalPSO} label. */
    private static final String PSO_B = "pso-b-";

    /** {@link CLONALG} label. */
    private static final String CLONALG = "clonalg";

    /** Sizes of the neural network layers. */
    private static final int[] LAYER_SIZES = new int[] {4, 5, 3, 3};

    /** Transfer functions used between each neural network layer. */
    private static final ITransferFunction[] TRANSFER_FUNCTIONS = new ITransferFunction[] {
            new SigmoidTransferFunction(),
            new SigmoidTransferFunction(),
            new SigmoidTransferFunction()
    };

    /** Maximum position value. */
    private static final double POSITION_MAX = 1.0;

    /** Maximum velocity value. */
    private static final double VELOCITY_MAX = 1.0;

    /** Factor of individuality. */
    private static final double C1 = 2.0;

    /** Factor of sociability. */
    private static final double C2 = 2.0;

    /** Factor used for calculating the number of clones. */
    private static final double BETA = 2;

    /** Percentage of the population to select for cloning. */
    private static final double SELECTION_PART = 0.5;

    /** Factor used for the hypermutation probability function. */
    private static final double RHO = 0.25;

    /** Percentage of the population size for the children population. */
    private static final double CHILDREN_PART = 0.5;

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (dataset filepath, algorithm label,
     *             population size, minimum error and maximum iteration).
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
        try {
            dataset = LoadedDataSet.loadFromFile(dataPath);
        } catch (IOException e) {
            System.err.println("A problem occurred while reading " + args[0] + ".");
            return;
        }

        FFANN network = new FFANN(LAYER_SIZES, TRANSFER_FUNCTIONS);
        Function function = new ErrorFunction(network, dataset);

        OptAlgorithm algorithm;
        if (args[1].equals(PSO_A)) {
            algorithm = new GlobalPSO(
                    function, populationSize, maxIteration, minError,
                    POSITION_MAX, VELOCITY_MAX, C1, C2
            );
        } else if (args[1].startsWith(PSO_B)) {
            String halfWidthText = args[1].substring(PSO_B.length());
            int halfWidth;
            try {
                halfWidth = Integer.parseInt(halfWidthText);
            } catch (NumberFormatException ex) {
                System.err.println(halfWidthText + " is not a valid half width.");
                return;
            }

            algorithm = new LocalPSO(
                    function, populationSize, maxIteration, minError, halfWidth,
                    POSITION_MAX, VELOCITY_MAX, C1, C2
            );
        } else if (args[1].equals(CLONALG)) {
            algorithm = new CLONALG(
                    function, populationSize, maxIteration, minError, BETA,
                    (int) (SELECTION_PART*populationSize), RHO, POSITION_MAX,
                    (int) (CHILDREN_PART*populationSize)
            );
        } else {
            System.err.println(args[1] + " is not a valid algorithm name.");
            return;
        }

        double[] solution = algorithm.run();
        new FFANNEvaluator(network, dataset, solution).evaluate(System.out);
    }
}
