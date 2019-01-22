package hr.fer.zemris.optjava.dz10.problem;

/**
 * Models a multiple-objective optimization problem.
 *
 * @author Mate Gašparini
 */
public interface MOOPProblem {

    /**
     * Returns the number of solution components (decision values).
     *
     * @return The number of decision values.
     */
    int getNumberOfSolutions();

    /**
     * Returns the number of problem objectives.
     *
     * @return The number of objectives.
     */
    int getNumberOfObjectives();

    /**
     * Evaluates the given decision values and stores the objective values into
     * the given objective values array (this allows the caller to use a single
     * array, as opposed to instantiating a new array each time and returning
     * it).
     *
     * @param solution The given decision values array.
     * @param objectives The given objectives values array.
     */
    void evaluateSolution(double[] solution, double[] objectives);

    /**
     * Returns the lower bounds of all decision values.
     *
     * @return The minimum x values.
     */
    double[] xMin();

    /**
     * Returns the upper bounds of all decision values.
     *
     * @return The maximum x values.
     */
    double[] xMax();

    /**
     * Returns the lower bounds of all objective values.
     *
     * @return The minimum f values.
     */
    double[] fMin();

    /**
     * Returns the upper bounds of all objective values.
     *
     * @return The maximum f values.
     */
    double[] fMax();
}
