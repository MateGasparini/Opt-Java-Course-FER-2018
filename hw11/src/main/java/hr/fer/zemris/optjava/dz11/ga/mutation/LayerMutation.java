package hr.fer.zemris.optjava.dz11.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * {@link Mutation} operator which randomly swaps {@code n} rectangle layers.
 *
 * @author Mate Gašparini
 */
public class LayerMutation implements Mutation<GASolution<int[]>> {

    /** Number of layer swaps. */
    private int n;

    /**
     * Constructor specifyingt the number of layer swaps.
     *
     * @param n The specified swap count.
     */
    public LayerMutation(int n) {
        this.n = n;
    }

    @Override
    public void mutate(GASolution<int[]> solution) {
        IRNG rng = RNG.getRNG();
        int[] data = solution.getData();
        for (int count = 0; count < n; count ++) {
            int firstIndex = rng.nextInt(1, data.length);
            firstIndex -= (firstIndex - 1) % 5;
            int secondIndex;
            do {
                secondIndex = rng.nextInt(1, data.length);
                secondIndex -= (secondIndex - 1) % 5;
            } while (secondIndex == firstIndex);

            for (int i = 0; i < 5; i++) {
                int copy = data[firstIndex + i];
                data[firstIndex + i] = data[secondIndex + i];
                data[secondIndex + i] = copy;
            }
        }
    }
}
