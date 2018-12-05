package hr.fer.zemris.optjava.dz6;

/**
 * <p>Class containing candidate (and non-candiate) lists for each node.</p>
 * <p>A node's candidates are its closest nodes.</p>
 *
 * @author Mate Gašparini
 */
public class Nodes {

    /** The "lists" of candidates for each node. */
    private int[][] candidates;

    /** The "lists" of non-scandidates for each node. */
    private int[][] nonCandidates;

    /** Size of each node's candidate list. */
    private int candidateCount;

    /**
     * Constructor specifying the distance matrix and the candidate count.
     *
     * @param distances The specified distance matrix.
     * @param candidateCount The specified size of each node's candidate list.
     */
    public Nodes(double[][] distances, int candidateCount) {
        this.candidateCount = candidateCount;
        int nonCandidateCount = distances.length - candidateCount - 1;

        candidates = new int[distances.length][candidateCount];
        nonCandidates = new int[distances.length][nonCandidateCount];

        fillArrays(distances);
    }

    /**
     * Returns the specified node's candidate "list" (actually, an array).
     *
     * @param node The specified node index.
     * @return The corresponding candidate list.
     */
    public int[] getCandidates(int node) {
        return candidates[node];
    }

    /**
     * Returns the specified node's non-candidate "list" (actually, an array).
     *
     * @param node The specified node index.
     * @return The corresponding non-candidate list.
     */
    public int[] getNonCandidates(int node) {
        return nonCandidates[node];
    }

    /**
     * Fills the candidate and non-candidate lists according to the distances
     * between the nodes.
     *
     * @param distances The given distance matrix.
     */
    private void fillArrays(double[][] distances) {
        for (int row = 0; row < distances.length; row ++) {
            int candidateCounter = 0;
            int nonCandidateCounter = 0;
            for (int col = 0; col < distances.length; col ++) {
                if (row == col) continue;

                if (candidateCounter != candidateCount) {
                    // Fill first candidateCount candidates in order.
                    candidates[row][candidateCounter] = col;
                    candidateCounter ++;
                } else {
                    // Replace the worst if found better.
                    double distance = distances[row][col];
                    boolean foundBetter = false;
                    for (int i = 0; i < candidateCount; i ++) {
                        double savedDistance = distances[row][candidates[row][i]];
                        if (distance < savedDistance) {
                            nonCandidates[row][nonCandidateCounter ++] = candidates[row][i];
                            candidates[row][i] = col;
                            foundBetter = true;
                            break;
                        }
                    }
                    if (!foundBetter) {
                        nonCandidates[row][nonCandidateCounter ++] = col;
                    }
                }
            }
        }
    }
}
