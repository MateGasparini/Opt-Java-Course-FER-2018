package hr.fer.zemris.optjava.dz5.part2.solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which encapsulates a {@code List} of {@link Solution}s.
 *
 * @author Mate Gašparini
 */
public class Population {

    /** Solutions of the population. */
    private List<Solution> solutions;

    /**
     * Constructor specifying the population and solution dimensions.
     *
     * @param size The specified size of the population.
     * @param solutionSize The specified size of the solution.
     */
    public Population(int size, int solutionSize) {
        solutions = new ArrayList<>(size);
        for (int i = 0; i < size; i ++) {
            solutions.add(new Solution(solutionSize));
        }
    }

    /**
     * Constructor specifying the {@code List} of {@link Solution}s.
     *
     * @param solutions The specified {@code List}.
     */
    public Population(List<Solution> solutions) {
        this.solutions = solutions;
    }

    /**
     * Returns the {@code List} of {@link Solution}s.
     *
     * @return The {@code List} of {@link Solution}s.
     */
    public List<Solution> getSolutions() {
        return solutions;
    }
}
