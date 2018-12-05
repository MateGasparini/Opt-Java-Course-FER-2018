package hr.fer.zemris.optjava.dz6;

/**
 * Class which is responsible for handling all pheromone values for some TSP
 * graph.
 *
 * @author Mate Gašparini
 */
public class Pheromones {

    /** The rate at which all pheromones (in each iteration) evaporate. */
    private double evaporationRate;

    /** Ratio between {@code tauMin} and {@code tauMax}. */
    private double a;

    /** Matrix of all current pheromone values. */
    private double[][] values;

    /** Maximum allowed pheromone value. */
    private double tauMax;

    /** Minimum allowed pheromone value. */
    private double tauMin;

    /**
     * Constructor specifying all needed initialization information.
     *
     * @param size The specified number of nodes.
     * @param evaporationRate The specified evaporation rate.
     * @param a The specified ratio between minimum and maximum pheromone value.
     * @param tau0 The specified starting maximum allowed pheromone value.
     */
    public Pheromones(int size, double evaporationRate, double a, double tau0) {
        this.evaporationRate = evaporationRate;
        this.a = a;
        setTauMax(tau0);
        this.values = new double[size][size];
        resetValues();
    }

    /**
     * <p>Returns the pheromone value at the specified vertex from the TSP
     * graph.</p>
     * <p>Note that if the parameters change place, the returned value will be
     * the same.</p>
     *
     * @param from First node of the vertex.
     * @param to Second node of the vertex.
     * @return The corresponding pheromone value.
     */
    public double get(int from, int to) {
        return values[from][to];
    }

    /**
     * Sets the maximum pheromone value to the given value, and calculates the
     * minimum pheromone value (using the specified ratio <i>a</i>).
     *
     * @param tauMax The given value.
     */
    public void setTauMax(double tauMax) {
        this.tauMax = tauMax;
        this.tauMin = tauMax / a;
    }

    /**
     * Evaporates all pheromones using the specified evaporation rate (and does
     * not allow the values to drop below the current minimum pheromone value).
     */
    public void evaporate() {
        for (int row = 0; row < values.length; row ++) {
            for (int col = 0; col < values.length; col ++) {
                values[row][col] *= (1 - evaporationRate);
                if (values[row][col] < tauMin) values[row][col] = tauMin;
            }
        }
    }

    /**
     * Increases the pheromone values for the specified amount for each vertex
     * from the given path.
     *
     * @param pathNodes The given path.
     * @param delta The specified amount.
     */
    public void update(int[] pathNodes, double delta) {
        for (int i = 0; i < pathNodes.length - 1; i ++) {
            int from = pathNodes[i];
            int to = pathNodes[i+1];
            values[from][to] += delta;
            if (values[from][to] > tauMax) values[from][to] = tauMax;
            values[to][from] = values[from][to];
        }
    }

    /**
     * Sets all pheromone values to the current maximum pheromone value.
     */
    public void resetValues() {
        for (int row = 0; row < values.length; row ++) {
            for (int col = 0; col < values.length; col ++) {
                values[row][col] = tauMax;
            }
        }
    }
}
