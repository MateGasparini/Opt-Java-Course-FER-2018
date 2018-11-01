package hr.fer.zemris.optjava.dz3.tempschedule;

/**
 * {@link ITempSchedule} which reduces the temperature geometrically.
 *
 * @author Mate Gašparini
 */
public class GeometricTempSchedule implements ITempSchedule {

    /** Cooling factor (should be close to 1.0). */
    private double alpha;

    /** Initial temperature. */
    private double tInitial;

    /** Current temperature. */
    private double tCurrent;

    /** Inner loop limit. */
    private final int innerLimit;

    /** Outer loop limit. */
    private final int outerLimit;

    /**
     * Constructor specifying the attributes.
     *
     * @param tInitial The specified initial temperature.
     * @param tFinal The specified final temperature.
     * @param innerLimit The specified inner loop limit.
     * @param outerLimit The specified outer loop limit.
     */
    public GeometricTempSchedule(double tInitial, double tFinal,
                                 int innerLimit, int outerLimit) {
        this.tInitial = tInitial;
        this.tCurrent = tInitial;
        this.innerLimit = innerLimit;
        this.outerLimit = outerLimit;
        calculateAlpha(tFinal);
    }

    @Override
    public double getNextTemperature() {
        tCurrent *= alpha;
        return tCurrent;
    }

    @Override
    public int getInnerLoopCounter() {
        return innerLimit;
    }

    @Override
    public int getOuterLoopCounter() {
        return outerLimit;
    }

    /**
     * Calculates an appropriate alpha (cooling factor) value.
     *
     * @param tFinal The specified final temperature.
     */
    private void calculateAlpha(double tFinal) {
        if (tFinal == 0.0) tFinal = 1;
        alpha = Math.pow(tFinal / tInitial, 1.0 / (outerLimit-1));
    }
}
