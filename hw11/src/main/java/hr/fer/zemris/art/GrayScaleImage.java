package hr.fer.zemris.art;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * Represents a black-and-white image with pixel values ranging from 0 to 255.
 *
 * @author Mate Gašparini
 */
public class GrayScaleImage {

    /** Width of the image. */
    private int width;

    /** Height of the image. */
    private int height;

    /** Pixel data. */
    private byte[] data;

    /**
     * Constructor specifying the width and the height of the image.
     *
     * @param width The specified width.
     * @param height The specified height.
     */
    public GrayScaleImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new byte[height*width];
    }

    /**
     * Returns the width of the image.
     *
     * @return The number of pixel columns.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the image.
     *
     * @return The number of pixel rows.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the pixel data array.
     *
     * @return The pixel data.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Fills the pixel data with the specified color value.
     *
     * @param color The specified color value.
     */
    public void clear(byte color) {
        int index = 0;
        for (int h = 0; h < height; h ++) {
            for (int w = 0; w < width; w ++) {
                data[index] = color;
                index ++;
            }
        }
    }

    /**
     * Draws a rectangle at the specified position and fills it with the
     * specified color value.
     *
     * @param x The specified x-coordinate.
     * @param y The specified y-coordinate.
     * @param w The specified width.
     * @param h The specified height.
     * @param color The specified color value.
     */
    public void rectangle(int x, int y, int w, int h, byte color) {
        int xs = x;
        int xe = x + w - 1;
        int ys = y;
        int ye = y + h - 1;

        if (width <= xs || height <= ys || xe < 0 || ye < 0) return;

        if (xs < 0) xs = 0;
        if (ys < 0) ys = 0;
        if (xe >= width) xe = width-1;
        if (ye >= height) ye = height-1;

        for (int y1 = ys; y1 <= ye; y1 ++) {
            int index = y1*width + xs;
            for (int x1 = xs; x1 <=xe; x1 ++) {
                data[index] = color;
                index ++;
            }
        }
    }

    /**
     * Saves the image to the specified file (in the PNG format).
     *
     * @param file The specified file.
     * @throws IOException If an IO error occurs.
     */
    public void save(File file) throws IOException {
        BufferedImage bim = new BufferedImage(
                width, height, BufferedImage.TYPE_BYTE_GRAY);
        int[] buf = new int[1];
        WritableRaster r = bim.getRaster();
        int index = 0;
        for (int h = 0; h < height; h ++) {
            for (int w = 0; w < width; w ++) {
                buf[0] = (int) data[index] & 0xFF;
                r.setPixel(w, h, buf);
                index ++;
            }
        }

        try {
            ImageIO.write(bim, "png", file);
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Loads the image from the given file and returns it.
     *
     * @param file The given file.
     * @return The parsed image.
     * @throws IOException If an IO error occurs.
     */
    public static GrayScaleImage load(File file) throws IOException {
        BufferedImage bim = ImageIO.read(file);
        if (bim.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            throw new IOException("Image must be gray scale.");
        }

        GrayScaleImage im = new GrayScaleImage(bim.getWidth(), bim.getHeight());
        try {
            int[] buf = new int[1];
            WritableRaster r = bim.getRaster();
            int index = 0;
            for (int h = 0; h < im.height; h ++) {
                for (int w = 0; w < im.width; w ++) {
                    r.getPixel(w, h, buf);
                    im.data[index] = (byte) buf[0];
                    index ++;
                }
            }
        } catch (Exception ex) {
            throw new IOException("A problem occurred while loading the image.");
        }

        return im;
    }
}
