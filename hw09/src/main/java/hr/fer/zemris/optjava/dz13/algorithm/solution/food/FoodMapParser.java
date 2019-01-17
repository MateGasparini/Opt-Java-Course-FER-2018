package hr.fer.zemris.optjava.dz13.algorithm.solution.food;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>Class which is used to parse a file containing an ant trail map into a
 * {@link FoodMap} object.</p>
 * <p>The input file's first line must contain the dimensions of the map.</p>
 *
 * @author Mate Gašparini
 */
public class FoodMapParser {

    /** Character which denotes a food cell. */
    private static final char FOOD = '1';

    /**
     * Parses the file specified by the given path into a {@link FoodMap}.
     *
     * @param path The given path.
     * @return The parsed {@link FoodMap}.
     * @throws IOException If an IO error occurs.
     */
    public FoodMap parse(Path path) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(path)))) {
            String line = reader.readLine();
            boolean[][] map = createEmptyMap(line.trim().split("x"));

            int row = 0;
            while (true) {
                line = reader.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.isEmpty()) continue;

                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i ++) {
                    if (chars[i] == FOOD) {
                        map[row][i] = true;
                    }
                }
                row ++;
            }
            return new FoodMap(map);
        }
    }

    private boolean[][] createEmptyMap(String[] dimensions) {
        int rows = Integer.parseInt(dimensions[0]);
        int cols = Integer.parseInt(dimensions[1]);
        return new boolean[rows][cols];
    }
}
