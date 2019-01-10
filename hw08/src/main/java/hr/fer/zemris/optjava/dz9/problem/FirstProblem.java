package hr.fer.zemris.optjava.dz9.problem;

/**
 * <p>Models the following problem:</p>
 * <ul>
 *     <li>minimize f(X) = x₁^2</li>
 *     <li>minimize f(X) = x₂^2</li>
 *     <li>minimize f(X) = x₃^2</li>
 *     <li>minimize f(X) = x₄^2</li>
 * </ul>
 * <p>where X = (x₁, x₂, x₃, x₄) ∈ R^4,</p>
 * <p>with bound xi ∈ [-5, 5], i ∈ {1, 2, 3, 4}.</p>
 *
 * @author Mate Gašparini
 */
public class FirstProblem implements MOOPProblem {

    /** Number of decision values. */
    private static final int NUMBER_OF_SOLUTIONS = 4;

    /** Number of objective values. */
    private static final int NUMBER_OF_OBJECTIVES = 4;

    /** Lower bounds of decision values. */
    private static final double[] X_MIN = new double[] {-5.0, -5.0, -5.0, -5.0};

    /** Upper bounds of decision values. */
    private static final double[] X_MAX = new double[] {5.0, 5.0, 5.0, 5.0};

    /** Lower bounds of objective values. */
    private static final double[] F_MIN = new double[] {0.0, 0.0, 0.0, 0.0};

    /** Upper bounds of objective values. */
    private static final double[] F_MAX = new double[] {25.0, 25.0, 25.0, 25.0};

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
        for (int i = 0; i < solution.length; i ++) {
            double component = solution[i];
            objectives[i] = component*component;
        }
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
