package classes;

import utils.Utils;

public class Pixel {
  int red, green, blue, alpha;

  Pixel(int red, int green, int blue) {
    Utils utils = new Utils();
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = utils.maxValue;
  }

  Pixel(int red, int green, int blue, int alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }
}
