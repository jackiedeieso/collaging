package classes;

import interfaces.PixelType;

/**
 * Class representation of an HSL Pixel, which consists of
 * a hue value, a saturation value, and a lightness value.
 */
public class PixelHSL implements PixelType {
  double hue;
  double saturation;
  double lightness;

  /**
   * The constructor for the PixelHSL class.
   * @param hue represents the tone of the color, (i.e. red,
   *            green, etc.).
   * @param saturation represents the intensity of a color
   *                   in a given pixel.
   * @param lightness  represents how bright a pixel is.
   */
  PixelHSL(double hue, double saturation, double lightness) {
    this.hue = hue;
    this.saturation = saturation;
    this.lightness = lightness;

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
  PixelHSL(PixelRGB pixelRGB) {
    this.convertRGBtoHSL(pixelRGB);
  }

  /**
   * A method which converts an RGB value into an HSL value.
   * @param pixelRGB represents a given RGB pixel value.
   */
  public void convertRGBtoHSL(PixelRGB pixelRGB) {
    double componentMax = Math.max(pixelRGB.red, Math.max(pixelRGB.green, pixelRGB.blue));
    double componentMin = Math.min(pixelRGB.red, Math.min(pixelRGB.green, pixelRGB.blue));
    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;
    double hue, saturation;
    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;
      if (componentMax == pixelRGB.red) {
        hue = (pixelRGB.green - pixelRGB.blue) / delta;
        hue = hue % 6;
      } else if (componentMax == pixelRGB.green) {
        hue = (pixelRGB.blue - pixelRGB.red) / delta;
        hue += 2;
      } else if (componentMax == pixelRGB.blue) {
        hue = (pixelRGB.red - pixelRGB.green) / delta;
        hue += 4;
      }

      hue = hue * 60;
    }

    this.hue = hue;
    this.lightness = lightness;
    this.saturation = saturation;
  }

}
