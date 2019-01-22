package hr.fer.zemris.optjava.dz10.problem;

/**
 * <p>Models the following problem:</p>
 * <ul>
 *     <li>minimize f(X) = x₁</li>
 *     <li>minimize f(X) = (1 + x₂) / x₁</li>
 * </ul>
 * <p>where X = (x₁, x₂) ∈ R^2,</p>
 * <p>with bounds x₁ ∈ [0.1, 1], x₂ ∈ [0, 5].</p>
 *
 * @author Mate Gašparini
 */
public class SecondProblem implements MOOPProblem {

    /** Number of decision values. */
    private static final int NUMBER_OF_SOLUTIONS = 2;

    /** Number of objective values. */
    private static final int NUMBER_OF_OBJECTIVES = 2;

    /** Lower bounds of decision values. */
    private static final double[] X_MIN = new double[] {0.1, 0.0};

    /** Upper bounds of decision values. */
    private static final double[] X_MAX = new double[] {1.0, 5.0};

    /** Lower bounds of objective values. */
    private static final double[] F_MIN = new double[] {0.1, 1.0};

    /** Upper bounds of objective values. */
    private static final double[] F_MAX = new double[] {1.0, 60.0};

    @Override
    public int getNumberOfSolutions() {
        return NUMBER_OF_SOLUTIONS;
    }

    @Override
    public int getNumberOfObjectives() {
        return NUMBER_OF_OBJECTIVES;
    }

    @Override
    public void evaluateSolution(double[] solution, double[] objectives) {
        objectives[0] = solution[0];
        objectives[1] = (1 + solution[1]) / solution[0];
    }

    @Override
    public double[] xMin() {
        return X_MIN;
    }

    @Override
    public double[] xMax() {
        return X_MAX;
    }

    @Override
    public double[] fMin() {
        return F_MIN;
    }

    @Override
    public double[] fMax() {
        return F_MAX;
    }
}
