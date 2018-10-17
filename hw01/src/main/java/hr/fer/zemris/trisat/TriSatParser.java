package hr.fer.zemris.trisat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>Provides functionality for parsing a file to a {@link SATFormula}.</p>
 * <p>The format of the file is defined in the homework PDF.</p>
 *
 * @author Mate Gašparini
 */
public class TriSatParser {

    /** Marks the start of a comment line. */
    private static final String COMMENT_START = "c";

    /** Marks the end of formula definition. */
    private static final String DEFINITION_END = "%";

    /** Regex used for clause splitting. */
    private static final String CLAUSE_REGEX = "\\s+";

    /** Specified path to the file containing the formula definition. */
    private Path filePath;

    /**
     * Constructor specifying the path to the file containing the formula
     * definition.
     *
     * @param filePath The specified file path.
     */
    public TriSatParser(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Tries to parse the specified file to a {@link SATFormula} representation.
     *
     * @return The corresponding {@link SATFormula}.
     * @throws IOException If an I/O error occurs.
     */
    public SATFormula parse() throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(filePath)))) {
            Clause[] clauses = null;
            int numberOfVariables = 0;
            int clauseCounter = 0;
            boolean firstLineRead = false;

            while (true) {
                String line = reader.readLine();
                if (line.startsWith(DEFINITION_END)) {
                    break;
                } else if (!line.startsWith(COMMENT_START)) {
                    String[] parts = line.trim().split(CLAUSE_REGEX);
                    if (firstLineRead) {
                        int size = parts.length - 1; // Without the trailing 0.
                        int[] indexes = new int[size];
                        for (int i = 0; i < size; i ++) {
                            indexes[i] = Integer.parseInt(parts[i]);
                        }
                        clauses[clauseCounter ++] = new Clause(indexes);
                    } else {
                        firstLineRead = true;
                        numberOfVariables = Integer.parseInt(parts[2]);
                        clauses = new Clause[Integer.parseInt(parts[3])];
                    }
                }
            }

            return new SATFormula(numberOfVariables, clauses);
        }
    }
}
