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
}
