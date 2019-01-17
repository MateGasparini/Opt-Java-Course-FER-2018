package hr.fer.zemris.optjava.dz9.algorithm;

import hr.fer.zemris.optjava.dz9.algorithm.crossover.SubTreeCrossover;
import hr.fer.zemris.optjava.dz9.algorithm.dataset.Dataset;
import hr.fer.zemris.optjava.dz9.algorithm.initialization.RampedHalfAndHalf;
import hr.fer.zemris.optjava.dz9.algorithm.mutation.Reproduction;
import hr.fer.zemris.optjava.dz9.algorithm.mutation.SubTreeMutation;
import hr.fer.zemris.optjava.dz9.algorithm.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz9.nodes.NodeSupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class which is used to parse a configuration file into a {@link SymbolicGP}
 * algorithm instance.
 *
 * @author Mate Gašparini
 */
public class ConfigParser {

    private static final String COMMA_REGEX = "\\s*,\\s*";

    private static final String FUNCTION_NODES = "FunctionNodes:";

    private static final String CONSTANT_RANGES = "ConstantRange:";

    private static final String POPULATION_SIZE = "PopulationSize:";

    private static final String TOURNAMENT_SIZE = "TournamentSize:";

    private static final String COST_EVALUATIONS = "CostEvaluations:";

    private static final String MUTATION_PROBABILITY = "MutationProbability:";

    private static final String MAX_DEPTH = "MaxTreeDepth:";

    private static final String MAX_NODES = "MaxTreeNodes:";

    private int populationSize;

    private int tournamentSize = 7;

    private int maxGeneration;

    private double mutationProbability = 0.14;

    private int maxDepth = 7;

    private int maxNodes = 200;

    /**
     * Parses the file specified by the given path into a {@link SymbolicGP}
     * object.
     *
     * @param path The given path.
     * @param dataset The given dataset needed to construct the algorithm.
     * @return The constructed algorithm.
     * @throws IOException If an IO error occurs.
     */
    public SymbolicGP parse(Path path, Dataset dataset) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(path)))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (line.isEmpty()) continue;
                parseLine(line);
            }

            return new SymbolicGP(populationSize, maxGeneration, dataset,
                    new RampedHalfAndHalf(maxDepth, maxNodes),
                    new TournamentSelection(tournamentSize),
                    new SubTreeCrossover(maxDepth, maxNodes),
                    new SubTreeMutation(maxDepth, maxNodes),
                    new Reproduction(), mutationProbability
            );
        }
    }

    private void parseLine(String line) {
        if (line.startsWith(FUNCTION_NODES)) {
            line = line.substring(FUNCTION_NODES.length());
            String[] functionNodeNames = line.split(COMMA_REGEX);
            NodeSupplier.allowFunctionNodes(functionNodeNames);
        } else if (line.startsWith(CONSTANT_RANGES)) {
            line = line.substring(CONSTANT_RANGES.length());
            String[] parts = line.split(COMMA_REGEX);
            try {
                double min = Double.parseDouble(parts[0]);
                double max = Double.parseDouble(parts[1]);
                NodeSupplier.allowConstantNodes(min, max);
            } catch (NumberFormatException ignorable) {}
        } else if (line.startsWith(POPULATION_SIZE)) {
            populationSize = Integer.parseInt(
                    line.substring(POPULATION_SIZE.length()).trim());
        } else if (line.startsWith(TOURNAMENT_SIZE)) {
            tournamentSize = Integer.parseInt(
                    line.substring(TOURNAMENT_SIZE.length()).trim());
        } else if (line.startsWith(COST_EVALUATIONS)) {
            maxGeneration = Integer.parseInt(
                    line.substring(COST_EVALUATIONS.length()).trim());
        } else if (line.startsWith(MUTATION_PROBABILITY)) {
            mutationProbability = Double.parseDouble(
                    line.substring(MUTATION_PROBABILITY.length()).trim());
        } else if (line.startsWith(MAX_DEPTH)) {
            maxDepth = Integer.parseInt(
                    line.substring(MAX_DEPTH.length()).trim());
        } else if (line.startsWith(MAX_NODES)) {
            maxNodes = Integer.parseInt(
                    line.substring(MAX_NODES.length()).trim());
        } else {
            System.out.println("Skipping: " + line);
        }
    }
}
