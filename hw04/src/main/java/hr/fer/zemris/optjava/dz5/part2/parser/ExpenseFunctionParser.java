package hr.fer.zemris.optjava.dz5.part2.parser;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz5.part2.function.ExpenseFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class which parses contents of a file to an {@link ExpenseFunction} instance.
 *
 * @author Mate Gašparini
 */
public class ExpenseFunctionParser {

    /** Regex used to split values by whitespaces. */
    private static final String SPACE_REGEX = "\\s+";

    /**
     * Parses the contents of the file specified by the given path and returns
     * the corresponding {@link ExpenseFunction}.
     *
     * @param path The given path.
     * @return The corresponding {@link ExpenseFunction}.
     * @throws IOException If an IO error occurs.
     */
    public ExpenseFunction parse(Path path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(path)))) {
            int length = Integer.parseInt(reader.readLine().trim());

            Matrix distances = new Matrix(length, length);
            fillMatrix(distances, reader);

            Matrix costs = new Matrix(length, length);
            fillMatrix(costs, reader);

            return new ExpenseFunction(distances, costs);
        }
    }

    /**
     * Fills the given matrix with the right number of values using the given
     * reader for input.
     *
     * @param matrix The given matrix.
     * @param reader The given reader.
     * @throws IOException If an IO error occurs.
     */
    private void fillMatrix(Matrix matrix, BufferedReader reader) throws IOException {
        int length = matrix.getRowDimension();
        int current = 0;

        while (current < length*length) {
            String line = reader.readLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(SPACE_REGEX);
            for (int i = 0; i < parts.length; i ++, current ++) {
                double value = Double.parseDouble(parts[i]);
                matrix.set(current / length, current % length, value);
            }
        }
    }
}
