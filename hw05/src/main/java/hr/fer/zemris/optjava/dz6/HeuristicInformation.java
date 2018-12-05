package hr.fer.zemris.optjava.dz6;

import static java.lang.Math.pow;

/**
 * Class containing a matrix of heuristic information (reciprocal distance
 * values to the power of some specified value <i>beta</i>) for some TSP.
 *
 * @author Mate Gašparini
 */
public class HeuristicInformation {

    /** Matrix containing the values. */
    private double[][] lists;

    /**
     * Constructor specifying the distance matrix and the <i>beta</i> value.
     *
     * @param distances The specified distance matrix.
     * @param beta The specified <i>beta</i> value.
     */
    public HeuristicInformation(double[][] distances, double beta) {
        this.lists = new double[distances.length][distances.length];
        fillLists(distances, beta);
    }

    /**
     * Returns the value at the specified position.
     *
     * @param row The specified row.
     * @param col The specified column.
     * @return The corresponding value from the internal matrix.
     */
    public double get(int row, int col) {
        return lists[row][col];
    }

    /**
     * Calculates all values (except the diagonal, as it does not make sense)
     * and stores them in the internal matrix.
     *
     * @param distances The specified distance matrix.
     * @param beta The specified <i>beta</i> value.
     */
    private void fillLists(double[][] distances, double beta) {
        for (int row = 0; row < distances.length; row ++) {
            for (int col = 0; col < distances.length; col ++) {
                if (row == col) continue;
                lists[row][col] = pow(1.0 / distances[row][col], beta);
            }
        }
    }
}
