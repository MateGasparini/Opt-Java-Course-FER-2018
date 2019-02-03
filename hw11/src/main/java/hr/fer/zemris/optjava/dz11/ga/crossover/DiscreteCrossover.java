package hr.fer.zemris.optjava.dz11.ga.crossover;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IntArrayGASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * {@link Crossover} which fills the child data array with discrete,
 * randomly-chosen parent data values.
 *
 * @author Mate Gašparini
 */
public class DiscreteCrossover implements Crossover<GASolution<int[]>> {

    @Override
    public GASolution<int[]> cross(GASolution<int[]> first,
            GASolution<int[]> second) {
        IRNG rng = RNG.getRNG();
        int[] firstData = first.getData();
        int[] secondData = second.getData();
        int[] childData = new int[firstData.length];
        for (int i = 0; i < childData.length; i ++) {
            childData[i] = rng.nextBoolean() ? firstData[i] : secondData[i];
        }
        return new IntArrayGASolution(childData);
    }
}
