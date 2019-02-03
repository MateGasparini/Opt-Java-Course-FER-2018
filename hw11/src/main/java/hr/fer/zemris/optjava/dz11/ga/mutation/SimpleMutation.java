package hr.fer.zemris.optjava.dz11.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.TemplateProvider;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * {@link Mutation} operator which has a chance to change a value from the
 * solution representation to some random, but reasonable (index-dependent)
 * value.
 *
 * @author Mate Gašparini
 */
public class SimpleMutation implements Mutation<GASolution<int[]>> {

    /** Probability of single value mutation. */
    private double chance;

    /**
     * Constructor specifying the probability of a single value mutation.
     *
     * @param chance The specified mutation chance.
     */
    public SimpleMutation(double chance) {
        this.chance = chance;
    }

    @Override
    public void mutate(GASolution<int[]> solution) {
        IRNG rng = RNG.getRNG();
        int width = TemplateProvider.getTemplateWidth();
        int height = TemplateProvider.getTemplateHeight();

        int[] data = solution.getData();
        if (rng.nextDouble() < chance) data[0] = rng.nextInt(0, 256);
        for (int i = 1; i < data.length; i ++) {
            if (rng.nextDouble() < chance) data[i] = rng.nextInt(0, width);
            i++;
            if (rng.nextDouble() < chance) data[i] = rng.nextInt(0, height);
            i++;
            if (rng.nextDouble() < chance) data[i] = rng.nextInt(0, width);
            i++;
            if (rng.nextDouble() < chance) data[i] = rng.nextInt(0, height);
            i++;
            if (rng.nextDouble() < chance) data[i] = rng.nextInt(0, 256);
        }
    }
}
