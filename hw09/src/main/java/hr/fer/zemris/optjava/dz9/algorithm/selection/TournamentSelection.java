package hr.fer.zemris.optjava.dz9.algorithm.selection;

import hr.fer.zemris.optjava.dz9.algorithm.solution.SymbolicTreeSolution;

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
public class TournamentSelection implements Selection<SymbolicTreeSolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** Number of solutions participating in the tournament. */
    private int n;

    /** Helper {@code List} of tournament participating solutions. */
    private List<SymbolicTreeSolution> solutions;

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
    public SymbolicTreeSolution select(List<SymbolicTreeSolution> population) {
        while (solutions.size() < n) {
            SymbolicTreeSolution solution = population.get(random.nextInt(population.size()));
            if (!solutions.contains(solution)) solutions.add(solution);
        }

        solutions.sort(Comparator.reverseOrder());
        SymbolicTreeSolution bestSolution = solutions.get(0);
        solutions.clear();

        return bestSolution;
    }
}
