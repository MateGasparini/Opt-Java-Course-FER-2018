package hr.fer.zemris.optjava.rng;

import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

/**
 * {@link Thread} which also provides a single {@link IRNG} instance.
 *
 * @author Mate Gašparini
 */
public class EVOThread extends Thread implements IRNGProvider {

    /** The single {@link IRNG} instance. */
    private IRNG rng = new RNGRandomImpl();

    /** @see Thread#Thread() */
    public EVOThread() {
        super();
    }

    /** @see Thread#Thread(Runnable) */
    public EVOThread(Runnable target) {
        super(target);
    }

    /** @see Thread#Thread(ThreadGroup, Runnable) */
    public EVOThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    /** @see Thread#Thread(String) */
    public EVOThread(String name) {
        super(name);
    }

    /** @see Thread#Thread(ThreadGroup, String) */
    public EVOThread(ThreadGroup group, String name) {
        super(group, name);
    }

    /** @see Thread#Thread(Runnable, String) */
    public EVOThread(Runnable target, String name) {
        super(target, name);
    }

    /** @see Thread#Thread(ThreadGroup, Runnable, String) */
    public EVOThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
    }

    /** @see Thread#Thread(ThreadGroup, Runnable, String, long) */
    public EVOThread(ThreadGroup group, Runnable target, String name,
            long stackSize) {
        super(group, target, name, stackSize);
    }

    @Override
    public IRNG getRNG() {
        return rng;
    }
}
