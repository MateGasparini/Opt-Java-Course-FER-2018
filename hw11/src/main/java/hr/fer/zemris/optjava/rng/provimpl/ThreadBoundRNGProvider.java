package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;

/**
 * Provides an {@link IRNG} instance by assuming that the current {@link Thread}
 * object implements the {@link IRNGProvider} interface and returning its
 * internally stored {@link IRNG}.
 *
 * @author Mate Gašparini
 */
public class ThreadBoundRNGProvider implements IRNGProvider {

    @Override
    public IRNG getRNG() {
        return ((IRNGProvider) Thread.currentThread()).getRNG();
    }
}
