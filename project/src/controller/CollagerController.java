package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import classes.Layer;
import classes.Pixel;
import classes.Project;
import state.CollagerState;
import utils.Utils;
import view.TextView;

/**
 * A class representation of the controller for the Collager.
 */
public class CollagerController {

  CollagerState state;
  TextView view;
  Scanner sc;

  public boolean enterCommandStopper;

  /**
   * The constructor for the CollagerController class.
   * @param state represents the current state of the game.
   * @param view represents the control for the output for a user.
   */
  public CollagerController(CollagerState state, TextView view, Scanner sc) {
    this.state = state;
    this.view = view;
    this.sc = sc;
    this.enterCommandStopper = false;
  }

  /**
   * A method that creates a new project for
   * @param input represent the user input for height and width.
   */
  public void makeNewProject(String[] input) {
    int height;
    int width;
    try {
      height = Integer.valueOf(input[1]);
      width = Integer.valueOf(input[2]);
    }
    catch (Exception e) {
      try {
        this.view.destination.append("Height and Width must be given" + "\n");
        return;
      }
      catch (Exception f) {
        throw new IllegalStateException(f.getMessage());
      }
    }
    if (height < 0 || width < 0) {
      try {
        this.view.destination.append("Height and Width must be greater then 0" + "\n");
        return;
      }
      catch (Exception exception) {
        throw new IllegalStateException(exception.getMessage());
      }
    }
    this.state.currentProject = new Project("C1", height, width, this.state, this);
    this.state.currentProject.addInitialLayer();
    this.state.active = true;
  }

  /**
   * A method that, prompted by the user, saves a project to a .txt file.
   */
  public void saveProject() {
    File save;
    PrintWriter pw;
    try {
      this.view.destination.append("Name the file. (no extension, just a name)" + "\n");
    }
    catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
    String fileName = this.sc.next();
    try {
      save = new File(fileName + ".txt");
    }
    catch (Exception e) {
      try {
        this.view.destination.append("Need to open or create a project before saving." + "\n");
        return;
      }
      catch (Exception f) {
        throw new IllegalStateException(f.getMessage());
      }
    }
    try {
      FileWriter fw = new FileWriter(save);
      pw = new PrintWriter(fw);
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
    pw.write(this.state.currentProject.toString() + "\n");
    pw.write(this.state.currentProject.getWidth() + " " + this.state.currentProject.getHeight() + "\n");
    pw.write(this.state.currentProject.getMaxValue() + "\n");
    for (int i = 0; i < this.state.currentProject.getLayers().size(); i++) {
      pw.write(this.state.currentProject.getLayers().get(i).toString() + " " + this.state.currentProject.getLayers().get(i).getCurrentFilter() + "\n");
      for (int k = 0; k < this.state.currentProject.getHeight(); k++) {
        for (int j = 0; j < this.state.currentProject.getWidth(); j++) {
          ArrayList<Integer> x = this.state.currentProject.getLayers().get(i).getPixels().get(k).get(j).getRGBA();
          pw.write(x.get(0) + " " + x.get(1) + " " + x.get(2) + " " + x.get(3) + " ");
        }
        pw.write("\n");
      }
    }
    pw.close();
    this.enterCommandStopper = true;
  }

  /**
   * A method that loads an existing project in. The user will be able to make
   * changes to this project if desired.
   * @param input represents the desired file the user loads in.
   */
  public void loadProject(String[] input) {
    if (input.length <= 1) {
      try {
        view.destination.append("File not found! Retry." + "\n");
        return;
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    Scanner scLoading;
    try {
      scLoading = new Scanner(new FileInputStream(input[1] + ".txt"));
    }
    catch (FileNotFoundException e) {
      try {
        view.destination.append("File not found! Retry." + "\n");
        return;
      }
      catch (Exception f) {
        throw new IllegalStateException(f.getMessage());
      }
    }
    StringBuilder builder = new StringBuilder();
    while (scLoading.hasNextLine()) {
      String s = scLoading.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }
    scLoading = new Scanner(builder.toString());
    String name;
    name = scLoading.next();
    if (!name.equals("C1")) {
      try {
        view.destination.append("Invalid collager file! choose a different file" + "\n");
        return;
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    int width = scLoading.nextInt();
    int height = scLoading.nextInt();
    int maxValue = scLoading.nextInt();
    ArrayList<Layer> layers = new ArrayList<Layer>();
    do {
      String layerName = scLoading.next();
      String filterName = scLoading.next();
      ArrayList<ArrayList<Pixel>> pixels = new ArrayList<ArrayList<Pixel>>();
      for (int i = 0; i < height; i++) {
        pixels.add(new ArrayList<Pixel>());
        for (int k = 0; k < width; k++) {
          int r = scLoading.nextInt();
          int g = scLoading.nextInt();
          int b = scLoading.nextInt();
          int a = scLoading.nextInt();
          pixels.get(i).add(new Pixel(r, g, b, a, this.state, this));
        }
      }
      layers.add(new Layer(pixels, layerName, filterName));
    } while (scLoading.hasNext());
    this.state.currentProject = new Project("C1", height, width, maxValue, layers, this.state, this);
    this.state.active = true;
  }

  /**
   * A method that allows a user to add a new layer to an existing project.
   * @param input represents the name of the layer.
   */
  public void addLayer(String[] input) {
    if (input.length > 1) {
      this.state.currentProject.addLayer(input[1]);
    }
    else {
      try {
        this.view.destination.append("Input a name alongside the command. Try again." + "\n");
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
  }

  /**
   * A method that saves an image to a directory given by the user.
   * @param input represents the name of the image the user wants to save
   *              with the file extension.
   */
  public void saveImage(String[] input) {
    if (input.length <= 1) {
      try {
        view.destination.append("No name detected. Please retry command with a name." + "\n");
        return;
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    if (input.length > 1) {
      this.state.currentProject.saveImage(input);
    }
  }

  /**
   * A method that adds an image to an existing layer.
   * @param splited represents the layerName, imageName, xPosition,
   *                and yPosition.
   */
  public void addImageToLayer(String[] splited) {
    String layerName;
    String imageName;
    int xPosition;
    int yPosition;
    if (splited.length == 5) {
      layerName = splited[1];
      imageName = splited[2];
      xPosition = Integer.valueOf(splited[3]);
      yPosition = Integer.valueOf(splited[4]);
      this.state.currentProject.addImageToLayer(layerName, imageName, xPosition, yPosition);
    }
    else {
      try {
        this.view.destination.append("Invalid command. Retry." + "\n");
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
  }

  /**
   * A method which changes the image to have the desired filter.
   * @param splited represents filterName and layerName, input by the user.
   */
  public void setFilter(String[] splited) {
    boolean validFilter = false;
    if (splited.length != 3) {
      try {
        this.view.destination.append("Invalid Arguments, Try Again.");
        return;
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    String layerName = splited[1];
    String filterOption = splited[2];
    for (int i = 0; i < this.state.possibleFilters.size(); i++) {
      if (this.state.possibleFilters.get(i).equals(filterOption)) {
        validFilter = true;
      }
    }
    if (!validFilter) {
      try {
        this.view.destination.append("Invalid Filter Option" + "\n");
        return;
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    this.state.currentProject.markFilter(layerName, filterOption);
  }
}
