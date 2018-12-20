package hr.fer.zemris.optjava.dz8.dataset;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents a dataset containing some time series values.</p>
 * <p>Creates the samples according to the specified window length and element
 * count.</p>
 *
 * @author Mate Gašparini
 */
public class LoadedDataSet implements IReadOnlyDataset {

    /** Minimum value after normalization. */
    private static final double MIN = -1.0;

    /** Maximum value after normalization. */
    private static final double MAX = 1.0;

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
     * <p>Loads the dataset from the specified file.</p>
     * <p>Calling this method is equivalent to calling
     * loadFromFile(path, 1, elementCount).</p>
     *
     * @param path The specified file.
     * @param elementCount The specified number of samples.
     * @return The loaded dataset.
     * @throws IOException If an I/O error occurs.
     */
    public static LoadedDataSet loadFromFile(Path path, int elementCount)
            throws IOException {
        return loadFromFile(path, 1, elementCount);
    }

    /**
     * <p>Loads the dataset from the specified file.</p>
     *
     * @param path The specified file.
     * @param windowLength The specified number of inputs for each sample.
     * @param elementCount The specified number of samples (if invalid, it is
     *                     set to max possible).
     * @return The loaded dataset.
     * @throws IOException If an I/O error occurs.
     */
    public static LoadedDataSet loadFromFile(Path path, int windowLength,
                                             int elementCount) throws IOException {
        double[] elements = parseAndNormalize(readLines(path), elementCount);

        int sampleCount = elements.length - windowLength;
        double[][] inputs = new double[sampleCount][windowLength];
        double[][] outputs = new double[sampleCount][1];
        for (int sample = 0; sample < sampleCount; sample ++) {
            for (int i = 0; i < windowLength; i ++) {
                inputs[sample][i] = elements[sample + i];
            }
            outputs[sample][0] = elements[sample + windowLength];
        }

        return new LoadedDataSet(inputs, outputs);
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
     * Returns all (trimmed) lines from the file specified by the given path.
     *
     * @param path The given path.
     * @return All trimmed lines from the file.
     * @throws IOException If an I/O error occurs.
     */
    private static List<String> readLines(Path path) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(path)))) {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                lines.add(line.trim());
            }
        }
        return lines;
    }

    /**
     * Parses the given lines into real values, normalizes them according to
     * {@code MIN} and {@code MAX} values and returns them in an array which
     * contains only the first {@code elementCount} values.
     *
     * @param lines The given lines parsable into real values.
     * @param elementCount The size of the return array.
     * @return The parsed values.
     * @throws NumberFormatException If a value is not parsable.
     */
    private static double[] parseAndNormalize(List<String> lines, int elementCount)
            throws NumberFormatException {
        double[] elements = new double[elementCount > 0 ? elementCount : lines.size()];

        elements[0] = Double.parseDouble(lines.get(0));
        double min = elements[0];
        double max = elements[0];

        for (int i = 1; i < lines.size(); i ++) {
            double value = Double.parseDouble(lines.get(i));
            if (i < elementCount) elements[i] = value;
            if (value < min) min = value;
            if (value > max) max = value;
        }

        for (int i = 0; i < elements.length; i ++) {
            elements[i] = MIN + (elements[i] - min) * (MAX - MIN) / (max - min);
        }

        return elements;
    }
}
