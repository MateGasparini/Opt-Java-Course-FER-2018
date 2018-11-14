package hr.fer.zemris.optjava.dz5.part2.selection;

import hr.fer.zemris.optjava.dz5.part2.solution.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Class which is able to select a {@link Solution} in a fitness-based
 * tournament of {@code n} randomly chosen population {@link Solution}s.
 */
public class NTournamentSelection {

    /** Number of solutions participating in the tournament. */
    private int n;

    /** Helper {@code List} of tournament participating solutions. */
    private List<Solution> solutions;

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /**
     * Constructor specifying the number of solutions participating in the
     * tournament.
     *
     * @param n The specified parameter.
     */
    public NTournamentSelection(int n) {
        this.n = n;
        this.solutions = new ArrayList<>();
    }

    /**
     * Selects the best solution from {@code n} randomly chosen {@link Solution}s
     * from the given population and returns it.
     *
     * @param population The given population.
     * @return The selected solution.
     */
    public Solution getSolution(List<Solution> population) {
        for (int i = 0; i < n; i ++) {
            solutions.add(population.get(random.nextInt(population.size())));
        }

        solutions.sort(Comparator.reverseOrder());
        Solution bestSolution = solutions.get(0);
        solutions.clear();

        return bestSolution;
    }
}
