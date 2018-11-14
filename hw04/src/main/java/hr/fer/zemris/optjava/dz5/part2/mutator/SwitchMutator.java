package hr.fer.zemris.optjava.dz5.part2.mutator;

import hr.fer.zemris.optjava.dz5.part2.solution.Solution;

import java.util.Random;

/**
 * Class which is able to mutate a {@link Solution} by switching random elements.
 *
 * @author Mate Gašparini
 */
public class SwitchMutator {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /**
     * <p>Mutates the given solution by randomly switching two random elements
     * in the solution array.</p>
     * <p>Note that the switch may not happen at all in a, somewhat unlikely,
     * event of choosing the same index two times.</p>
     *
     * @param solution The given solution.
     */
    public void mutate(Solution solution) {
        int size = solution.values.length;
        int first = random.nextInt(size-1);
        int second = first + random.nextInt(size-first);

        int firstCopy = solution.values[first];
        solution.values[first] = solution.values[second];
        solution.values[second] = firstCopy;
    }
}
