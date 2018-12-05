package hr.fer.zemris.optjava.dz6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Class used for parsing files containing traveling salesman problem (TSP)
 * definitions.
 *
 * @author Mate Gašparini
 */
public class TSPParser {

    /** Label which marks the line containing the number of nodes. */
    private static final String DIMENSION_LABEL = "DIMENSION";

    /** Labels which mark the start of the node coordinate list. */
    private static final List<String> START_LABELS = List.of(
            "NODE_COORD_SECTION", "DISPLAY_DATA_SECTION"
    );

    /** Label which marks the end of data relevant for the specified TSP. */
    private static final String EOF = "EOF";

    /** The path specifying the file containing the TSP definition. */
    private Path path;

    /**
     * Constructor specifying the TSP definition file path.
     *
     * @param path The specified file path.
     */
    public TSPParser(Path path) {
        this.path = path;
    }

    /**
     * Parses the specified file and returns the calculated distance matrix
     * between the nodes.
     *
     * @return The distance matrix.
     * @throws IOException If an IO error occurs.
     */
    public double[][] getDistances() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(path)))) {
            double[][] points = new double[parseDimension(reader)][2];
            findStart(reader);
            parsePoints(reader, points);
            return calculateDistances(points);
        }
    }

    /**
     * Consumes input lines from the given reader until {@code DIMENSION_LABEL}
     * is reached and returns the parsed dimension.
     *
     * @param reader The given reader.
     * @return The number of nodes used for the TSP.
     * @throws IOException If an IO error occurs.
     * @throws IllegalArgumentException If {@code DIMENSION_LABEL} could not be
     *         found, or if the dimension could not be parsed.
     */
    private int parseDimension(BufferedReader reader) throws IOException {
        while (true) {
            String line = reader.readLine();
            if (line == null || line.equals(EOF)) break;
            if (line.startsWith(DIMENSION_LABEL)) {
                line = line.substring(DIMENSION_LABEL.length());
                line = line.split("\\s*:\\s*")[1];
                try {
                    return Integer.parseInt(line);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException(
                            line + " is not a valid dimension");
                }
            }
        }
        throw new IllegalArgumentException("Could not find the "
                + DIMENSION_LABEL + " line.");
    }

    /**
     * Consumes input lines from the given reader until some of the
     * {@code START_LABELS} is reached.
     *
     * @param reader The given reader.
     * @throws IOException If an IO error occurs.
     * @throws IllegalArgumentException If none of the {@code START_LABELS}
     *         could be found.
     */
    private void findStart(BufferedReader reader) throws IOException {
        while (true) {
            String line = reader.readLine();
            if (line == null || line.equals(EOF)) break;
            if (START_LABELS.contains(line)) {
                return;
            }
        }
        throw new IllegalArgumentException("Could not find some of the "
                + START_LABELS + " lines.");
    }

    /**
     * Parses the lines from the given reader containing the coordinates of all
     * points (nodes) and stores them in the given points array.
     *
     * @param reader The given reader.
     * @param points The given points array.
     * @throws IOException If an IO error occurs.
     * @throws IllegalArgumentException If there are coordinates missing, or if
     *         some coordinate could not be parsed.
     */
    private void parsePoints(BufferedReader reader, double[][] points) throws IOException {
        for (int i = 0; i < points.length; i ++) {
            String line = reader.readLine();
            if (line == null || line.equals(EOF)) {
                throw new IllegalArgumentException("Expected more node coordinate entries.");
            }

            String[] parts = line.trim().split("\\s+");
            try {
                points[i][0] = Double.parseDouble(parts[1]);
                points[i][1] = Double.parseDouble(parts[2]);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Parsing error at " + i
                        + "th line of node coordinate entries.");
            }
        }
    }

    /**
     * <p>Calculates the distance matrix for the given points array.</p>
     * <p>The matrix will be a square matrix with all zeros on the diagonal.</p>
     *
     * @param points The given points array.
     * @return The calculated distance matrix.
     */
    private double[][] calculateDistances(double[][] points) {
        double[][] distances = new double[points.length][points.length];
        for (int i = 0; i < points.length; i ++) {
            for (int j = 0; j < points.length; j ++) {
                distances[i][j] = distance(
                        points[i][0], points[i][1], points[j][0], points[j][1]
                );
            }
        }
        return distances;
    }

    /**
     * Calculates the Euclidean distance between the two specified points.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     * @return The calculated distance.
     */
    private double distance(double x1, double y1, double x2, double y2) {
        double xDelta = x2 - x1;
        double yDelta = y2 - y1;
        return Math.sqrt(xDelta*xDelta + yDelta*yDelta);
    }
}
