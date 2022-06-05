package mrteddy.gui;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Utilities for mrteddy.gui package.
 *
 * @author MisterTeddy
 */
public class Utilities {

    /**
     * Method to draw a string centered within a rectangle, should be used within the paintComponent method, and pass
     * the default graphics option in the method.
     *
     * @param g Graphics class
     * @param text Text to draw
     * @param rect Area to center the string in, (eg. getWidth(), getHeight())
     * @param font Font to draw text with.
     *
     * @author StackOverflow man
     */
    static public void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = (rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent());
        g.setFont(font);
        g.drawString(text, x, y);
    }

    /**
     * Method changes value of input color based on the <code>amount</code> argument.
     *
     * @param color input color
     * @param amount amount to change color
     * @return <code>Color</code>
     *
     * @author MisterTeddy
     */
    static public Color changeValue(Color color, int amount) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        r = Math.min(Math.max(r + amount, 0), 255);
        g = Math.min(Math.max(g + amount, 0), 255);
        b = Math.min(Math.max(b + amount, 0), 255);

        return new Color(r, g, b);
    }

    /**
     * A method to color images with a certain color, useful for coloring textures with multiple different colors,
     * recommended that you only use black and white images. Use a color with a low value for the best result.
     *
     * @param input BufferedImage image to colorize, usually black and white
     * @param inputColor java.awt.Color the color to add to the image
     * @return Image the image that has been colorized
     *
     * @author MisterTeddy
     */
    public static BufferedImage colorizeImage(BufferedImage input, Color inputColor) {

        Color color = new Color(
                (float)inputColor.getRed() / 255f,
                (float)inputColor.getGreen() / 255f,
                (float)inputColor.getBlue() / 255f, 0.5f);

        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage dyed = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dyed.createGraphics();
        g.drawImage(input, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        g.dispose();
        return dyed;
    }

    /**
     * Convert int ARGB color to a java.awt.Color for easy use
     *
     * @param i int RGB color, returned from Color.getRGB() and such.
     * @return java.awt.Color
     *
     * @author MisterTeddy
     */
    public static Color intToColor(int i) {
        int a = (i >> 24) & 0xFF;
        int r = (i >> 16) & 0xFF;
        int g = (i >>  8) & 0xFF;
        int b = (i      ) & 0xFF;

        return new Color(r, g, b, a);
    }

    /**
     * Draws a 2D array of every colour combination with specified saturation and size.
     *
     * @param width x dimension of output array
     * @param height y dimension of output array
     * @param saturation float 1-0 How saturated you want the colours to be
     * @return Color[][] Array with all different hue and saturation of colour
     *
     * @author MisterTeddy
     */
    public static Color[][] drawColorMap(int width, int height, float saturation) {
        Color[][] output = new Color[width][height];

        float h = 0f,s,b; //initialize r, g and b
        s = Math.max(Math.min(saturation, 1f), 0f); //s equals saturation
        double bInc = 1f / height; //vertical increment, for brightness
        double hInc = 1f / width; //horizontal increment, for hue
        for (int x = 0; x < width; x++) {
            h += hInc;
            b = 1.0f;
            for (int y = 0; y < height; y++) {
                b -= bInc;
                Color buffer = Color.getHSBColor(h,s,b);
                output[x][y] = new Color(buffer.getRed(), buffer.getBlue(), buffer.getGreen());
            }
        }
        return output;
    }

    /**
     * This method converts a 2D array of colors to a image. Good for storing previously made backgrounds for fast
     * rendering
     *
     * @param in Color[][] 2D array of colours as input for raster image
     * @return {@code BufferedImage} output image
     *
     * @author MisterTeddy
     */
    public static BufferedImage colorArrayToImage(Color[][] in) {
        BufferedImage out = new BufferedImage(in.length, in[0].length, BufferedImage.TYPE_INT_RGB);

        for (int rows = 1; rows < in.length; rows++) {
            for (int cols = 1; cols < in[0].length; cols++)
                out.setRGB(rows, cols, in[rows][cols].getRGB());
        }
        return out;
    }

    public static void printArray(Color[][] i) {
        for (int x = i.length - 1; x >= 0; x--) {
            for (int y = i[0].length - 1; y > 0; y--) {
                if (y == 1)
                    System.out.println((colorName(i[x][y])));
                 else
                    System.out.print(colorName(i[x][y]));
            }
        }
    }

    /**
     * Method to print a array 2D array of colors, in the format of the array. Good for testing images, you can get a
     * color from an image using the {@link Color intToColor()} with the {@link int java.awt.Image.getRGB()} method.
     *
     * @param color Color[][] to print
     * @return <code>String</code> Output to print
     * @author MisterTeddy
     */
    public static String colorName(Color color) {

        String out;

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        if (r < g && r > b)
            out = "Red";
        else if (g < b)
            out = "Green";
        else
            out = "Blue";

        return out;
    }

    /**
     * This is a method to read a specific area of a image, from a input image. Useful for reading from texture indexes,
     * and other such activities.
     *
     * @param b {@code {@lnk java.awt.image.BufferedImage}} the image to read from
     * @param x1 {@code int} staring x coordinate
     * @param y1 {@code int} starting y coordinate
     * @param x2 {@code int} end x coordinate
     * @param y2 {@code int} ending y coordinate
     * @return {@code {@link java.awt.image.BufferedImage}} section of image designated
     *
     * @author MisterTeddy
     */
    public static BufferedImage readPixels(BufferedImage b, int x1, int y1, int x2, int y2) {
        if (x1 > x2 || y1 > y2)
            throw new IllegalArgumentException("x1 / y1 less then x2 / y2");
        BufferedImage out = new BufferedImage(x2 - x1, y2 - y1, BufferedImage.TYPE_INT_ARGB);

        for (int outX = 0, x = x1; x < x2; x++) {
            for (int outY = 0, y = y1; y < y2; y++) {
                out.setRGB(outX, outY, b.getRGB(x, y));

                outY++;
            }
            outX++;
        }
        return out;
    }
}
