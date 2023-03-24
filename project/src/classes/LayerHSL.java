package classes;

import java.util.ArrayList;

/**
 * Class representation of a layer. Each pixel in the layer
 * is in HSL form. At the moment, this class is not in use.
 */
public class LayerHSL {
  ArrayList<ArrayList<PixelHSL>> pixels;
  String name;
  String filterOnCurrentLayer;

  /**
   * This is the first constructor for the LayerHSL class, which is used
   * when making a layer
   * @param pixels
   * @param name
   */
  public LayerHSL(ArrayList<ArrayList<PixelHSL>> pixels, String name) {
    this.pixels = pixels;
    this.name = name;
    this.filterOnCurrentLayer = "normal";
  }

}