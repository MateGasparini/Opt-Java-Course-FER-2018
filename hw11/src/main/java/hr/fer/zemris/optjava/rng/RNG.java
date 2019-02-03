package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Singleton class which constructs its {@link IRNGProvider} instance by reading
 * the class name as the {@code rng-provider} property from the
 * {@code rng-config.properties} resource.
 *
 * @author Mate Gašparini
 */
public class RNG {

    /** Constructed {@link IRNGProvider} instance. */
    private static IRNGProvider rngProvider;

    static {
        Properties properties = new Properties();
        try {
            properties.load(RNG.class.getResourceAsStream("/rng-config.properties"));
        } catch (IOException e) {
            throw new IllegalStateException("rng-config.properties could not be read.");
        }
        String fcdn = (String) properties.get("rng-provider");
        Class<?> providerClass;
        try {
            providerClass = RNG.class.getClassLoader().loadClass(fcdn);
            rngProvider = (IRNGProvider) providerClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException
                | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new IllegalStateException(fcdn + " is not a valid IRNGProvider.");
        }
    }

    /**
     * Returns the internal {@link IRNGProvider}'s {@link IRNG} instance.
     *
     * @return The appropriate {@link IRNG}.
     */
    public static IRNG getRNG() {
        return rngProvider.getRNG();
    }
}
