package hr.fer.zemris.optjava.dz8.transfer;

/**
 * {@link ITransferFunction} which maps the given value to the interval
 * {@code [-1, 1]} (basically, a translated and scaled sigmoid).
 *
 * @author Mate Gašparini
 */
public class HyperbolicTangentTransferFunction implements ITransferFunction {

    @Override
    public double transfer(double value) {
        double negativeExp = Math.exp(-value);
        return (1 - negativeExp) / (1 + negativeExp);
    }
}
