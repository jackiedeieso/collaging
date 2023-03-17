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

public class CollagerController {

  CollagerState state;

  TextView view;
  public CollagerController(CollagerState state, TextView view) {
    this.state = state;
    this.view = view;
  }

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
    this.state.currentProject = new Project("C1", height, width, this.state, this);
    this.state.currentProject.addInitialLayer();
    this.state.active = true;

  }

  public void saveProject() {
    File save;
    PrintWriter pw;
    Scanner sc = new Scanner(System.in);
    try {
      this.view.destination.append("Name the file. (no extension, just a name)" + "\n");
    }
    catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
    String fileName = sc.next();
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
      pw.write(this.state.currentProject.getLayers().get(i).toString() + "\n");
      for (int k = 0; k < this.state.currentProject.getHeight(); k++) {
        for (int j = 0; j < this.state.currentProject.getWidth(); j++) {
          ArrayList<Integer> x = this.state.currentProject.getLayers().get(i).getPixels().get(k).get(j).getRGBA();
          pw.write(x.get(0) + " " + x.get(1) + " " + x.get(2) + " " + x.get(3) + " ");
        }
        pw.write("\n");
      }
    }
    pw.close();

  }

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
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(input[1] + ".txt"));
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
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String name;
    name = sc.next();
    if (!name.equals("C1")) {
      try {
        view.destination.append("Invalid collager file! choose a different file" + "\n");
        return;
      }
      catch (Exception e) {
        throw new IllegalStateException(e.getMessage());
      }
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    ArrayList<Layer> layers = new ArrayList<Layer>();
    do {
      String layerName = sc.next();
      ArrayList<ArrayList<Pixel>> pixels = new ArrayList<ArrayList<Pixel>>();
      for (int i = 0; i < height; i++) {
        pixels.add(new ArrayList<Pixel>());
        for (int k = 0; k < width; k++) {
          int r = sc.nextInt();
          int g = sc.nextInt();
          int b = sc.nextInt();
          int a = sc.nextInt();
          pixels.get(i).add(new Pixel(r, g, b, a, this.state, this));
        }
      }
      layers.add(new Layer(pixels, layerName));
    } while (sc.hasNext());
    this.state.currentProject = new Project("C1", height, width, maxValue, layers, this.state, this);
    this.state.active = true;
  }

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

  public void setFilter(String[] splited) {
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
    this.state.currentProject.setFilter(layerName, filterOption);
  }
}
