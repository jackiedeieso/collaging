package classes;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import controller.CollagerController;
import state.CollagerState;
import utils.ImageUtil;
import utils.Utils;
import view.TextView;

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

  TextView view;

  public Project(String name, int height, int width, CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
    this.name = name;
    this.height = height;
    this.width = width;
    this.maxValue = utils.maxValue;
    this.view = new TextView(this.state);
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
    this.view = new TextView(this.state);
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
    String name = input[1];
    if (this.layers.size() == 1) {
      this.utils.saveImageToFile(this.height, this.width, this.maxValue, this.layers.get(0).pixels, name);
      return;
    }
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
    this.utils.saveImageToFile(this.height, this.width, this.maxValue, finalArray, name);
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
    if (top.alpha == 255 && bottom.alpha == 255) {
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
  public void addImageToLayer(String layerName, String imageName, int xPosition, int yPosition) {
    ImageUtil imageUtil = new ImageUtil(this.state, this.controller);
    Integer layerPos = -1;
    for (int i = 0; i < this.layers.size(); i++) {
      if (layerName.equals(this.layers.get(i).name)) {
        layerPos = i;
      }
    }
    if (layerPos == -1) {
      try {
        this.view.destination.append("Given Layer not found. Re-Enter command." + "\n");
        return;
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    imageUtil.readPPM(imageName);
    if (this.state.imageToBeAdded.equals(new ArrayList<ArrayList<Pixel>>())) {
      try {
        this.view.destination.append("Image can not be found. Re-Enter command." + "\n");
        return;
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    ArrayList<ArrayList<Pixel>> newPixels = this.layers.get(layerPos).pixels;
    ArrayList<ArrayList<Pixel>> newLayer = new ArrayList<ArrayList<Pixel>>();
    int placeCounterA = 0;
    int placeCounterB = 0;
    for (int a = 0; a < newPixels.size(); a++) {
      placeCounterB = 0;
      newLayer.add(new ArrayList<Pixel>());
      for (int b = 0; b < newPixels.get(a).size(); b++) {
        if (a >= yPosition && b >= xPosition
                && this.state.imageToBeAdded.size() > placeCounterA && this.state.imageToBeAdded.get(placeCounterA).size() > placeCounterB) {
          newLayer.get(a).add(this.state.imageToBeAdded.get(placeCounterA).get(placeCounterB));
          placeCounterB = placeCounterB + 1;
        }
        else {
          newLayer.get(a).add(newPixels.get(a).get(b));
        }
      }
      if (a > yPosition) {
        placeCounterA = placeCounterA + 1;
      }
    }
    this.layers.get(layerPos).pixels = newLayer;
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
