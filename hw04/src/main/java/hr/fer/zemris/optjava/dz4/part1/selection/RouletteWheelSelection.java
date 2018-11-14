package hr.fer.zemris.optjava.dz4.part1.selection;

import hr.fer.zemris.optjava.dz4.part1.solution.DoubleArraySolution;

import java.util.List;
import java.util.Random;

/**
 * {@link ISelection} in which a solution is selected using fitness-based
 * proportional selection of the given population.
 *
 * @author Mate Gašparini
 */
public class RouletteWheelSelection implements ISelection<DoubleArraySolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    @Override
    public DoubleArraySolution getSolution(List<DoubleArraySolution> population) {
        double fitnessSum = 0.0;
        for (DoubleArraySolution solution : population) {
            fitnessSum += solution.fitness;
        }

        double value = random.nextDouble() * fitnessSum;
        for (DoubleArraySolution solution : population) {
            value -= solution.fitness;
            if (value < 0) return solution;
        }

        return population.get(population.size()-1); // Last element rounding errors.
    }
}
