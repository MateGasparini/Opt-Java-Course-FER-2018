package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhood.INeighborhood;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;
import hr.fer.zemris.optjava.dz3.tempschedule.ITempSchedule;

import java.util.Random;

/**
 * When running, has a chance (depending on the current temperature) to accept
 * worse neighbors than the current solutions. Performs better than the greedy
 * algorithm, but requires careful parameter tweaking.
 *
 * @param <T> The type of the solution.
 * @author Mate Gašparini
 */
public class SimulatedAnnealing<T extends SingleObjectiveSolution>
        implements IOptAlgorithm<T> {

    /** The specified solution decoder. */
    private IDecoder<T> decoder;

    /** The specified solution neighbor generator. */
    private INeighborhood<T> neighborhood;

    /** The specified starting solution. */
    private T startWith;

    /** The problem function. */
    private IFunction function;

    /** The specified temperature schedule. */
    private ITempSchedule tempSchedule;

    /** Proportionality between solution value and fitness. */
    private boolean minimize;

    /** The internal {@link Random} instance. */
    private Random random = new Random();

    /**
     * Constructor specifying the attributes.
     *
     * @param decoder The specified solution decoder.
     * @param neighborhood The specified solution neighbor generator.
     * @param startWith The specified starting solution.
     * @param function The problem function.
     * @param tempSchedule The specified temperature schedule.
     * @param minimize The specified proportionality between solution value and
     *                 fitness.
     */
    public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood,
            T startWith, IFunction function, ITempSchedule tempSchedule, boolean minimize) {
        this.decoder = decoder;
        this.neighborhood = neighborhood;
        this.startWith = startWith;
        this.function = function;
        this.tempSchedule = tempSchedule;
        this.minimize = minimize;
    }

    @Override
    public T run() {
        T solution = startWith;
        solution.value = value(solution);
        solution.fitness = fitness(solution);

        for (int i = 0; i < tempSchedule.getOuterLoopCounter(); i ++) {
            double temperature = tempSchedule.getNextTemperature();
            for (int j = 0; j < tempSchedule.getInnerLoopCounter(); j ++) {
                T neighbor = neighborhood.randomNeighbor(solution);
                neighbor.value = value(neighbor);
                neighbor.fitness = fitness(neighbor);

                double deltaValue = neighbor.value - solution.value;
                if (deltaValue <= 0.0
                        || random.nextDouble() < Math.exp(-deltaValue/temperature)) {
                    solution = neighbor;
                    System.out.println("Current solution value: " + solution.value);
                }
            }
        }

        return solution;
    }

    /**
     * Calculates the given solution's fitness.
     *
     * @param solution The given solution.
     * @return The calculated fitness.
     */
    private double fitness(T solution) {
        return minimize ? 1.0/solution.value : solution.value;
    }

    /**
     * Calculates the given solution's value.
     *
     * @param solution The given solution.
     * @return The calculated function value.
     */
    private double value(T solution) {
        return function.valueAt(decoder.decode(solution));
    }
}
