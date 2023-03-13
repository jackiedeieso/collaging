package classes;

import java.util.ArrayList;

import utils.Utils;

public class Project {
  String name;
  int height;
  int width;
  int maxValue;
  ArrayList<Layer> layers;

  Project(String name, int height, int width) {
    Utils utils = new Utils();
    this.name = name;
    this.height = height;
    this.width = width;
    this.maxValue = utils.maxValue;
    this.layers = null;
  }
}
