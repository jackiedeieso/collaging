package classes;

import java.util.ArrayList;

public class Layer {
  ArrayList<ArrayList<Pixel>> pixels;
  String name;

  public Layer(ArrayList<ArrayList<Pixel>> pixels, String name) {
    this.pixels = pixels;
    this.name = name;
  }

  public ArrayList<ArrayList<Pixel>> getPixels() {
    return this.pixels;
  }

  public String toString() {
    return this.name;
  }
}
