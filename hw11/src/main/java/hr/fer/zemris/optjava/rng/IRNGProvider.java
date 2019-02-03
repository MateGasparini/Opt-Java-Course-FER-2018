package hr.fer.zemris.optjava.rng;

/**
 * Provides some {@link IRNG} object (through the {@link #getRNG()} method).
 *
 * @author Mate Gašparini
 */
public interface IRNGProvider {

    /**
     * Returns this object's {@link IRNG}.
     *
     * @return The random number generator.
     */
    IRNG getRNG();
}
