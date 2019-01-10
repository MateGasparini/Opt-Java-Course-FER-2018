package hr.fer.zemris.optjava.dz9.selection;

import hr.fer.zemris.optjava.dz9.solution.DoubleArraySolution;

import java.util.Random;

/**
 * {@link Selection} in which a solution is selected using fitness-based
 * proportional selection of the given population.
 *
 * @author Mate Gašparini
 */
public class RouletteWheelSelection implements Selection<DoubleArraySolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    @Override
    public DoubleArraySolution select(DoubleArraySolution[] population) {
        double fitnessSum = 0.0;
        for (DoubleArraySolution solution : population) {
            fitnessSum += solution.getFitness();
        }

        double value = random.nextDouble() * fitnessSum;
        for (DoubleArraySolution solution : population) {
            value -= solution.getFitness();
            if (value < 0) return solution;
        }
        return population[population.length - 1];
    }
}
