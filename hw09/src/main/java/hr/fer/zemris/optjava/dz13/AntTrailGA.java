package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.algorithm.AntTrailGP;
import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;
import hr.fer.zemris.optjava.dz13.algorithm.solution.food.FoodMap;
import hr.fer.zemris.optjava.dz13.algorithm.solution.food.FoodMapParser;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Program which accepts 5 command line arguments, tries to solve the specified
 * Santa Fe ant trail problem and opens a {@link AntTrailFrame} in which the
 * best found ant's steps can be simulated.
 *
 * @author Mate Gašparini
 */
public class AntTrailGA {

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (food map path, max generation,
     *             population size, stop score and result file path).
     */
    public static void main(String[] args) {
        if (args.length != 5) {
            System.err.println("Expected 5 arguments.");
            return;
        }

        Path mapPath = Paths.get(args[0]);
        if (!Files.isReadable(mapPath)) {
            System.err.println(mapPath + " does not exist.");
            return;
        }
        FoodMap foodMap;
        try {
            foodMap = new FoodMapParser().parse(mapPath);
        } catch (Exception ex) {
            System.err.println(mapPath + " contains invalid data.");
            return;
        }

        int maxGeneration;
        try {
            maxGeneration = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            System.err.println(args[1] + " is not a valid max generation.");
            return;
        }

        int populationSize;
        try {
            populationSize = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            System.err.println(args[2] + " is not a valid population size.");
            return;
        }

        int stopScore;
        try {
            stopScore = Integer.parseInt(args[3]);
        } catch (NumberFormatException ex) {
            System.err.println(args[3] + " is not a valid stop score.");
            return;
        }

        AntTrailGP algorithm = new AntTrailGP(
                populationSize, maxGeneration, stopScore, foodMap);
        TreeSolution best = algorithm.run();
        saveToFile(best, Paths.get(args[4]));

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new AntTrailFrame(best, foodMap);
            frame.setVisible(true);
        });
    }

    private static void saveToFile(TreeSolution solution, Path path) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(path)))) {
            System.out.println("Writing result to " + path + ".");
            writer.write(solution.getRoot().toString());
        } catch (IOException e) {
            System.err.println("Could not write the result to " + path
                    + ". Printing to stdout instead.");
            System.out.println(solution.getRoot());
        }
    }
}
