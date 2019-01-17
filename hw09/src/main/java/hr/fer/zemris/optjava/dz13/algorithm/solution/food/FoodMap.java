package hr.fer.zemris.optjava.dz13.algorithm.solution.food;

/**
 * Class which contains the ant trail map.
 *
 * @author Mate Gašparini
 */
public class FoodMap {

    /** The ant trail matrix. */
    private boolean[][] food;

    /**
     * Constructor specifying the ant trail matrix.
     *
     * @param food The specified food matrix.
     */
    public FoodMap(boolean[][] food) {
        this.food = food;
    }

    /**
     * Returns the row count of the map.
     *
     * @return The number of rows.
     */
    public int getRows() {
        return food.length;
    }

    /**
     * Returns the column count of the map.
     *
     * @return The number of columns.
     */
    public int getCols() {
        return food[0].length;
    }

    /**
     * Fills the given array with food values.
     *
     * @param array The given array.
     */
    public void fillFood(boolean[][] array) {
        for (int i = 0; i < food.length; i ++) {
            for (int j = 0; j < food[i].length; j ++) {
                array[i][j] = food[i][j];
            }
        }
    }
}
