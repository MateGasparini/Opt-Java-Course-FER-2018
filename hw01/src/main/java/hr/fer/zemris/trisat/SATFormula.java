package hr.fer.zemris.trisat;

import java.util.Objects;

/**
 * <p>Represents a boolean algebra formula made up from multiple multiplied
 * (logical AND operation) {@link Clause}s.</p>
 * <p>It is specified for some number of boolean algebra variables.</p>
 *
 * @author Mate Gašparini
 */
public class SATFormula {

    /** Number of defined boolean algebra variables. */
    private int numberOfVariables;

    /** Internal array of {@link Clause}s. */
    private Clause[] clauses;

    /**
     * Constructor specifying the number of defined boolean algebra variables
     * and the array of {@link Clause}s.
     *
     * @param numberOfVariables The specified number of defined boolean algebra
     *                          variables.
     * @param clauses The specified array of {@link Clause}s.
     * @throws IllegalArgumentException If the specified number of defined
     *         boolean algebra variables is not positive.
     * @throws NullPointerException If the specified array of {@link Clause}s is
     *         {@code null}.
     */
    public SATFormula(int numberOfVariables, Clause[] clauses) {
        if (numberOfVariables <= 0) {
            throw new IllegalArgumentException("Number of variables must be positive.");
        }
        Objects.requireNonNull(clauses, "Clauses cannot be null.");

        this.numberOfVariables = numberOfVariables;
        this.clauses = clauses;
    }

    /**
     * Returns the number of defined boolean algebra variables.
     *
     * @return The number of defined boolean algebra variables.
     */
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    /**
     * Returns the length of the internal array of {@link Clause}s.
     *
     * @return The number of {@link Clause}s.
     */
    public int getNumberOfClauses() {
        return clauses.length;
    }

    /**
     * Returns the {@link Clause} specified by the given index.
     *
     * @param index The given index.
     * @return The corresponding {@link Clause}.
     */
    public Clause getClause(int index) {
        return clauses[index];
    }

    /**
     * Returns {@code true} if all stored {@link Clause}s are satisfied for the
     * given assignment.
     *
     * @param assignment The given assignment.
     * @return {@code true} if the formula is satisfied for the given assignment,
     *         or {@code false} otherwise.
     * @throws IllegalArgumentException If the size of the given assignment is
     *         not equal to the number of defined boolean algebra variables.
     */
    public boolean isSatisfied(BitVector assignment) {
        if (numberOfVariables != assignment.getSize()) {
            throw new IllegalArgumentException(
                    "Assignment and formula must be of same size."
            );
        }

        for (Clause clause : clauses) {
            if (!clause.isSatisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Clause clause : clauses) {
            builder.append('(');
            builder.append(clause);
            builder.append(')');
        }
        return builder.toString();
    }
}
