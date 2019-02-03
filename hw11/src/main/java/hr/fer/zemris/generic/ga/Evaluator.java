package hr.fer.zemris.generic.ga;

import hr.fer.zemris.art.GrayScaleImage;

import java.util.Objects;

/**
 * {@link IGAEvaluator} which evaluates solutions according to the pixel
 * difference between the specified template and the corresponding image.
 *
 * @author Mate Gašparini
 */
public class Evaluator implements IGAEvaluator<int[]> {

    /** The specified template image. */
    private GrayScaleImage template;

    /** Single instance of the solution image. */
    private GrayScaleImage image;

    /**
     * Constructor specifying the template image.
     *
     * @param template The specified template image.
     */
    public Evaluator(GrayScaleImage template) {
        this.template = Objects.requireNonNull(
                template, "Template image must not be null."
        );
    }

    /**
     * Draws an image which corresponds to the given solution and returns it.
     *
     * @param solution The given solution.
     * @param image The image result. If {@code null}, it is constructed
     *              according to template dimensions.
     * @return The drawn image.
     */
    public GrayScaleImage draw(GASolution<int[]> solution, GrayScaleImage image) {
        if (image == null) {
            image = new GrayScaleImage(template.getWidth(), template.getHeight());
        }

        int[] data = solution.getData();
        byte bgColor = (byte) data[0];
        image.clear(bgColor);

        int n = (data.length - 1) / 5;
        int index = 1;
        for (int i = 0; i < n; i ++) {
            image.rectangle(data[index], data[index+1], data[index+2],
                    data[index+3], (byte) data[index+4]);
            index += 5;
        }

        return image;
    }

    @Override
    public void evaluate(GASolution<int[]> solution) {
        if (image == null) {
            image = new GrayScaleImage(template.getWidth(), template.getHeight());
        }
        draw(solution, image);

        byte[] iData = image.getData();
        byte[] tData = template.getData();
        int width = image.getWidth();
        int height = image.getHeight();

        double error = 0.0;
        int index = 0;
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x ++) {
                error += Math.abs(
                        ((int) iData[index] & 0xFF) - ((int) tData[index] & 0xFF)
                );
                index ++;
            }
        }

        solution.fitness = -error;
    }
}
