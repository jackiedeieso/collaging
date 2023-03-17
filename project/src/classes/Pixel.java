package classes;

import java.util.ArrayList;
import java.util.Arrays;

import controller.CollagerController;
import state.CollagerState;
import utils.Utils;

public class Pixel {
  int red, green, blue, alpha;
  CollagerState state;
  CollagerController controller;
  Utils utils;

  public Pixel(int red, int green, int blue, CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = this.utils.maxValue;
  }

  public Pixel(int red, int green, int blue, int alpha, CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  //throwaway body to counter bad java logic.
  public Pixel() {

  }

  public ArrayList<Integer> getRGB() {
    return new ArrayList<Integer>(Arrays.asList(this.red, this.green, this.blue));
  }

  public ArrayList<Integer> getRGBA() {
    return new ArrayList<Integer>(Arrays.asList(this.red, this.green, this.blue, this.alpha));
  }

  public String toString() {
    return "(" + this.red + ", " + this.green + ", " + this.blue + ", " + this.alpha + ")";
  }

  public ArrayList<Integer> getRGBAConvertRGB() {
    int redPrime = this.red * this.alpha/255;
    int greenPrime = this.green * this.alpha/255;
    int bluePrime = this.blue * this.alpha/255;
    return new ArrayList<Integer>(Arrays.asList(redPrime, greenPrime, bluePrime));
  }

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

  public void brightenPixelValue() {
    int arr[] = {this.red, this.green, this.blue};
    int value =  Arrays.stream(arr).max().getAsInt();
    this.red = this.red + value;
    this.green = this.green + value;
    this.blue = this.blue + value;
    this.checkRGBLimits();
  }

  public void brightenPixelIntensity() {
    int arr[] = {this.red, this.green, this.blue};
    int avg = (this.red + this.green + this.blue) / 3;
    this.red = this.red + avg;
    this.green = this.green + avg;
    this.blue = this.blue + avg;
    this.checkRGBLimits();
  }

  public void brightenPixelLuma() {
    double luma  = (0.2126 * this.red) + (0.7153 * this.green) * (0.0722 * this.blue);
    int lumaInt = (int)luma; // turns luma into an int
    this.red = this.red + lumaInt;
    this.green = this.green + lumaInt;
    this.blue = this.blue + lumaInt;
    this.checkRGBLimits();
  }

  public void darkenPixelValue() {
    int arr[] = {this.red, this.green, this.blue};
    int value =  Arrays.stream(arr).min().getAsInt(); // max or min?
    this.red = this.red - value;
    this.green = this.green - value;
    this.blue = this.blue - value;
    this.checkRGBLimitZero();
  }

  public void darkenPixelIntensity() {
    int arr[] = {this.red, this.green, this.blue};
    int avg = (this.red + this.green + this.blue) / 3;
    this.red = this.red - avg;
    this.green = this.green - avg;
    this.blue = this.blue - avg;
    this.checkRGBLimitZero();
  }

  public void darkenPixelLuma() {
    double luma  = (0.2126 * this.red) + (0.7153 * this.green) * (0.0722 * this.blue);
    int lumaInt = (int)luma; // turns luma into an int

    this.red = this.red - lumaInt;
    this.green = this.green - lumaInt;
    this.blue = this.blue - lumaInt;
    this.checkRGBLimitZero();
  }
}
