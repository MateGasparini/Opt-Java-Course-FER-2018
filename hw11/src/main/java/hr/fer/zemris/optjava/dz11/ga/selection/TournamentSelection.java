package hr.fer.zemris.optjava.dz11.ga.selection;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * {@link Selection} in which the best of {@code n} randomly chosen solutions
 * is returned.
 *
 * @author Mate Gašparini
 */
public class TournamentSelection implements Selection<GASolution<int[]>> {

    /** The specified tournament degree. */
    private int n;

    /**
     * Constructor specifying the number of solutions to choose from.
     *
     * @param n The specified tournament degree.
     */
    public TournamentSelection(int n) {
        this.n = n;
    }

    @Override
    public GASolution<int[]> select(List<GASolution<int[]>> population) {
        IRNG rng = RNG.getRNG();
        List<GASolution<int[]>> solutions = new ArrayList<>(n);
        while (solutions.size() < n) {
            GASolution<int[]> solution = population.get(rng.nextInt(0, population.size()));
            if (!solutions.contains(solution)) solutions.add(solution);
        }
        return solutions.stream().min(Comparator.naturalOrder()).get();
    }
}
