package classes;

import java.util.ArrayList;

/**
 * Class which represents a singular layer in a project.
 */
public class Layer {
  ArrayList<ArrayList<Pixel>> pixels;
  String name;
  String filterOnCurrentLayer;

  /**
   * This is the constructor for the Layer class.
   * @param pixels represents an image, in pixels.
   * @param name represents the name which is given to.
   */
  public Layer(ArrayList<ArrayList<Pixel>> pixels, String name) {
    this.pixels = pixels;
    this.name = name;
    this.filterOnCurrentLayer = "normal";
  }

  public Layer(ArrayList<ArrayList<Pixel>> pixels, String name, String filterOnCurrentLayer) {
    this.pixels = pixels;
    this.name = name;
    this.filterOnCurrentLayer = filterOnCurrentLayer;

  }

  /**
   * A method used when called in other classes in order to
   * get the pixels in a given layer.
   * @return 2D array list of pixels.
   */
  public ArrayList<ArrayList<Pixel>> getPixels() {
    return this.pixels;
  }

  /**
   * A method used when called in other classs in order to
   * get the name of a given layer
   * @return the name of the layer
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


