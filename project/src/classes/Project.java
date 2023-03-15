package classes;

import java.util.ArrayList;
import java.util.Arrays;

import controller.CollagerController;
import state.CollagerState;
import utils.Utils;

public class Project {
  String name;
  int height;
  int width;
  int maxValue;
  ArrayList<Layer> layers;
  CollagerState state;
  Utils utils;
  CollagerController controller;
  ArrayList<ArrayList<ArrayList<Pixel>>> layeredPixels;

  public Project(String name, int height, int width, CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
    this.name = name;
    this.height = height;
    this.width = width;
    this.maxValue = utils.maxValue;
    this.layers = new ArrayList<Layer>();
    this.layeredPixels = new ArrayList<ArrayList<ArrayList<Pixel>>>();
  }

  public Project(String name, int height, int width, int maxValue, ArrayList<Layer> layers, CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
    this.name = name;
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
    this.layers = layers;
    this.layeredPixels = new ArrayList<ArrayList<ArrayList<Pixel>>>();
  }

  public String toString() {
    return this.name;
  }

  public void addInitialLayer() {
    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<ArrayList<Pixel>>();
    for (int i = 0; i < this.height; i++) {
      pixels.add(new ArrayList<Pixel>());
      for (int k = 0; k < this.width; k++) {
        pixels.get(i).add(new Pixel(255, 255, 255, 255, this.state, this.controller));
      }
    }
    this.layers.add(new Layer(pixels, "Initial"));
  }

  public void addLayer(String name) {
    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<ArrayList<Pixel>>();
    for (int i = 0; i < this.height; i++) {
      pixels.add(new ArrayList<Pixel>());
      for (int k = 0; k < this.width; k++) {
        pixels.get(i).add(new Pixel(255, 255, 255, 0, this.state, this.controller));
      }
    }
    this.layers.add(0, new Layer(pixels, name));
  }

  public void saveImage(String[] input) {
    for (int i = 0; i < this.layers.get(0).pixels.size(); i++) {
      this.layeredPixels.add(new ArrayList<ArrayList<Pixel>>());
      for (int k = 0; k < this.layers.get(0).pixels.get(0).size(); k++) {
        this.layeredPixels.get(i).add(new ArrayList<Pixel>());
        for (int j = 0; j < this.layers.size(); j++) {
          this.layeredPixels.get(i).get(k).add(this.layers.get(j).pixels.get(i).get(k));
        }
      }
    }
    ArrayList<Pixel> fillRow = new ArrayList<Pixel>();
    ArrayList<ArrayList<Pixel>> finalArray = new ArrayList<ArrayList<Pixel>>();
    Pixel pixPrime = new Pixel();
    for (int i = 0; i < this.layeredPixels.size(); i++) {
      for (int k = 0; k < this.layeredPixels.get(i).size(); k++) {
        for (int j = 0; j < this.layeredPixels.get(i).get(k).size() - 1; j++) {
          if (j == 0) {
            pixPrime = this.formula(this.layeredPixels.get(i).get(k).get(j), this.layeredPixels.get(i).get(k).get(j + 1));
          }
          if (j > 0) {
            pixPrime = this.formula(pixPrime, this.layeredPixels.get(i).get(k).get(j + 1));
          }
        }
        fillRow.add(pixPrime);
      }
      finalArray.add(fillRow);
      fillRow = new ArrayList<Pixel>();
    }
    this.utils.saveImageToFile(this.height, this.width, this.maxValue, finalArray);
  }

  public Pixel formula (Pixel top, Pixel bottom) {
    if (top.alpha == 0 && bottom.alpha > 0) {
      return bottom;
    }
    if (bottom.alpha == 0 && top.alpha > 0) {
      return top;
    }
    if (top.alpha == 0 && bottom.alpha == 0) {
      return top;
    }
    int aPrime = (top.alpha/255 + bottom.alpha/255 * (1 - (top.alpha/255)));
    int rPrime = (top.alpha/255 * top.red 
            + bottom.red * (bottom.alpha/255)
            * (1 - top.alpha/255)) * (1/aPrime);
    int gPrime = (top.alpha/255 * top.green
            + bottom.green * (bottom.alpha/255)
            * (1 - top.alpha/255)) * (1/aPrime);
    int bPrime = (top.alpha/255 * top.blue
            + bottom.blue * (bottom.alpha/255)
            * (1 - top.alpha/255)) * (1/aPrime);
    
    return new Pixel(rPrime, gPrime, bPrime, aPrime, this.state, this.controller);
  }

  public ArrayList<Layer> getLayers() {
    return this.layers;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public int getMaxValue() {
    return this.maxValue;
  }
}
