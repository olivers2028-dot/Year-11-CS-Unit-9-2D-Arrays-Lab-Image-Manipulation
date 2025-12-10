package code;

import image.APImage;
import image.ImagePanel;
import image.Pixel;

import javax.swing.*;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage image = new APImage("/Users/olivers2028/IdeaProjects/Year-11-CS-Unit-9-2D-Arrays-Lab-Image-Manipulation/cyberpunk2077.jpg");
        image.draw();
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                Pixel pixel = image.getPixel(x, y);
                int pixelAverage = getAverageColour(pixel);
                pixel.setBlue(pixelAverage);
                pixel.setGreen(pixelAverage);
                pixel.setRed(pixelAverage);
            }
        }
        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */

    private static int getAverageColour(Pixel pixel) {
        int sum = (pixel.getBlue() + pixel.getGreen() + pixel.getRed());
        return sum/3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for(int i = 0; i < image.getHeight(); i++){
            for(int j = 0; j < image.getWidth(); j++){
                Pixel pixel = image.getPixel(j, i);

                int pixelAverage = getAverageColour(pixel);
                if(pixelAverage < 128){
                    pixel.setRed(0);
                    pixel.setGreen(0);
                    pixel.setBlue(0);
                } else{
                    pixel.setRed(255);
                    pixel.setGreen(255);
                    pixel.setBlue(255);
                }
            }
        }
        image.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        APImage output = new APImage(pathToFile);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {

                Pixel pixel = image.getPixel(j, i);

                int avg = getAverageColour(pixel);
                Pixel outPix = output.getPixel(j, i);

                boolean leftEdge = false;
                if (j > 0) {
                    Pixel left = image.getPixel(j - 1, i);
                    int leftAvg = getAverageColour(left);
                    if (Math.abs(avg - leftAvg) > threshold) {
                        leftEdge = true;
                    }
                }

                boolean downEdge = false;
                if (i < image.getHeight() - 1) {
                    Pixel down = image.getPixel(j, i + 1);
                    int downAvg = getAverageColour(down);
                    if (Math.abs(avg - downAvg) > threshold) {
                        downEdge = true;
                    }
                }

                if (leftEdge || downEdge) {
                    outPix.setRed(0);
                    outPix.setGreen(0);
                    outPix.setBlue(0);
                } else {
                    outPix.setRed(255);
                    outPix.setGreen(255);
                    outPix.setBlue(255);
                }
            }
        }

        output.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage output = new APImage(pathToFile);
        for (int i = 0; i < image.getHeight() - 1; i++) {
            for (int j = 1; j < image.getWidth() - 1; j++) {
                output.setPixel(image.getWidth()-j, i, image.getPixel(j, i));
            }
        }
        output.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage output = new APImage(image.getHeight(), image.getWidth());
        for (int i = 0; i < image.getHeight() - 1; i++) {
            for (int j = 1; j < image.getWidth() - 1; j++) {
                output.setPixel(i, j, image.getPixel(j, i));
            }
        }
        output.draw();
    }

    public static void greenScreen(String pathToForeground, String pathToBackground, Pixel replacePixel, int threshold){
        APImage foreground = new APImage(pathToForeground);
        APImage background = new APImage(pathToBackground);

        // Error logic: Checks foreground smaller or equal to background size
        if (foreground.getWidth() > background.getWidth() || foreground.getHeight() > background.getHeight()) {
            throw new IllegalArgumentException("Background H/W MUST be larger than corresponding foreground H/W.");
        }

        // Separates the pixel to be replaced into the RGB components, so it is easier accessed later
        int replaceRed = replacePixel.getRed();
        int replaceGreen = replacePixel.getGreen();
        int replaceBlue = replacePixel.getBlue();

        // For loop iterating through each pixel
        for(int y = 0; y < foreground.getHeight(); y++){
            for(int x = 0; x < foreground.getWidth(); x++){

                // Takes pixel from foreground
                Pixel p = foreground.getPixel(x, y);

                // Tests if each pixel matches the color-to-replace pixel within a threshold
                if (Math.abs(p.getRed() - replaceRed) < threshold &&
                        Math.abs(p.getGreen() - replaceGreen) < threshold &&
                        Math.abs(p.getBlue() - replaceBlue) < threshold) {

                    // Sets foreground pixel to background if above condition is true
                    Pixel bgPixel = background.getPixel(x, y);
                    foreground.setPixel(x, y, bgPixel);
                }
            }
        }
        foreground.draw();
    }

}
