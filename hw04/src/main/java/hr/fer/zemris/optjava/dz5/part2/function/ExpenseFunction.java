package hr.fer.zemris.optjava.dz5.part2.function;

import Jama.Matrix;

/**
 * <p>Models a function used to solve basic <i>Quadratic Assignment Problems</i>.</p>
 * <p>The mathematical function returns a sum of all expenses, hence the name.</p>
 * <p>It is defined using two quadratic matrices of same dimensions - one of
 * which represents distances between nodes, and the other which represents
 * costs associated with routes between nodes.</p>
 *
 * @author Mate Gašparini
 */
public class ExpenseFunction {

    /** The length of the matrices (and the solution). */
    private int solutionSize;

    /** Matrix which represents distances between nodes. */
    private Matrix distances;

    /** Matrix which represents costs associated with routes between nodes. */
    private Matrix costs;

    /**
     * Constructor specifying the distances and costs matrices.
     *
     * @param distances The specified distances matrix.
     * @param costs The specified costs matrix.
     */
    public ExpenseFunction(Matrix distances, Matrix costs) {
        this.solutionSize = distances.getRowDimension();
        this.distances = distances;
        this.costs = costs;
    }

    /**
     * Returns the size of the solution (length of the matrices).
     *
     * @return The size of the solution.
     */
    public int getSolutionSize() {
        return solutionSize;
    }

    /**
     * Returns the sum of all expenses for the given solution array.
     *
     * @param solution The given solution array.
     * @return The sum of all expenses.
     */
    public double getExpense(int[] solution) {
        double expense = 0.0;
        for (int i = 0; i < solution.length; i ++) {
            for (int j = 0; j < solution.length; j ++) {
                double singleExpense = costs.get(i, j);
                singleExpense *= distances.get(solution[i], solution[j]);
                expense += singleExpense;
            }
        }
        return expense;
    }
}
