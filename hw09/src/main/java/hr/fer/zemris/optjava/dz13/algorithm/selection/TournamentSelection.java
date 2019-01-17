package hr.fer.zemris.optjava.dz13.algorithm.selection;

import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Selection in which a solution is selected in a fitness-based tournament of
 * {@code n} randomly chosen solutions from the population.
 *
 * @author Mate Gašparini
 */
public class TournamentSelection implements Selection<TreeSolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** Number of solutions participating in the tournament. */
    private int n;

    /** Helper {@code List} of tournament participating solutions. */
    private List<TreeSolution> solutions;

    /**
     * Constructor specifying the number of solutions participating in the
     * tournament.
     *
     * @param n The specified parameter.
     */
    public TournamentSelection(int n) {
        this.n = n;
        solutions = new ArrayList<>(n);
    }

    @Override
    public TreeSolution select(List<TreeSolution> population) {
        while (solutions.size() < n) {
            TreeSolution solution = population.get(random.nextInt(population.size()));
            if (!solutions.contains(solution)) solutions.add(solution);
        }

        solutions.sort(Comparator.reverseOrder());
        TreeSolution bestSolution = solutions.get(0);
        solutions.clear();

        return bestSolution;
    }
}
