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
  int red;
  int green;
  int blue;
  int alpha;
  CollagerState state;
  CollagerController controller;
  Utils utils;

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
  public PixelRGB(int red, int green, int blue, CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
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
                  CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
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
   *
   * @return An ArrayList of the RGB components of a PixelRGB.
   */
  public ArrayList<Integer> getRGB() {
    return new ArrayList<Integer>(Arrays.asList(this.red, this.green, this.blue));
  }

  /**
   * A method used for four-component PixelRGB values, which creates a new ArrayList
   * of each PixelRGB's values.
   *
   * @return
   */
  public ArrayList<Integer> getRGBA() {
    return new ArrayList<Integer>(Arrays.asList(this.red, this.green, this.blue, this.alpha));
  }

  /**
   * Creates a combined string of the PixelRGB.
   *
   * @return a string of the PixelRGB RGBA.
   */
  public String toString() {
    return "(" + this.red + ", " + this.green + ", " + this.blue + ", " + this.alpha + ")";
  }

  /**
   * Converting a three-component pixel into a four-component
   * pixel, adding the alpha value and creating a transparency
   * effect.
   *
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
    if (this.red > this.state.currentProject.maxValue) {
      this.red = 255;
    }
    if (this.green > this.state.currentProject.maxValue) {
      this.green = 255;
    }
    if (this.blue > this.state.currentProject.maxValue) {
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

  public void convertHSLtoRGB(PixelHSL pixelHSL) {
    double r = convertFn(pixelHSL.hue, pixelHSL.saturation, pixelHSL.lightness, 0) * 255;
    double g = convertFn(pixelHSL.hue, pixelHSL.saturation, pixelHSL.lightness, 8) * 255;
    double b = convertFn(pixelHSL.hue, pixelHSL.saturation, pixelHSL.lightness, 4) * 255;
    this.red = (int) r;
    this.green = (int) g;
    this.blue = (int) b;
  }

  /*
   * Helper method that performs the translation from the HSL polygonal
   * model to the more familiar RGB model
   */
  private double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue/30)) % 12;
    double a  = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }

  public void blendPixelDifference(int layerPos, int row, int col) {
    int firstLayerNotTransparent = -1;
    TextView view = new TextView(this.state);
    for (int i = 1; i <= this.state.currentProject.layers.size() - layerPos; i++) {
      if (this.state.currentProject.layers.get(layerPos + i).getPixels().get(row).get(col).alpha > 0) {
        firstLayerNotTransparent = layerPos + i;
        break;
      }
    }
    if (firstLayerNotTransparent == -1) {
      return;
    }
    this.red = Math.abs(this.red - this.state.currentProject.layers.
            get(firstLayerNotTransparent).getPixels().get(row).get(col).red);
    this.green = Math.abs(this.green - this.state.currentProject.layers.get(firstLayerNotTransparent).getPixels().get(row).get(col).green);
    this.blue = Math.abs(this.blue - this.state.currentProject.layers.get(firstLayerNotTransparent).getPixels().get(row).get(col).blue);
  }
}
