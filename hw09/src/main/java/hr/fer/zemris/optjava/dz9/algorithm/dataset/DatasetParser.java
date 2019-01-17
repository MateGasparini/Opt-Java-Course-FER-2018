package hr.fer.zemris.optjava.dz9.algorithm.dataset;

import hr.fer.zemris.optjava.dz9.nodes.NodeSupplier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class which can read a file containing a table of some function values and
 * convert it into a {@link Dataset} object.</p>
 * <p>The input file's first line must contain the variable and function
 * identifiers, and the remaining lines must contain their respective values
 * (separated by whitespace).</p>
 *
 * @author Mate Gašparini
 */
public class DatasetParser {

    /**
     * Parses the file specified by the given path and returns it as a
     * {@link Dataset}.
     *
     * @param path The given path.
     * @return The parsed {@link Dataset} object.
     * @throws IOException If an IO error occurs.
     */
    public Dataset parse(Path path) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(path)))) {
            String[] names = reader.readLine().trim().split("\\s+");
            NodeSupplier.allowVariableNodes(names);

            List<List<Double>> inputList = new ArrayList<>();
            List<Double> outputList = new ArrayList<>();

            while (true) {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) break;

                String[] parts = line.trim().split("\\s+");
                List<Double> currentInputs = new ArrayList<>();
                for (int i = 0; i < parts.length-1; i ++) {
                    currentInputs.add(Double.parseDouble(parts[i]));
                }
                inputList.add(currentInputs);
                outputList.add(Double.parseDouble(parts[parts.length-1]));
            }

            return new Dataset(toMatrix(inputList), toArray(outputList));
        }
    }

    /**
     * Converts the given {@code List} of {@code Double} {@code List}s into a
     * two-dimensional array of primitive {@code double}s.
     *
     * @param lists The given {@code List}.
     * @return The corresponding two-dimensional array.
     */
    private double[][] toMatrix(List<List<Double>> lists) {
        double[][] matrix = new double[lists.size()][];
        for (int i = 0; i < matrix.length; i ++) {
            matrix[i] = toArray(lists.get(i));
        }
        return matrix;
    }

    /**
     * Converts the given {@code List} of {@code Double}s into an array of
     * primitive {@code double}s.
     *
     * @param list The given {@code List}.
     * @return The corresponding array.
     */
    private double[] toArray(List<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < array.length; i ++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
