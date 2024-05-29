import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Static utility class that is responsible for transforming the images.
 * Each function (or at least most functions) take in an Image and return
 * a transformed image.
 */
public class ImageManipulator {
    /**
     * Loads the image at the given path
     * @param path path to image to load
     * @return an Img object that has the given image loaded
     * @throws IOException
     */
    public static Img LoadImage(String path) throws IOException {
        Img image = new Img(path);
        return image;

        //throw new UnsupportedOperationException();
    }

    /**
     * Saves the image to the given file location
     * @param image image to save
     * @param path location in file system to save the image
     * @throws IOException
     */
    public static void SaveImage(Img image, String path) throws IOException {
        int dot = 0;
        for(int i = 0; i < path.length(); i++)
            if(path.substring(i, i+1).equals("."))
                dot = i;

        image.Save(path.substring(dot+1), path);
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }

    /**
     * Converts the given image to grayscale (black, white, and gray). This is done
     * by finding the average of the RGB channel values of each pixel and setting
     * each channel to the average value.
     * @param image image to transform
     * @return the image transformed to grayscale
     */
    public static Img ConvertToGrayScale(Img image) {
        for(int i = 0; i < image.GetWidth();i++) {
            for(int j = 0; j < image.GetHeight(); j++){
                RGB colors = image.GetRGB(i, j);
                RGB newColor = new RGB((colors.GetRed() + colors.GetGreen() + colors.GetBlue())/3,(colors.GetRed() + colors.GetGreen() + colors.GetBlue())/3,(colors.GetRed() + colors.GetGreen() + colors.GetBlue())/3);
                image.SetRGB(i,j, newColor);
            }
        }
        return image;
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }

    /**
     * Inverts the image. To invert the image, for each channel of each pixel, we get
     * its new value by subtracting its current value from 255. (r = 255 - r)
     * @param image image to transform
     * @return image transformed to inverted image
     */
    public static Img InvertImage(Img image) {
        for(int i = 0; i < image.GetWidth();i++) {
            for (int j = 0; j < image.GetHeight(); j++) {
                RGB colors = image.GetRGB(i, j);
                int red = 255 - colors.GetRed();
                int green = 255 - colors.GetGreen();
                int blue = 255 - colors.GetBlue();
                RGB newColors = new RGB(red, green, blue);
                image.SetRGB(i, j, newColors);
            }
        }
        return image;
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }

    /**
     * Converts the image to sepia. To do so, for each pixel, we use the following equations
     * to get the new channel values:
     * r = .393r + .769g + .189b
     * g = .349r + .686g + .168b
     * b = 272r + .534g + .131b
     * @param image image to transform
     * @return image transformed to sepia
     */
    public static Img ConvertToSepia(Img image) {
        for(int i = 0; i < image.GetWidth();i++) {
            for (int j = 0; j < image.GetHeight(); j++) {
                RGB colors = image.GetRGB(i, j);
                int red = (int)((0.393 * colors.GetRed()) + (0.769 * colors.GetGreen()) + (0.189 * colors.GetBlue()));
                int green = (int)((0.349 * colors.GetRed()) + (0.686 * colors.GetGreen()) + (0.168 * colors.GetBlue()));
                int blue = (int)((0.272 * colors.GetRed()) + (0.534 * colors.GetGreen()) + (0.131 * colors.GetBlue()));
                RGB newColors = new RGB(red, green, blue);
                image.SetRGB(i, j, newColors);
            }
        }
        return image;
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }

    /**
     * Creates a stylized Black/White image (no gray) from the given image. To do so:
     * 1) calculate the luminance for each pixel. Luminance = (.299 r^2 + .587 g^2 + .114 b^2)^(1/2)
     * 2) find the median luminance
     * 3) each pixel that has luminance >= median_luminance will be white changed to white and each pixel
     *      that has luminance < median_luminance will be changed to black
     * @param image image to transform
     * @return black/white stylized form of image
     */
    public static Img ConvertToBW(Img image) {
        ArrayList<Double> luminance = new ArrayList<Double>();
        for (int i = 0; i < image.GetWidth(); i++) {
            for (int j = 0; j < image.GetHeight(); j++) {
                RGB colors = image.GetRGB(i, j);
                luminance.add(Math.pow(((0.299 * Math.pow(colors.GetRed(), 2)) + (0.587 * Math.pow(colors.GetGreen(), 2)) + (0.114 * Math.pow(colors.GetBlue(), 2))), (1.0 / 2)));
            }
        }
        Collections.sort(luminance);
        //luminance.sort(Comparator.naturalOrder())
        /*for (int k = 0; k < luminance.size(); k++) {
            //for (int l = k; l < luminance.size(); l++) {
                if (luminance.get(k) < luminance.get(l)) {
                    luminance.add(k, luminance.get(l));
                    luminance.remove(l + 1);
                }
            }*/
            double median = luminance.get(luminance.size() / 2);
            int o = 0;
            for (int m = 0; m < image.GetWidth(); m++) {
                for (int n = 0; n < image.GetHeight(); n++) {
                    RGB colors = image.GetRGB(m, n);
                    if ((Math.pow(((0.299 * Math.pow(colors.GetRed(), 2)) + (0.587 * Math.pow(colors.GetGreen(), 2)) + (0.114 * Math.pow(colors.GetBlue(), 2))), (1.0 / 2))) < median) {
                        colors.SetBlue(0);
                        colors.SetRed(0);
                        colors.SetGreen(0);
                        image.SetRGB(m, n, colors);
                    }
                    else if ((Math.pow(((0.299 * Math.pow(colors.GetRed(), 2)) + (0.587 * Math.pow(colors.GetGreen(), 2)) + (0.114 * Math.pow(colors.GetBlue(), 2))), 1.0 / 2)) >= median) {
                        colors.SetBlue(255);
                        colors.SetRed(255);
                        colors.SetGreen(255);
                        image.SetRGB(m, n, colors);
                    }
                }
            }

            return image;
            // Implement this method and remove the line below
            //throw new UnsupportedOperationException();

    }

    /**
     * Rotates the image 90 degrees clockwise.
     * @param image image to transform
     * @return image rotated 90 degrees clockwise
     */
    public  static Img RotateImage(Img image) {
        RGB[][] array = new RGB[image.GetWidth()][image.getHeight()];
        for(int i = 0; i < image.getWidth(); i++){
            for(int j = 0; j < image.getHeight(); j++){
                array[i][j]= image.GetRGB(i,j);
            }
        }
        Img flip = new Img(image.getHeight(), image.getWidth());
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                flip.SetRGB(flip.getWidth()-i,j, array[i][j]);
            }
        }
        image = flip;
        return image;
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }

    /**
     * Applies an Instagram-like filter to the image. To do so, we apply the following transformations:
     * 1) We apply a "warm" filter. We can produce warm colors by reducing the amount of blue in the image
     *      and increasing the amount of red. For each pixel, apply the following transformation:
     *          r = r * 1.2
     *          g = g
     *          b = b / 1.5
     * 2) We add a vignette (a black gradient around the border) by combining our image with an
     *      an image of a halo (you can see the image at resources/halo.png). We take 65% of our
     *      image and 35% of the halo image. For example:
     *          r = .65 * r_image + .35 * r_halo
     * 3) We add decorative grain by combining our image with a decorative grain image
     *      (resources/decorative_grain.png). We will do this at a .95 / .5 ratio.
     * @param image image to transform
     * @return image with a filter
     * @throws IOException
     */
    public static Img InstagramFilter(Img image) throws IOException {
            Img halo = new Img("resources/halo.png");
            Img deck = new Img("resources/decorative_grain.png");

        for(int i = 0; i < image.GetWidth();i++) {
                for (int j = 0; j < image.GetHeight(); j++) {
                    RGB colors = image.GetRGB(i, j);
                    RGB haloColors = halo.GetRGB(i,j);
                    RGB deckColors = deck.GetRGB(i,j);
                    colors.SetRed((int)((((((colors.GetRed() * 0.65) + (0.35 * haloColors.GetRed())))* 0.95) + (0.05 * deckColors.GetRed())) * 1.2));
                    colors.SetGreen((int)(((((colors.GetGreen() * 0.65) + (0.35 * haloColors.GetGreen())))* 0.95) + (0.05 * deckColors.GetGreen())));
                    colors.SetBlue((int)((((((colors.GetBlue() * 0.65) + (0.35 * haloColors.GetBlue()))) * 0.95) + (0.05 * deckColors.GetBlue()))/1.5));
                    image.SetRGB(i,j,colors);
                }
                return image;
            }
        return image;
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }

    /**
     * Sets the given hue to each pixel image. Hue can range from 0 to 360. We do this
     * by converting each RGB pixel to an HSL pixel, Setting the new hue, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param hue amount of hue to add
     * @return image with added hue
     */
    public static Img SetHue(Img image, int hue) {
            if(hue > 360)
                hue = 360;
            else if(hue <0)
                hue = 0;
        for(int i = 0; i < image.GetWidth();i++) {
                for (int j = 0; j < image.GetHeight(); j++) {
                    RGB colors = image.GetRGB(i, j);
                    HSL newCol = colors.ConvertToHSL();
                    newCol.SetHue(hue);
                    image.SetRGB(i,j,newCol.GetRGB());
                }
            }
            return image;
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }

    /**
     * Sets the given saturation to the image. Saturation can range from 0 to 1. We do this
     * by converting each RGB pixel to an HSL pixel, setting the new saturation, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param saturation amount of saturation to add
     * @return image with added hue
     */
    public static Img SetSaturation(Img image, double saturation) {
            if(saturation > 1)
                saturation = 1;
            else if(saturation <0)
                saturation = 0;
            for(int i = 0; i < image.GetWidth();i++) {
                for (int j = 0; j < image.GetHeight(); j++) {
                    RGB colors = image.GetRGB(i, j);
                    HSL newCol = colors.ConvertToHSL();
                    newCol.SetSaturation(saturation);
                    image.SetRGB(i,j,newCol.GetRGB());
                }
            }
            return image;
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }

    /**
     * Sets the lightness to the image. Lightness can range from 0 to 1. We do this
     * by converting each RGB pixel to an HSL pixel, setting the new lightness, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param lightness amount of hue to add
     * @return image with added hue
     */
    public static Img SetLightness(Img image, double lightness) {
            if(lightness > 1)
                lightness = 1;
            else if(lightness <0)
                lightness = 0;
            for(int i = 0; i < image.GetWidth();i++) {
                for (int j = 0; j < image.GetHeight(); j++) {
                    RGB colors = image.GetRGB(i, j);
                    HSL newCol = colors.ConvertToHSL();
                    newCol.SetLightness(lightness);
                    image.SetRGB(i,j,newCol.GetRGB());
                }
            }
            return image;
        // Implement this method and remove the line below
        //throw new UnsupportedOperationException();
    }
}
