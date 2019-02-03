package hr.fer.zemris.optjava.dz11;

/**
 * <p>Command line program which tries to recreate a template image using a
 * genetic algorithm which parallelizes solution evaluation.</p>
 * <p>It expects 7 command line arguments:</p>
 * <ul>
 * <li>path to the template PNG</li>
 * <li>number of rectangles used in the approximation</li>
 * <li>population size</li>
 * <li>maximum iteration</li>
 * <li>maximum fitness value</li>
 * <li>path to the parameters file</li>
 * <li>path to the generated image file.</li>
 * </ul>
 *
 * @author Mate Gašparini
 */
public class Pokretac1 {

    /**
     * Main method which is called when the program launches.
     *
     * @param args Command line arguments (template PNG path, rectangle count,
     *             population size, maximum iteration, maximum fitness, params
     *             file path and generated image path).
     */
    public static void main(String[] args) {
        Util.runAlgorithm(args, Util.createAlgorithm1(args));
    }
}
