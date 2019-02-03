package hr.fer.zemris.generic.ga;

import hr.fer.zemris.art.GrayScaleImage;

/**
 * Singleton class which provides a template {@link GrayScaleImage}.
 *
 * @author Mate Gašparini
 */
public class TemplateProvider {

    /** The specified template image. */
    private static GrayScaleImage template;

    /**
     * Default private constructor
     */
    private TemplateProvider() {
    }

    /**
     * Returns the stored template image.
     *
     * @return The template image.
     */
    public static GrayScaleImage getTemplate() {
        return template;
    }

    /**
     * Sets the template image to the given image.
     *
     * @param template The given image.
     */
    public static void setTemplate(GrayScaleImage template) {
        TemplateProvider.template = template;
    }

    /**
     * Returns the width of the stored template image.
     *
     * @return The template width.
     */
    public static int getTemplateWidth() {
        return template.getWidth();
    }

    /**
     * Returns the height of the stored template image.
     *
     * @return The template height.
     */
    public static int getTemplateHeight() {
        return template.getHeight();
    }
}
