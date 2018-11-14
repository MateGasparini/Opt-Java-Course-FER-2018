package hr.fer.zemris.optjava.dz4.part1.selection;

import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * {@link ISelection} in which a solution is selected in a fitness-based
 * tournament of {@code n} randomly chosen {@link DoubleArraySolution}s.
 *
 * @author Mate Gašparini
 */
public class NTournamentSelection implements ISelection<DoubleArraySolution> {

    /** Minimum value of the {@code n} parameter. */
    public static final int MIN_TOURNAMENT = 2;

    /** Number of solutions participating in the tournament. */
    private int n;

    /** Helper {@code List} of tournament participating solutions. */
    private List<DoubleArraySolution> solutions;

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
        this.solutions = new ArrayList<>(n);
    }

    @Override
    public DoubleArraySolution getSolution(List<DoubleArraySolution> population) {
        for (int i = 0; i < n; i ++) {
            solutions.add(population.get(random.nextInt(population.size())));
        }

        solutions.sort(Comparator.reverseOrder());
        DoubleArraySolution bestSolution = solutions.get(0);
        solutions.clear();

        return bestSolution;
    }
}
