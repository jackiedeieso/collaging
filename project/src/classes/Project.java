package classes;

import java.util.ArrayList;

import controller.CollagerController;
import state.CollagerState;
import utils.ImageUtil;
import utils.Utils;
import view.TextView;

/**
 * A class representation of a Project. A project is what a user can open
 * or close, and can contain multiple layers.
 */
public class Project {
  String name;
  int height;
  int width;
  int maxValue;
  ArrayList<LayerRGB> layers;
  CollagerState state;
  Utils utils;
  CollagerController controller;
  ArrayList<ArrayList<ArrayList<PixelRGB>>> layeredPixels;
  TextView view;

  /**
   * First constructor for the Project class. This is used for creating a
   * new project.
   *
   * @param name       represents the title given to a project.
   * @param height     represents the height of a frame for the project.
   * @param width      represents the width of a frame for the project.
   * @param state      represents the current state of the game.
   * @param controller represents the controller class that run methods for the main.
   */
  public Project(String name, int height, int width, CollagerState state,
                 CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
    this.name = name;
    this.height = height;
    this.width = width;
    this.maxValue = utils.maxValue;
    this.view = new TextView(this.state);
    this.layers = new ArrayList<LayerRGB>();
    this.layeredPixels = new ArrayList<ArrayList<ArrayList<PixelRGB>>>();
  }

  /**
   * Second constructor for the Project class. This constructor is used for
   * loading an existing project in.
   *
   * @param name       represents the title given to a project.
   * @param height     represents the height of a frame for the project.
   * @param width      represents the width of a frame for the project.
   * @param maxValue   represents the max value of a PixelRGB.
   * @param layers     represents the layer(s) in a project.
   * @param state      represents the current state of the game.
   * @param controller represents the controller class that run methods for the main.
   */
  public Project(String name, int height, int width, int maxValue,
                 ArrayList<LayerRGB> layers, CollagerState state,
                 CollagerController controller) {
    this.state = state;
    this.controller = controller;
    this.utils = new Utils(this.state, this.controller);
    this.name = name;
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
    this.view = new TextView(this.state);
    this.layers = layers;
    this.layeredPixels = new ArrayList<ArrayList<ArrayList<PixelRGB>>>();
  }

  /**
   * A method that converts the project into a string by returning
   * the project name.
   *
   * @return the name of the project.
   */
  public String toString() {
    return this.name;
  }

  /**
   * A method that creates the first layer in a project. This consists of
   * a white background with a unique height and width.
   */
  public void addInitialLayer() {
    ArrayList<ArrayList<PixelRGB>> pixels = new ArrayList<ArrayList<PixelRGB>>();
    for (int i = 0; i < this.height; i++) {
      pixels.add(new ArrayList<PixelRGB>());
      for (int k = 0; k < this.width; k++) {
        pixels.get(i).add(new PixelRGB(255, 255, 255, 255, this.state, this.controller));
      }
    }
    this.layers.add(new LayerRGB(pixels, "initial-layer"));
  }

  /**
   * A method that creates a new layer to an existing project.
   *
   * @param name represents the title of the new layer.
   */
  public void addLayer(String name) {
    ArrayList<ArrayList<PixelRGB>> pixels = new ArrayList<ArrayList<PixelRGB>>();
    for (int i = 0; i < this.height; i++) {
      pixels.add(new ArrayList<PixelRGB>());
      for (int k = 0; k < this.width; k++) {
        pixels.get(i).add(new PixelRGB(255, 255, 255, 0, this.state, this.controller));
      }
    }
    this.layers.add(0, new LayerRGB(pixels, name));
  }

  /**
   * A method that saves an image as a PPM.
   *
   * @param input represents the name of the
   *              file given by the user.
   */
  public void saveImage(String[] input) {
    for (int d = 0; d < this.layers.size(); d++) {
      this.setFilter(this.layers.get(d).name, this.layers.get(d).getCurrentFilter());
    }
    String name = input[1];
    if (this.layers.size() == 1) {
      this.utils.saveImageToFile(this.height, this.width, this.maxValue,
              this.layers.get(0).pixels, name);
      return;
    }
    for (int i = 0; i < this.layers.get(0).pixels.size(); i++) {
      this.layeredPixels.add(new ArrayList<ArrayList<PixelRGB>>());
      for (int k = 0; k < this.layers.get(0).pixels.get(0).size(); k++) {
        this.layeredPixels.get(i).add(new ArrayList<PixelRGB>());
        for (int j = 0; j < this.layers.size(); j++) {
          this.layeredPixels.get(i).get(k).add(this.layers.get(j).pixels.get(i).get(k));
        }
      }
    }
    ArrayList<PixelRGB> fillRow = new ArrayList<PixelRGB>();
    ArrayList<ArrayList<PixelRGB>> finalArray = new ArrayList<ArrayList<PixelRGB>>();
    PixelRGB pixPrime = new PixelRGB();
    for (int i = 0; i < this.layeredPixels.size(); i++) {
      for (int k = 0; k < this.layeredPixels.get(i).size(); k++) {
        for (int j = 0; j < this.layeredPixels.get(i).get(k).size() - 1; j++) {
          if (j == 0) {
            pixPrime = this.formula(this.layeredPixels.get(i).get(k).get(j),
                    this.layeredPixels.get(i).get(k).get(j + 1));
          }
          if (j > 0) {
            pixPrime = this.formula(pixPrime, this.layeredPixels.get(i).get(k).get(j + 1));
          }
        }
        fillRow.add(pixPrime);
      }
      finalArray.add(fillRow);
      fillRow = new ArrayList<PixelRGB>();
    }
    this.utils.saveImageToFile(this.height, this.width, this.maxValue, finalArray, name);
  }

  /**
   * A method that computes prime values from RGBA values.
   * This is done with a formula provided.
   *
   * @param top    represents the layer above the bottom layer.
   * @param bottom represents the layer below the top layer.
   * @return a new PixelRGB that represents the RGBA value with the alpha
   *         formula applied.
   */
  public PixelRGB formula(PixelRGB top, PixelRGB bottom) {
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
    float topAlpha = top.alpha;
    float bottomAlpha = bottom.alpha;
    float aDoublePrime = (topAlpha / 255 + bottomAlpha / 255 * (1 - (topAlpha / 255)));
    float aPrime = aDoublePrime * 255;
    float topRed = top.red;
    float bottomRed = bottom.red;
    float topGreen = top.green;
    float bottomGreen = bottom.green;
    float topBlue = top.blue;
    float bottomBlue = bottom.blue;
    float rPrime = (topAlpha / 255 * topRed
            + bottomRed * (bottomAlpha / 255)
            * (1 - topAlpha / 255)) * (1 / aDoublePrime);
    float gPrime = (topAlpha / 255 * topGreen
            + bottomGreen * (bottomAlpha / 255)
            * (1 - topAlpha / 255)) * (1 / aDoublePrime);
    float bPrime = (topAlpha / 255 * topBlue
            + bottomBlue * (bottomAlpha / 255)
            * (1 - topAlpha / 255)) * (1 / aDoublePrime);
    int rPrimeInt = (int) rPrime;
    int gPrimeInt = (int) gPrime;
    int bPrimeInt = (int) bPrime;
    int aPrimeInt = (int) aPrime;

    return new PixelRGB(rPrimeInt, gPrimeInt, bPrimeInt, aPrimeInt, this.state, this.controller);
  }

  /**
   * A method that allows a user to add a PPM image to a given layer.
   *
   * @param layerName represents the layer that the user wants to add
   *                  and image to.
   * @param imageName represents the file path to the image.
   * @param xPosition represents the desired x-position of the image.
   *                  This begins in the top left corner.
   * @param yPosition represents the desired y-position of the image.
   *                  This begins in the top left corner, and then moves
   *                  down.
   */
  public void addImageToLayer(String layerName, String imageName, int xPosition, int yPosition) {
    ImageUtil imageUtil = new ImageUtil(this.state, this.controller);
    if (xPosition < 0 || yPosition < 0 || xPosition > this.width || yPosition > this.height) {
      try {
        view.destination.append("X/Y Values out of bounds." + "\n");
        return;
      } catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    int layerPos = -1;
    for (int i = 0; i < this.layers.size(); i++) {
      if (layerName.equals(this.layers.get(i).name)) {
        layerPos = i;
      }
    }
    if (layerPos == -1) {
      try {
        this.view.destination.append("Given LayerRGB not found. Re-Enter command." + "\n");
        return;
      } catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    imageUtil.readPPM(imageName);
    if (this.state.imageToBeAdded.equals(new ArrayList<ArrayList<PixelRGB>>())) {
      try {
        this.view.destination.append("Image can not be found. Re-Enter command." + "\n");
        return;
      } catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    ArrayList<ArrayList<PixelRGB>> newPixels = this.layers.get(layerPos).pixels;
    ArrayList<ArrayList<PixelRGB>> newLayer = new ArrayList<ArrayList<PixelRGB>>();
    int placeCounterA = 0;
    int placeCounterB = 0;
    for (int a = 0; a < newPixels.size(); a++) {
      placeCounterB = 0;
      newLayer.add(new ArrayList<PixelRGB>());
      for (int b = 0; b < newPixels.get(a).size(); b++) {
        if (a >= yPosition && b >= xPosition
                && this.state.imageToBeAdded.size() > placeCounterA
                && this.state.imageToBeAdded.get(placeCounterA).size() > placeCounterB) {
          newLayer.get(a).add(this.state.imageToBeAdded.get(placeCounterA).get(placeCounterB));
          placeCounterB = placeCounterB + 1;
        } else {
          newLayer.get(a).add(newPixels.get(a).get(b));
        }
      }
      if (a > yPosition) {
        placeCounterA = placeCounterA + 1;
      }
    }
    this.layers.get(layerPos).pixels = newLayer;
  }

  /**
   * A method that gets the layers in a project.
   *
   * @return an ArrayList of layers.
   */
  public ArrayList<LayerRGB> getLayers() {
    return this.layers;
  }

  /**
   * A method that retrieves the height of the project.
   *
   * @return the height of the initial layer.
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * A method retrieves the width of the project.
   *
   * @return the width of the intial layer.
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * A method that retrieves the max value of the RGB values.
   *
   * @return the max value of the RGB values.
   */
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * A method that changes the name of a filter, so that when
   * it is applied, the filter can be saved on the layer.
   *
   * @param layerName    represents the name of the layer.
   * @param filterOption represents which filter is being choosen.
   */
  public void markFilter(String layerName, String filterOption) {
    int layerPos = -1;
    for (int i = 0; i < this.layers.size(); i++) {
      if (layerName.equals(this.layers.get(i).name)) {
        layerPos = i;
      }
    }
    if (layerPos == -1) {
      try {
        this.view.destination.append("Given LayerRGB not found. Re-Enter command." + "\n");
        return;
      } catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    this.layers.get(layerPos).markFilter(filterOption);

  }

  /**
   * A method that applies a unique filter to a given layer.
   *
   * @param layerName    represents the name of the layer.
   * @param filterOption represents which filter is being chosen to be applied.
   */
  public void setFilter(String layerName, String filterOption) {
    int layerPos = -1;
    for (int i = 0; i < this.layers.size(); i++) {
      if (layerName.equals(this.layers.get(i).name)) {
        layerPos = i;
      }
    }
    if (layerPos == -1) {
      try {
        this.view.destination.append("Given LayerRGB not found. Re-Enter command." + "\n");
        return;
      } catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    if (filterOption.equals("normal")) {
      this.layers.get(layerPos).filterOnCurrentLayer = "normal";
    } else if (filterOption.equals("red-component")) {
      this.layers.get(layerPos).changeComponent("Red");
    } else if (filterOption.equals("green-component")) {
      this.layers.get(layerPos).changeComponent("Green");
    } else if (filterOption.equals("blue-component")) {
      this.layers.get(layerPos).changeComponent("Blue");
    } else if (filterOption.equals("brighten-value")) {
      this.layers.get(layerPos).brightenValue();
    } else if (filterOption.equals("brighten-intensity")) {
      this.layers.get(layerPos).brightenIntensity();
    } else if (filterOption.equals("brighten-luma")) {
      this.layers.get(layerPos).brightenLuma();
    } else if (filterOption.equals("darken-value")) {
      this.layers.get(layerPos).darkenValue();
    } else if (filterOption.equals("darken-intensity")) {
      this.layers.get(layerPos).darkenIntensity();
    } else if (filterOption.equals("darken-luma")) {
      this.layers.get(layerPos).darkenLuma();
    } else {
      try {
        this.view.destination.append("Invalid Filter Option. Reverted layer to normal.");
        this.setFilter(layerName, this.layers.get(layerPos).filterOnCurrentLayer);
        this.layers.get(layerPos).filterOnCurrentLayer = "normal";
      } catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
  }
}
