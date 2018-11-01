package hr.fer.zemris.optjava.dz3;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz3.function.ErrorFunction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which parses contents of a file to an {@link ErrorFunction} instance.
 *
 * @author Mate Gašparini
 */
public class ErrorFunctionParser {

    /** Number of values in each row. */
    private static final int VALUE_COUNT = ErrorFunction.COEFFICIENT_COUNT + 1;

    /** Start of a ignored comments. */
    private static final String COMMENT_START = "#";

    /** Start of each equation. */
    private static final String EQUATION_START = "[";

    /** End of each equation. */
    private static final String EQUATION_END = "]";

    /** Regex used to split equation rows. */
    private static final String EQUATION_PARTS_REGEX = "\\s*,\\s*";

    /**
     * Parses the contents of the file specified by the given path and returns
     * the corresponding {@link ErrorFunction}.
     *
     * @param filePath The given path.
     * @return The corresponding {@link ErrorFunction}.
     * @throws IOException If an IO error occurs.
     */
    public ErrorFunction parse(Path filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(filePath)))) {
            List<double[]> values = new ArrayList<>();
            while (true) {
                String line = reader.readLine();

                if (line == null) break;
                if (line.startsWith(COMMENT_START)) continue;

                if (!line.startsWith(EQUATION_START) || !line.endsWith(EQUATION_END)) {
                    writeError();
                    continue;
                }

                line = line.substring(1, line.length() - 1);
                String[] parts = line.split(EQUATION_PARTS_REGEX);
                if (parts.length != VALUE_COUNT) {
                    writeError();
                    continue;
                }

                double[] equation = new double[VALUE_COUNT];
                try {
                    for (int i = 0; i < VALUE_COUNT; i ++) {
                        equation[i] = Double.parseDouble(parts[i]);
                    }
                } catch (NumberFormatException ex) {
                    writeError();
                    continue;
                }
                values.add(equation);
            }

            Matrix coefficients = new Matrix(values.size(), ErrorFunction.COEFFICIENT_COUNT);
            Matrix rightSide = new Matrix(values.size(), 1);

            for (int row = 0; row < values.size(); row ++) {
                double[] rowValues = values.get(row);
                int last = ErrorFunction.COEFFICIENT_COUNT;
                for (int col = 0; col < last; col ++) {
                    coefficients.set(row, col, rowValues[col]);
                }
                rightSide.set(row, 0, rowValues[last]);
            }

            return new ErrorFunction(coefficients, rightSide);
        }
    }

    /**
     * Writes an error message to stderr.
     */
    private void writeError() {
        System.err.println("Input file contains invalid line. Skipping.");
    }
}
