package test;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * Program which tests the thread-local {@link IRNG} implementation.
 *
 * @author Mate Gašparini
 */
public class Test1 {

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        IRNG rng = RNG.getRNG();

        for (int i = 0; i < 20; i ++) {
            System.out.println(rng.nextInt(-5, 5));
        }
    }
}
