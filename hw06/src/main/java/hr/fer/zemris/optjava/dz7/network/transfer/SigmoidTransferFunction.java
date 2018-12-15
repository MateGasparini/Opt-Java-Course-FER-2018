package hr.fer.zemris.optjava.dz7.network.transfer;

/**
 * The sigmoid function ({@code 1/(1+e^-x)}).
 *
 * @author Mate Gašparini
 */
public class SigmoidTransferFunction implements ITransferFunction {

    @Override
    public double transfer(double value) {
        return 1.0 / (1.0 + Math.exp(-value));
    }
}
