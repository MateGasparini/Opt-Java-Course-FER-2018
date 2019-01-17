package hr.fer.zemris.optjava.dz9;

import hr.fer.zemris.optjava.dz9.algorithm.ConfigParser;
import hr.fer.zemris.optjava.dz9.algorithm.SymbolicGP;
import hr.fer.zemris.optjava.dz9.algorithm.dataset.Dataset;
import hr.fer.zemris.optjava.dz9.algorithm.dataset.DatasetParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>Command line program which accepts 2 arguments - the path to the dataset
 * and the path to the configuration file.</p>
 * <p>It tries to solve the specified symbolic regression problem. In other
 * words, it tries to figure out the mathematical expression which was used to
 * generate the dataset.</p>
 *
 * @author Mate Gašparini
 */
public class SymbolicRegression {

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments - dataset path and configuration path.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Expected 2 arguments - dataset path and config path.");
            return;
        }

        Path datasetPath = Paths.get(args[1]);
        if (!Files.isReadable(datasetPath)) {
            System.err.println(datasetPath + " does not exist.");
            return;
        }
        Dataset dataset;
        try {
            dataset = new DatasetParser().parse(datasetPath);
        } catch (Exception ex) {
            System.err.println("Dataset " + datasetPath + " contains invalid data.");
            return;
        }

        Path configPath = Paths.get(args[0]);
        if (!Files.isReadable(configPath)) {
            System.err.println(configPath + " does not exist.");
            return;
        }
        SymbolicGP algorithm;
        try {
            algorithm = new ConfigParser().parse(configPath, dataset);
        } catch (Exception ex) {
            System.err.println("Config " + configPath + " contains invalid data.");
            return;
        }
        algorithm.run();
    }
}
