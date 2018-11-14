package hr.fer.zemris.optjava.dz5.part2.crossover;

import hr.fer.zemris.optjava.dz5.part2.solution.Solution;

import java.util.Random;

/**
 * Crossover operator which crosses two {@link Solution}s using the <i>OX1</i>
 * [Davis, 1985.] method.
 *
 * @author Mate Gašparini
 */
public class OrderCrossover {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /**
     * Crosses the given parent solutions and returns one random child solution
     * from a generated children pair.
     *
     * @param firstParent The first given parent.
     * @param secondParent The second given parent.
     * @return The resulting child.
     */
    public Solution cross(Solution firstParent, Solution secondParent) {
        int size = firstParent.values.length;
        int start = random.nextInt(size-1);
        int end = start + random.nextInt(size-start);

        int[] firstChild = new int[size];
        int[] secondChild = new int[size];
        copySegment(firstChild, firstParent.values, start, end);
        copySegment(secondChild, secondParent.values, start, end);
        if (start > 0 || end < size-1) {
            fillOrdered(firstChild, secondParent.values, end + 1);
            fillOrdered(secondChild, secondParent.values, end + 1);
        }

        return new Solution(random.nextDouble() < 0.5 ? firstChild : secondChild);
    }

    /**
     * Fills the given child array with all -1's, except in the specified
     * interval, where it fills it with the corresponding parent array elements.
     *
     * @param child The given child array.
     * @param parent The given parent array.
     * @param start The start of the specified interval (inclusive).
     * @param end The end of the specified interval (also inclusive).
     */
    private void copySegment(int[] child, int[] parent, int start, int end) {
        for (int i = 0; i < start; i ++) {
            child[i] = -1;
        }
        for (int i = start; i <= end; i ++) {
            child[i] = parent[i];
        }
        for (int i = end + 1; i < child.length; i ++) {
            child[i] = -1;
        }
    }

    /**
     * Fills the rest of the given child array using the given parent array and
     * starting at the given starting index.
     *
     * @param child The given child array.
     * @param parent The given parent array.
     * @param start The given starting index.
     */
    private void fillOrdered(int[] child, int[] parent, int start) {
        int childIndex = start % child.length;
        int parentIndex = childIndex;
        while (true) {
            int candidate = parent[parentIndex];
            if (!containsCandidate(
                    child,
                    candidate,
                    childIndex-1 < 0 ? child.length-1 : childIndex-1)) {
                child[childIndex] = candidate;
                childIndex ++;
                childIndex %= child.length;
                if (child[childIndex] != -1) break;
            }
            parentIndex ++;
            parentIndex %= parent.length;
        }
    }

    /**
     * Returns {@code true} if the given child array contains the given
     * candidate value.
     *
     * @param child The given child array.
     * @param candidate The given candidate value.
     * @param start Additional parameter used to improve performance. Marks the
     *              candidate (descending) search starting index.
     * @return {@code true} if the given child array contains the given
     *         candidate value, or {@code false} otherwise.
     */
    private boolean containsCandidate(int[] child, int candidate, int start) {
        int j = 0;
        for (int i = start; child[i] != -1; i --) {
            if (child[i] == candidate) return true;
            if (i == 0) i = child.length;
        }
        return false;
    }
}
