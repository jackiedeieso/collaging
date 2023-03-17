package classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Layer {
  ArrayList<ArrayList<Pixel>> pixels;
  String name;

  ArrayList<ArrayList<Pixel>> normalPixels;

  boolean filtered;

  String filterOnCurrentLayer;

  public Layer(ArrayList<ArrayList<Pixel>> pixels, String name) {
    this.pixels = pixels;
    this.name = name;
    this.normalPixels = new ArrayList<ArrayList<Pixel>>();
    this.filtered = false;
    this.filterOnCurrentLayer = "normal";
  }

  public ArrayList<ArrayList<Pixel>> getPixels() {
    return this.pixels;
  }

  public String toString() {
    return this.name;
  }

  public void backupLayer() {
    this.normalPixels = this.pixels;
  }

  public void makeLayerNormal() {
    this.pixels = this.normalPixels;
  }

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
      this.filtered = true;
    }
    if (color.equals("Green")) {
      this.filterOnCurrentLayer = "green-component";
      this.filtered = true;
    }
    if (color.equals("Blue")) {
      this.filterOnCurrentLayer = "blue-component";
      this.filtered = true;
    }
  }

  public void brightenValue() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int k = 0; k < this.pixels.get(i).size(); k++) {
        this.pixels.get(i).get(k).brightenPixelValue();
      }
    }
    this.filterOnCurrentLayer = "brighten-value";
    this.filtered = true;
  }

  public void brightenIntensity() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).brightenPixelIntensity();
      }
    }
    this.filterOnCurrentLayer = "brighten-intensity";
    this.filtered = true;
  }

  public void brightenLuma() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).brightenPixelIntensity();
      }
    }
    this.filterOnCurrentLayer = "brighten-luma";
    this.filtered = true;
  }

  public void darkenValue() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).darkenPixelValue();
      }
    }
    this.filterOnCurrentLayer = "darken-value";
    this.filtered = true;
  }

  public void darkenIntensity() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).darkenPixelIntensity();
      }
    }
    this.filterOnCurrentLayer = "darken-intensity";
    this.filtered = true;
  }

  public void darkenLuma() {
    for (int i = 0; i < this.pixels.size(); i++) {
      for (int j = 0; j < this.getPixels().get(i).size(); j++) {
        this.pixels.get(i).get(j).darkenPixelLuma();
      }
    }
    this.filterOnCurrentLayer = "darken-luma";
    this.filtered = true;
  }
}


