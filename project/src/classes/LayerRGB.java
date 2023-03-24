package classes;

import java.util.ArrayList;

import controller.CollagerController;
import state.CollagerState;
import utils.Utils;

/**
 * Class which represents a singular layer in a project. The pixels
 * in this layer are displayed from RGB values.
 */
public class LayerRGB {
  ArrayList<ArrayList<PixelRGB>> pixels;
  String name;
  String filterOnCurrentLayer;

  CollagerState state;

  CollagerController controller;

  Utils utils;

  /**
   * This is the first constructor for the LayerRGB class, which is used
   * when making a layer.
   * @param pixels represents an image, in pixels.
   * @param name represents the name of a layer.
   */
  public LayerRGB(ArrayList<ArrayList<PixelRGB>> pixels, String name, CollagerState state, CollagerController controller) {
    this.pixels = pixels;
    this.name = name;
    this.filterOnCurrentLayer = "normal";
    this.state = state;
    this.controller = controller;
  }

  /**
   * This is the second constructor class, which is used when the user
   * creates a new layer.
   * @param pixels represents the pixels that make up an image.
   * @param name represents the name of a layer.
   * @param filterOnCurrentLayer represents the filter on a given layer.
   */
  public LayerRGB(ArrayList<ArrayList<PixelRGB>> pixels, String name, String filterOnCurrentLayer, CollagerState state, CollagerController controller) {
    this.pixels = pixels;
    this.name = name;
    this.filterOnCurrentLayer = filterOnCurrentLayer;
    this.state = state;
    this.controller = controller;
  }

  /**
   * A method used when called in other classes in order to
   * get the pixels in a given layer.
   * @return 2D array list of pixels.
   */
  public ArrayList<ArrayList<PixelRGB>> getPixels() {
    return this.pixels;
  }

  /**
   * A method used when called in other classes in order to
   * get the name of a given layer.
   * @return the name of the layer.
   */
  public String toString() {
    return this.name;
  }

  /**
   * A method which changes the filter color of a given layer.
   * Once a color is input, turns the rest of the colors to 0.
   * @param color represents either red, green, or blue.
   */
  public void changeComponent(String color) {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int k = 0; k < this.pixels.get(i).size(); k++) {
        if (color.equals("Red")) {
          this.pixels.get(i).get(k).green = 0;
          this.pixels.get(i).get(k).blue = 0;
        }
        if (color.equals("Green")) {
          this.pixels.get(i).get(k).red = 0;
          this.pixels.get(i).get(k).blue = 0;
        }
        if (color.equals("Blue")) {
          this.pixels.get(i).get(k).red = 0;
          this.pixels.get(i).get(k).green = 0;
        }
      }
    }
    if (color.equals("Red")) {
      this.filterOnCurrentLayer = "red-component";
    }
    if (color.equals("Green")) {
      this.filterOnCurrentLayer = "green-component";
    }
    if (color.equals("Blue")) {
      this.filterOnCurrentLayer = "blue-component";
    }
  }

  /**
   * A method used to brighten a layer. This is done by increasing each
   * number by the max value in each RGB value.
   */
  public void brightenValue() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int k = 0; k < this.pixels.get(i).size(); k++) {
        this.pixels.get(i).get(k).brightenPixelValue();
      }
    }
    this.filterOnCurrentLayer = "brighten-value";
  }

  /**
   * A method that brightens a layer by the average of the RGB
   * values.
   */
  public void brightenIntensity() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).brightenPixelIntensity();
      }
    }
    this.filterOnCurrentLayer = "brighten-intensity";
  }

  /**
   * A method that brightens a layer by the luma value, which is represented
   * by the weighted sum of the 3 RGB values: 0.2126r + 0.7152g + 0.0722b,
   * where r is the red value, g is the green value, and b is the blue value.
   */
  public void brightenLuma() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).brightenPixelLuma();
      }
    }
    this.filterOnCurrentLayer = "brighten-luma";
  }

  /**
   * A method that darkens a layer based on the minimum value of a given
   * pixel. Each value in the pixel is subtracted by this minimum value.
   */
  public void darkenValue() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).darkenPixelValue();
      }
    }
    this.filterOnCurrentLayer = "darken-value";
  }

  /**
   * A method that darkens a pixel based on the average value of the pixel.
   * The average is subtracted from the RGB values.
   */
  public void darkenIntensity() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).darkenPixelIntensity();
      }
    }
    this.filterOnCurrentLayer = "darken-intensity";
  }

  /**
   * A method that darkens a layer by the luma value, which is represented
   * by the weighted sum of the 3 RGB values: 0.2126r + 0.7152g + 0.0722b,
   * where r is the red value, g is the green value, and b is the blue value.
   * This value is then subtracted from the RGB values.
   */
  public void darkenLuma() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).darkenPixelLuma();
      }
    }
    this.filterOnCurrentLayer = "darken-luma";
  }

  /**
   * A method that inverts a given pixel, and blends both the current
   * layer and the composite images underneath that layer.
   * @param layerPos represents the position of a given layer.
   */
  public void blendDifference(int layerPos) {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int k = 0; k < this.pixels.get(i).size(); k++) {
        this.pixels.get(i).get(k).blendPixelDifference(layerPos, i, k);
      }
    }
    this.filterOnCurrentLayer = "blend-difference";
  }

  /**
   * A method that darkens a given pixel by multiplying together
   * our darker colors.
   * @param layerPos represents the position of the layer we are currently
   *                 accessing.
   */
  public void blendMultiply(int layerPos) {
    for(int i = 0; i < this.pixels.size(); i++) {
      for (int k = 0; k < this.pixels.get(i).size(); k++) {
        PixelHSL pixelHSL = new PixelHSL(this.pixels.get(i).get(k), this.state, this.controller);
        pixelHSL.blendPixelMultiply(layerPos, i, k);
        this.pixels.get(i).set(k, new PixelRGB(pixelHSL, this.state, this.controller));
      }
    }
    this.filterOnCurrentLayer = "blend-multiply";
  }

  /**
   * A method that labels the current layers filter.
   * @param filterOption represents which filter is being used on
   *                     a given layer.
   */
  public void markFilter(String filterOption) {
    this.filterOnCurrentLayer = filterOption;
  }

  /**
   * A method that is used for other classes in order to determine
   * the filter on a given layer.
   * @return filter of a layer.
   */
  public String getCurrentFilter() {
    return this.filterOnCurrentLayer;
  }
}


