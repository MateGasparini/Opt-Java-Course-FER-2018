package hr.fer.zemris.trisat;

import java.util.Objects;

/**
 * <p>Represents a sum (logical OR operation) of some boolean algebra variables.</p>
 * <p>It stores an array of indexes which correspond to distinct variables.</p>
 * <p>If some index is negative, the complement of the corresponding variable is
 * used instead.</p>
 *
 * @author Mate Gašparini
 */
public class Clause {

    /** Internal array of variable indexes. */
    private int[] indexes;

    /**
     * Constructor specifying the array of variable indexes.
     *
     * @param indexes The specified array of variable indexes.
     * @throws NullPointerException If the given array is {@code null}.
     */
    public Clause(int[] indexes) {
        this.indexes = Objects.requireNonNull(indexes, "Indexes cannot be null.");
    }

    /**
     * Returns the length of the internal array of variable indexes.
     *
     * @return The size of the clause.
     */
    public int getSize() {
        return indexes.length;
    }

    /**
     * Returns the value at the given index.
     *
     * @param index The given index.
     * @return The corresponding value (some stored variable index).
     */
    public int getLiteral(int index) {
        return indexes[index];
    }

    /**
     * Returns {@code true} if at least one index in combination with the
     * corresponding bit from the given assignment evaluates to {@code true}.
     *
     * @param assignment The given assignment.
     * @return {@code true} if the clause is satisfied for the given assignment,
     *         or {@code false} otherwise.
     */
    public boolean isSatisfied(BitVector assignment) {
        for (int index : indexes) {
            if (index < 0 && !assignment.get(-index-1)
                    || index > 0 && assignment.get(index-1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < indexes.length; i ++) {
            int index = indexes[i];

            if (i != 0) {
                builder.append('+');
            }

            if (index < 0) {
                builder.append('~');
                index = -index;
            }

            builder.append('x').append(index);
        }
        return builder.toString();
    }
}
