package classes;

import controller.CollagerController;
import interfaces.PixelType;
import state.CollagerState;
import utils.Utils;
import view.TextView;

/**
 * Class representation of an HSL Pixel, which consists of
 * a hue value, a saturation value, and a lightness value.
 */
public class PixelHSL implements PixelType {
  private double hue;
  private double saturation;
  private double lightness;
  private CollagerState state;
  private CollagerController controller;
  private Utils utils;

  /**
   * The constructor for the PixelHSL class.
   * @param hue represents the tone of the color, (i.e. red,
   *            green, etc.).
   * @param saturation represents the intensity of a color
   *                   in a given pixel.
   * @param lightness  represents how bright a pixel is.
   */
  PixelHSL(double hue, double saturation, double lightness, CollagerState state, CollagerController controller) {
    this.hue = hue;
    this.saturation = saturation;
    this.lightness = lightness;
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);

    if (hue > 360 || hue <= 0) {
      throw new IllegalArgumentException("hue value is not within bounds.");
    }
    if (saturation > 1 || saturation < 0) {
      throw new IllegalArgumentException("saturation value is not within bounds.");
    }
    if (lightness > 1 || lightness < 0) {
      throw new IllegalArgumentException("lightness value is not within bounds");
    }
  }

  /**
   * Constructor for the PixelHSL class that converts an RGB Pixel.
   * @param pixelRGB the pixel in RGB format that will be converted.
   */
  PixelHSL(PixelRGB pixelRGB, CollagerState state, CollagerController controller) {
    this.state = state;
    this.convertRGBtoHSL(pixelRGB);
    this.controller = controller;
  }

  /**
   * A method that gets the HSL color value based on a string.
   * @param color represents one of the HSL values.
   * @return a singular H, S, or L value.
   */
  public double getHSLColorDouble(String color) {
    boolean validColor = true;
    if (!color.equals("Hue") && !color.equals("Saturation") && !color.equals("Lightness")) {
      validColor = false;
    }
    double c = 0;
    if (color.equals("Hue")) {
      c = this.hue;
    }
    if (color.equals("Saturation")) {
      c = this.saturation;
    }
    if (color.equals("Lightness")) {
      c = this.lightness;
    }
    if (!validColor) {
      System.out.println(c);
      throw new IllegalStateException("Invalid color called for.");
    }
    return c;
  }

  /**
   * A method which converts an RGB value into an HSL value.
   * @param pixelRGB represents a given RGB pixel value.
   */
  public void convertRGBtoHSL(PixelRGB pixelRGB) {
    double componentMax = Math.max(pixelRGB.getColorInt("Red"), Math.max(pixelRGB.getColorInt("Green"), pixelRGB.getColorInt("Blue")));
    double componentMin = Math.min(pixelRGB.getColorInt("Red"), Math.min(pixelRGB.getColorInt("Green"), pixelRGB.getColorInt("Blue")));
    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;
    double hue, saturation;
    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;
      if (componentMax == pixelRGB.getColorInt("Red")) {
        hue = (pixelRGB.getColorInt("Green") - pixelRGB.getColorInt("Blue")) / delta;
        hue = hue % 6;
      } else if (componentMax == pixelRGB.getColorInt("Green")) {
        hue = (pixelRGB.getColorInt("Blue") - pixelRGB.getColorInt("Red")) / delta;
        hue += 2;
      } else if (componentMax == pixelRGB.getColorInt("Blue")) {
        hue = (pixelRGB.getColorInt("Red") - pixelRGB.getColorInt("Green")) / delta;
        hue += 4;
      }
      hue = hue * 60;
    }

    this.hue = hue;
    this.lightness = lightness;
    this.saturation = saturation;
  }

  /**
   * A method that darkens a given pixel by multiplying together our
   * darker colors.
   * @param layerPos represents the position of the layer we are currently working on.
   * @param row represents the x position of a pixel.
   * @param col represents the y position of a pixel.
   */
  public void blendPixelMultiply(int layerPos, int row, int col) {
    int firstLayerNotTransparent = -1;
    TextView view = new TextView(this.state);
    for (int i = 1; i <= this.state.currentProject.getLayers().size() - layerPos; i++) {
      if (this.state.currentProject.getLayers().get(layerPos + i)
              .getPixels().get(row).get(col).getColorDouble("Alpha") > 0) {
        firstLayerNotTransparent = layerPos + i;
        break;
      }
    }
    if (firstLayerNotTransparent == -1) {
      return;
    }
    PixelHSL convertedPixel =
            new PixelHSL(this.state.currentProject.getLayers().
                    get(firstLayerNotTransparent).getPixels().get(row).get(col),
                    this.state, this.controller);
    this.lightness = this.lightness * convertedPixel.lightness;
  }
}
