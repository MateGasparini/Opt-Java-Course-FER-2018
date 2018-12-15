package hr.fer.zemris.optjava.dz7.network.dataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>{@link IReadOnlyDataset} which is loaded from disk.</p>
 * <p>The file containing the data must contain lines of the following format:</p>
 * <p>{@code (x_1,x_2,...,x_n):(y_1,y_2,...,y_m)},</p>
 * <p>where {@code (x_1..x_n)} are the real values of the input vector and
 * {@code (y_1..y_n)} are the real values of the output vector.</p>
 *
 * @author Mate Gašparini
 */
public class LoadedDataSet implements IReadOnlyDataset {

    /** The input-output vector delimiter. */
    private static final String COLON = ":";

    /** Marks the start of a vector. */
    private static final String OPEN = "(";

    /** Marks the end of a vector. */
    private static final String CLOSED = ")";

    /** The vector value delimiter. */
    private static final String COMMA = ",";

    /** Contains all parsed inputs of each sample. */
    private double[][] inputs;

    /** Contains all parsed outputs of each sample. */
    private double[][] outputs;

    /**
     * Private constructor specifying the parsed inputs and outputs.
     *
     * @param inputs The parsed inputs of each sample.
     * @param outputs The parsed outputs of each sample.
     */
    private LoadedDataSet(double[][] inputs, double[][] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    /**
     * <p>Loads the dataset from the file specified by the given path.</p>
     * <p>Prints possible parsing errors to stderr.</p>
     *
     * @param path The given path.
     * @return The parsed dataset.
     * @throws IOException If an IO error occurs.
     */
    public static LoadedDataSet loadFromFile(Path path) throws IOException {
        List<List<Double>> inputList;
        List<List<Double>> outputList;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(path)))) {
            inputList = new ArrayList<>();
            outputList = new ArrayList<>();

            int counter = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                counter ++;

                String[] parts = line.split(COLON);
                if (parts.length != 2) {
                    System.err.println("Error at line: " + counter);
                    continue;
                }

                List<Double> input = parseVector(parts[0]);
                List<Double> output = parseVector(parts[1]);
                if (input == null || output == null) {
                    System.err.println("Error at line: " + counter);
                    continue;
                }

                inputList.add(input);
                outputList.add(output);
            }
        }

        return new LoadedDataSet(to2DArray(inputList), to2DArray(outputList));
    }

    @Override
    public int getSampleSize() {
        return inputs.length;
    }

    @Override
    public int getOutputSize() {
        return outputs[0].length;
    }

    @Override
    public double[] getInputsAt(int sampleIndex) {
        return inputs[sampleIndex];
    }

    @Override
    public double[] getOutputsAt(int sampleIndex) {
        return outputs[sampleIndex];
    }

    /**
     * <p>Parses the given string into a {@code List} of real values.</p>
     * <p>The expected format is: {@code "(x_1,x_2,...,x_n)"}.</p>
     * <p>If the format is invalid, {@code null} is returned.</p>
     *
     * @param vector The given string of the specified format.
     * @return The parsed {@code List} of values (or {@code null} if invalid).
     */
    private static List<Double> parseVector(String vector) {
        if (!vector.startsWith(OPEN) || !vector.endsWith(CLOSED)) {
            return null;
        }
        vector = vector.substring(1, vector.length() - 1);

        List<Double> values = new ArrayList<>();
        String[] parts = vector.split(COMMA);
        try {
            for (String part : parts) {
                values.add(Double.parseDouble(part));
            }
        } catch (NumberFormatException ex) {
            return null;
        }
        return values;
    }

    /**
     * <p>Converts the given {@code List} of {@code List}s to an array of arrays.</p>
     * <p>This is done because of the {@link IReadOnlyDataset} interface which
     * is used in neural network error function implementations.</p>
     *
     * @param list The given {@code List}.
     * @return The corresponding array.
     */
    private static double[][] to2DArray(List<List<Double>> list) {
        double[][] array = new double[list.size()][];
        for (int i = 0; i < array.length; i ++) {
            array[i] = toArray(list.get(i));
        }
        return array;
    }

    /**
     * Converts the given {@code List} of (boxed) real values to an array of
     * primitive {@code double}s.
     *
     * @param list The given {@code List}.
     * @return The corresponding array.
     */
    private static double[] toArray(List<Double> list) {
        double[] array = new double[list.size()];
        for (int i = 0; i < array.length; i ++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
