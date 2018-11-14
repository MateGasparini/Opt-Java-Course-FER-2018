package hr.fer.zemris.optjava.dz4.part1.selection;

import hr.fer.zemris.optjava.dz4.part1.solution.SingleObjectiveSolution;

import java.util.List;

/**
 * Models the type of selection of a {@link SingleObjectiveSolution} from a
 * population {@code List}.
 *
 * @param <T> Type of the solution.
 * @author Mate Gašparini
 */
public interface ISelection<T extends SingleObjectiveSolution> {

    /**
     * Selects some solution from the given {@code List} and returns it.
     *
     * @param population The given {@code List} of solutions.
     * @return The selected solution.
     */
    T getSolution(List<T> population);
}
