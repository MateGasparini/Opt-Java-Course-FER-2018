package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

/**
 * Provides a thread-local {@link IRNG} instance (constructed separately for
 * each thread when {@link #getRNG()} is called for the first time).
 *
 * @author Mate Gašparini
 */
public class ThreadLocalRNGProvider implements IRNGProvider {

    /** The {@link ThreadLocal} instance. */
    private ThreadLocal<IRNG> threadLocal = new ThreadLocal<>();

    @Override
    public IRNG getRNG() {
        IRNG rng = threadLocal.get();
        if (rng == null) {
            rng = new RNGRandomImpl();
            threadLocal.set(rng);
        }
        return rng;
    }
}
