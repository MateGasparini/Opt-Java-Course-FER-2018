package hr.fer.zemris.generic.ga;

/**
 * Provides a thread-local {@link Evaluator} instance.
 *
 * @author Mate Gašparini
 */
public class EvaluatorProvider {

    /** The {@link ThreadLocal} instance. */
    private static ThreadLocal<Evaluator> threadLocal = new ThreadLocal<>();

    /**
     * Default private constructor.
     */
    private EvaluatorProvider() {
    }

    /**
     * <p>Returns the {@link Evaluator} instance of the current thread.</p>
     * <p>If it is the first time calling this method from some thread, its
     * evaluator is constructed, stored for later use and returned.</p>
     *
     * @return The thread-local evaluator.
     */
    public static Evaluator getEvaluator() {
        Evaluator evaluator = threadLocal.get();
        if (evaluator == null) {
            evaluator = new Evaluator(TemplateProvider.getTemplate());
            threadLocal.set(evaluator);
        }
        return evaluator;
    }
}
