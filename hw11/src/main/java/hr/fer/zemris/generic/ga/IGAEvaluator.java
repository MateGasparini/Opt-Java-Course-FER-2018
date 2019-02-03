package hr.fer.zemris.generic.ga;

/**
 * Models a {@link GASolution} fitness evaluator.
 *
 * @param <T> The type of solution data.
 * @author Mate Gašparini
 */
public interface IGAEvaluator<T> {

    /**
     * Evaluates the given solution and sets it fitness value to the appropriate
     * value.
     *
     * @param solution The given solution.
     */
    void evaluate(GASolution<T> solution);
}
