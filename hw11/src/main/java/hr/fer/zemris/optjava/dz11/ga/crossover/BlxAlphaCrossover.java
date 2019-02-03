package hr.fer.zemris.optjava.dz11.ga.crossover;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IntArrayGASolution;
import hr.fer.zemris.generic.ga.TemplateProvider;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * {@link Crossover} which fills the child data array with values chosen from a
 * parent value interval expanded by some specified factor.
 *
 * @author Mate Gašparini
 */
public class BlxAlphaCrossover implements Crossover<GASolution<int[]>> {

    /** The specified alpha factor. */
    private double alpha;

    /**
     * Constructor specifying the alpha factor.
     *
     * @param alpha The specified alpha factor.
     */
    public BlxAlphaCrossover(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public GASolution<int[]> cross(GASolution<int[]> first,
            GASolution<int[]> second) {
        IRNG rng = RNG.getRNG();

        int[] firstData = first.getData();
        int[] secondData = second.getData();
        int[] childData = new int[firstData.length];

        for (int i = 0; i < childData.length; i ++) {
            int lower = firstData[i];
            int upper = secondData[i];

            if (lower > upper) {
                int lowerCopy = lower;
                lower = upper;
                upper = lowerCopy;
            }

            int extension = (int) ((upper - lower)*alpha);
            lower -= extension;
            upper += extension;
            childData[i] = rng.nextInt(lower, upper);
        }

        childData[0] = childData[0] % 256;

        int width = TemplateProvider.getTemplateWidth();
        int height = TemplateProvider.getTemplateHeight();

        for (int i = 1; i < childData.length; i ++) {
            childData[i] = childData[i] % width;
            childData[++i] = childData[i] % height;
            childData[++i] = childData[i] % width;
            childData[++i] = childData[i] % height;
            childData[++i] = childData[i] % 256;
        }

        return new IntArrayGASolution(childData);
    }
}
