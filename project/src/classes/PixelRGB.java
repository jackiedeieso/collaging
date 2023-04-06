package classes;

import java.util.ArrayList;
import java.util.Arrays;

import controller.CollagerController;
import interfaces.PixelType;
import state.CollagerState;
import utils.Utils;
import view.TextView;

/**
 * Class representation for a pixel. A pixel contains an RGB value,
 * which is used to determine the color of the pixel.
 */
public class PixelRGB implements PixelType {
  private int red;
  private int green;
  private int blue;
  private int alpha;
  private CollagerState state;
  private CollagerController controller;
  private Utils utils;
  private TextView view;

  /**
   * First constructor for the PixelRGB class. This constructor is used for
   * pixels with three components.
   *
   * @param red        represents the red value of RGB.
   * @param green      represents the green value of RGB.
   * @param blue       represents the blue value of RGB.
   * @param state      represents the full state of the game.
   * @param controller represents the controller class that run methods for the main.
   */
  public PixelRGB(int red, int green, int blue, CollagerState state, CollagerController controller, TextView view) {
    this.state = state;
    this.controller = controller;
    this.view = view;
    this.utils = new Utils(this.state, this.controller, this.view);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = this.utils.maxValue;
  }

  /**
   * Second constructor for the PixelRGB class. This constructor is used for four component pixels.
   *
   * @param red        represents the red component of the RGB value.
   * @param green      represents the green component of the RGB value.
   * @param blue       represents the blue components of the RGB value.
   * @param alpha      represents the transparency component of a PixelRGB.
   * @param state      represents the full state of the game.
   * @param controller represents the controller class that run methods for the main.
   */
  public PixelRGB(int red, int green, int blue, int alpha,
                  CollagerState state, CollagerController controller, TextView view) {
    this.state = state;
    this.controller = controller;
    this.view = view;
    this.utils = new Utils(this.state, this.controller, this.view);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  /**
   * Third constructor for the PixelRGB class. It is empty because the
   * saveImage() method needed a pixel variable that had to be initialized.
   */
  public PixelRGB(CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
    /*
    This constructor is empty because the saveImage() method needed
    a pixel variable that had to be initialized.
     */
  }

  /**
   * Fourth constructor for the PixelRGB class. Converts an
   * HSL pixel to a RGB pixel.
   * @param pixelHSL The HSL implementation of the new pixel.
   */
  public PixelRGB(PixelHSL pixelHSL, CollagerState state, CollagerController controller) {
    this.convertHSLtoRGB(pixelHSL);
    this.state = state;
    this.controller = controller;
  }

  /**
   * A method with the purpose of creating a new ArrayList of each PixelRGB's
   * values.
   * @return An ArrayList of the RGB components of a PixelRGB.
   */
  public ArrayList<Integer> getRGB() {
    return new ArrayList<Integer>(Arrays.asList(this.red, this.green, this.blue));
  }

  /**
   * A method used for four-component PixelRGB values, which creates a new ArrayList
   * of each PixelRGB's values.
   * @return a new ArrayList of the RGBA values.
   */
  public ArrayList<Integer> getRGBA() {
    return new ArrayList<Integer>(Arrays.asList(this.red, this.green, this.blue, this.alpha));
  }

  /**
   * A method that creates a combined string of the PixelRGB.
   * @return a string of the PixelRGB RGBA.
   */
  public String toString() {
    return "(" + this.red + ", " + this.green + ", " + this.blue + ", " + this.alpha + ")";
  }

  /**
   * A method that converts a three-component pixel into a
   * four-component pixel, adding the alpha value and creating
   * a transparency effect.
   * @return An array of the changed RGB values.
   */
  public ArrayList<Integer> getRGBAConvertRGB() {
    int redPrime = this.red * this.alpha / 255;
    int greenPrime = this.green * this.alpha / 255;
    int bluePrime = this.blue * this.alpha / 255;
    return new ArrayList<Integer>(Arrays.asList(redPrime, greenPrime, bluePrime));
  }

  /**
   * A method that is used to make sure that the RGB values
   * are not going above 255.
   */
  public void checkRGBLimits() {
    if (this.red > this.state.currentProject.getMaxValue()) {
      this.red = 255;
    }
    if (this.green > this.state.currentProject.getMaxValue()) {
      this.green = 255;
    }
    if (this.blue > this.state.currentProject.getMaxValue()) {
      this.blue = 255;
    }
  }

  /**
   * A method used to make sure that the RGB value does not
   * go below 0.
   */
  public void checkRGBLimitZero() {
    if (this.red < 0) {
      this.red = 0;
    }
    if (this.green < 0) {
      this.green = 0;
    }
    if (this.blue < 0) {
      this.blue = 0;
    }
  }

  /**
   * A method that changes a pixel based on the filter being applied.
   * This is a helper for the method changeComponent.
   * @param color represents the color that is not changing.
   */
  public void changePixelComponent(String color) {
    if (color.equals("Red")) {
      this.green = 0;
      this.blue = 0;
    }
    if (color.equals("Green")) {
      this.red = 0;
      this.blue = 0;
    }
    if (color.equals("Blue")) {
      this.red = 0;
      this.green = 0;
    }
  }

  /**
   * A method used to brighten a layer. This is done by increasing each
   * number by the max value in each RGB value.
   */
  public void brightenPixelValue() {
    int[] arr = {this.red, this.green, this.blue};
    int value = Arrays.stream(arr).max().getAsInt();
    this.red = this.red + value;
    this.green = this.green + value;
    this.blue = this.blue + value;
    this.checkRGBLimits();
  }

  /**
   * A method that brightens a layer by the average of the RGB
   * values.
   */
  public void brightenPixelIntensity() {
    int[] arr = {this.red, this.green, this.blue};
    int avg = (this.red + this.green + this.blue) / 3;
    this.red = this.red + avg;
    this.green = this.green + avg;
    this.blue = this.blue + avg;
    this.checkRGBLimits();
  }

  /**
   * A method that brightens a layer by the luma value, which is represented
   * by the weighted sum of the 3 RGB values: 0.2126r + 0.7152g + 0.0722b,
   * where r is the red value, g is the green value, and b is the blue value.
   */
  public void brightenPixelLuma() {
    double luma = (0.2126 * this.red) + (0.7153 * this.green) * (0.0722 * this.blue);
    int lumaInt = (int) luma; // turns luma into an int
    this.red = this.red + lumaInt;
    this.green = this.green + lumaInt;
    this.blue = this.blue + lumaInt;
    this.checkRGBLimits();
  }

  /**
   * A method that darkens a layer based on the minimum value of a given
   * pixel. Each value in the pixel is subtracted by this minimum value.
   */
  public void darkenPixelValue() {
    int[] arr = {this.red, this.green, this.blue};
    int value = Arrays.stream(arr).min().getAsInt(); // max or min?
    this.red = this.red - value;
    this.green = this.green - value;
    this.blue = this.blue - value;
    this.checkRGBLimitZero();
  }

  /**
   * A method that darkens a pixel based on the average value of the pixel.
   * The average is subtracted from the RGB values.
   */
  public void darkenPixelIntensity() {
    int[] arr = {this.red, this.green, this.blue};
    int avg = (this.red + this.green + this.blue) / 3;
    this.red = this.red - avg;
    this.green = this.green - avg;
    this.blue = this.blue - avg;
    this.checkRGBLimitZero();
  }

  /**
   * A method that darkens a layer by the luma value, which is represented
   * by the weighted sum of the 3 RGB values: 0.2126r + 0.7152g + 0.0722b,
   * where r is the red value, g is the green value, and b is the blue value.
   * This value is then subtracted from the RGB values.
   */
  public void darkenPixelLuma() {
    double luma = (0.2126 * this.red) + (0.7153 * this.green) * (0.0722 * this.blue);
    int lumaInt = (int) luma; // turns luma into an int

    this.red = this.red - lumaInt;
    this.green = this.green - lumaInt;
    this.blue = this.blue - lumaInt;
    this.checkRGBLimitZero();
  }

  /**
   * A method that converts an HSL pixel to an RGB pixel.
   * @param pixelHSL represents an HSL pixel to be converted.
   */
  public void convertHSLtoRGB(PixelHSL pixelHSL) {
    double r = convertFn(pixelHSL.getHSLColorDouble("Hue"),
            pixelHSL.getHSLColorDouble("Saturation"),
            pixelHSL.getHSLColorDouble("Lightness"), 0) * 255;
    double g = convertFn(pixelHSL.getHSLColorDouble("Hue"),
            pixelHSL.getHSLColorDouble("Saturation"),
            pixelHSL.getHSLColorDouble("Lightness"), 8) * 255;
    double b = convertFn(pixelHSL.getHSLColorDouble("Hue"),
            pixelHSL.getHSLColorDouble("Saturation"),
            pixelHSL.getHSLColorDouble("Lightness"), 4) * 255;
    this.red = (int) Math.round(r);
    this.green = (int) Math.round(g);
    this.blue = (int) Math.round(b);
    this.alpha = pixelHSL.getAlpha();
    this.limitRGBValues();
  }

  public void limitRGBValues() {
    if (this.red < 0) {
      this.red = 0;
    }
    if (this.green < 0) {
      this.green = 0;
    }
    if (this.blue < 0) {
      this.blue = 0;
    }
    if (this.red > 255) {
      this.red = 255;
    }
    if (this.green > 255) {
      this.green = 255;
    }
    if (this.blue > 255) {
      this.blue = 255;
    }
  }

  /**
   * Helper method that performs the translation from the HSL polygonal
   * model to the more familiar RGB model.
   */
  private double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue/30)) % 12;
    double a  = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }

  /**
   * A method that filters a layer based on all project layers.
   * @param layerPos represents the starting position of the layer.
   * @param row represents the x position of the filter.
   * @param col represents the y position of the filter.
   */
  public void blendPixelDifference(int layerPos, int row, int col) {
    int firstLayerNotTransparent = -1;
    TextView view = new TextView(this.state);
    for (int i = 1; i <= this.state.currentProject.getLayers().size() - layerPos; i++) {
      if (this.state.currentProject.getLayers().get(layerPos + i).getPixels().get(row).get(col).alpha > 0) {
        firstLayerNotTransparent = layerPos + i;
        break;
      }
    }
    if (firstLayerNotTransparent == -1) {
      return;
    }
    this.red = Math.abs(this.red - this.state.currentProject.getLayers()
            .get(firstLayerNotTransparent).getPixels().get(row).get(col).red);
    this.green = Math.abs(this.green - this.state.currentProject.getLayers()
            .get(firstLayerNotTransparent).getPixels().get(row).get(col).green);
    this.blue = Math.abs(this.blue - this.state.currentProject.getLayers()
            .get(firstLayerNotTransparent).getPixels().get(row).get(col).blue);
  }

  /**
   * A method that is used for other methods to return the color float.
   * @param color represents the desired color.
   * @return the number of the color.
   */
  public double getColorDouble(String color) {
    double c = -1;
    if (color.equals("Green")) {
      c = this.green;
    }
    if (color.equals("Blue")) {
      c = this.blue;
    }
    if (color.equals("Red")) {
      c = this.red;
    }
    if (color.equals("Alpha")) {
      c = this.alpha;
    }
    if (c < 0) {
      throw new IllegalStateException("Invalid color called for.");
    }
    return c;
  }

  /**
   * A method that is finding the RGB value represented as an
   * integer.
   * @param color represents the desired color.
   * @return an integer representation of the R, G, or B value.
   */
  public int getColorInt(String color) {
    return (int) this.getColorDouble(color);
  }
}
