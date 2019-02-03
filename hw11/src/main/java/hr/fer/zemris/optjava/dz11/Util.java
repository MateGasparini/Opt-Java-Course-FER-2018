package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.EvaluatorProvider;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.dz11.ga.GeneticAlgorithm;
import hr.fer.zemris.optjava.dz11.ga.GeneticAlgorithm1;
import hr.fer.zemris.optjava.dz11.ga.GeneticAlgorithm2;
import hr.fer.zemris.optjava.rng.EVOThread;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class containing command line program shared code.
 *
 * @author Mate Gašparini
 */
public final class Util {

    /**
     * Default private constructor.
     */
    private Util() {
    }

    /**
     * Creates a {@link GeneticAlgorithm1} from the given arguments.
     *
     * @param args The given (command line) arguments.
     * @return The created genetic algorithm.
     */
    public static GeneticAlgorithm createAlgorithm1(String[] args) {
        return createAlgorithm(args, true);
    }

    /**
     * Creates a {@link GeneticAlgorithm2} from the given arguments.
     *
     * @param args The given (command line) arguments.
     * @return The created genetic algorithm.
     */
    public static GeneticAlgorithm createAlgorithm2(String[] args) {
        return createAlgorithm(args, false);
    }

    /**
     * Runs the given algorithm (if not {@code null}) in a new {@link EVOThread}
     * and saves the best found solution's parameters and image.
     *
     * @param args The given (command line) arguments.
     * @param algorithm The given algorithm.
     */
    public static void runAlgorithm(String[] args, GeneticAlgorithm algorithm) {
        if (algorithm == null) return;

        new EVOThread(() -> {
            GASolution<int[]> solution = algorithm.run();
            Util.saveSolution(solution, Paths.get(args[5]), Paths.get(args[6]));
        }).start();
    }

    /**
     * Saves the given solution's parameters and image.
     *
     * @param solution The given solution.
     * @param paramsPath The specified parameters file path.
     * @param imagePath The specified image file path.
     */
    private static void saveSolution(GASolution<int[]> solution,
            Path paramsPath, Path imagePath) {
        saveParams(solution, paramsPath);
        saveImage(solution, imagePath);
    }

    /**
     * Parses the given arguments and constructs the corresponding genetic
     * algorithm.
     *
     * @param args The given (command line) arguments.
     * @param first If {@code true}, {@link GeneticAlgorithm1} is constructed.
     * @return The constructed genetic algorithm.
     */
    private static GeneticAlgorithm createAlgorithm(String[] args, boolean first) {
        if (args.length != 7) {
            System.err.println("Expected 7 arguments.");
            return null;
        }

        Path templatePath = Paths.get(args[0]);
        if (!Files.isReadable(templatePath)) {
            System.err.println(args[0] + " is not a valid image path.");
            return null;
        }

        int rectangleCount;
        try {
            rectangleCount = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            System.err.println(args[1] + " is not a valid rectangle count.");
            return null;
        }

        int populationSize;
        try {
            populationSize = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            System.err.println(args[2] + " is not a valid population size.");
            return null;
        }

        int maxIteration;
        try {
            maxIteration = Integer.parseInt(args[3]);
        } catch (NumberFormatException ex) {
            System.err.println(args[3] + " is not a valid maximum iteration.");
            return null;
        }

        double maxFitness;
        try {
            maxFitness = Double.parseDouble(args[4]);
        } catch (NumberFormatException ex) {
            System.err.println(args[4] + " is not a valid maximum fitness.");
            return null;
        }

        try {
            return first ?
                    new GeneticAlgorithm1(templatePath, rectangleCount,
                            populationSize, maxIteration, maxFitness)
                    : new GeneticAlgorithm2(templatePath, rectangleCount,
                            populationSize, maxIteration, maxFitness);
        } catch (IOException e) {
            System.err.println(templatePath + " could not be read.");
            return null;
        }
    }

    /**
     * Saves the given solution's parameters to the given file path.
     *
     * @param solution The given solution.
     * @param path The given file path.
     */
    private static void saveParams(GASolution<int[]> solution, Path path) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(Files.newOutputStream(path)))) {
            for (int value : solution.getData()) {
                writer.write(value + "\n");
            }
        } catch (IOException ex) {
            System.err.println("Could not save params to " + path);
        }
    }

    /**
     * Generates an image from the given solution and saves it to the given file
     * path.
     *
     * @param solution The given solution.
     * @param path The given file path.
     */
    private static void saveImage(GASolution<int[]> solution, Path path) {
        Evaluator evaluator = EvaluatorProvider.getEvaluator();
        GrayScaleImage image = evaluator.draw(solution, null);
        try {
            image.save(path.toFile());
        } catch (IOException e) {
            System.err.println("Could not save image to " + path);
        }
    }
}
