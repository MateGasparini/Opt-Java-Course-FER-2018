package hr.fer.zemris.optjava.dz3.tempschedule;

/**
 * Models a schedule for temperature changes.
 *
 * @author Mate Gašparini
 */
public interface ITempSchedule {

    /**
     * Returns the next calculated temperature.
     *
     * @return The next temperature.
     */
    double getNextTemperature();

    /**
     * Returns the inner loop counter limit.
     *
     * @return The inner loop counter limit.
     */
    int getInnerLoopCounter();

    /**
     * Returns the outer loop counter limit.
     *
     * @return The outer loop counter limit.
     */
    int getOuterLoopCounter();
}
