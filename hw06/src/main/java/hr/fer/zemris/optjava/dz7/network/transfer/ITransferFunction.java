package hr.fer.zemris.optjava.dz7.network.transfer;

/**
 * Represents some transfer function which is used to map the neuron's sum value
 * to some <i>reasonable</i> (e.g. {@code [0, 1]}) interval.
 *
 * @author Mate Gašparini
 */
public interface ITransferFunction {

    /**
     * Transfers the given value to the function-specified interval.
     *
     * @param value The given value.
     * @return The <i>transferred</i> value.
     */
    double transfer(double value);
}
