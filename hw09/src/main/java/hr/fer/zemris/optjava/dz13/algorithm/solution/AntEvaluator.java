package hr.fer.zemris.optjava.dz13.algorithm.solution;

import hr.fer.zemris.optjava.dz13.AntSimulator;
import hr.fer.zemris.optjava.dz13.algorithm.solution.food.FoodMap;

import java.util.List;

/**
 * Class which is used to evaluate a population of solutions using the specified
 * {@link FoodMap}.
 *
 * @author Mate Gašparini
 */
public class AntEvaluator {

    /** Maximum allowed ant moves. */
    private static final int MAX_MOVES = 600;

    /** Used to simulate ant movement. */
    private AntSimulator simulator;

    /**
     * Constructor specifying the food map used by the {@link AntSimulator}.
     *
     * @param foodMap The specified food map.
     */
    public AntEvaluator(FoodMap foodMap) {
        simulator = new AntSimulator(foodMap);
    }

    /**
     * Simulates the ant movement and sets the score for each solution in the
     * given population.
     *
     * @param population The given population.
     */
    public void evaluatePopulation(List<TreeSolution> population) {
        for (TreeSolution solution : population) {
            simulator.reset();
            solution.resetScore();
            simulator.setSolution(solution);
            simulator.simulate(MAX_MOVES);
        }
    }
}
