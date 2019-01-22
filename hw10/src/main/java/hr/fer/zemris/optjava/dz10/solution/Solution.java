package hr.fer.zemris.optjava.dz10.solution;

/**
 * Models a multiple-objective solution.
 *
 * @author Mate Gašparini
 */
public abstract class Solution {

    /** Objective values array. */
    private double[] objectives;

    /** Fitness value. */
    private double fitness;

    /**
     * Returns the objective values array.
     *
     * @return The objectives.
     */
    public double[] getObjectives() {
        return objectives;
    }

    /**
     * Sets the objective values array to the given array.
     *
     * @param objectives The given objectives.
     */
    public void setObjectives(double[] objectives) {
        this.objectives = objectives;
    }

    /**
     * Returns the fitness value.
     *
     * @return The fitness.
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Sets the fitness value to the given value.
     *
     * @param fitness The given value.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
