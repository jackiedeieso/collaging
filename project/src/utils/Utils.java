package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import classes.Pixel;
import controller.CollagerController;
import state.CollagerState;
import view.TextView;

public class Utils {
  public int maxValue;
  public CollagerState state;
  public CollagerController controller;

  public Utils(CollagerState state, CollagerController controller) {
    this.maxValue = 255;
    this.state = state;
    this.controller = controller;
  }
  public void possibleOptions(String response) {
    String[] splited = response.split(" ");
    if (splited[0].equals("new-project")) {
      this.controller.makeNewProject(splited);
    }
    if (splited[0].equals("save-project")) {
      this.controller.saveProject();
    }
    if (splited[0].equals("load-project")) {
      this.controller.loadProject(splited);
    }
    if (splited[0].equals("add-layer")) {
      this.controller.addLayer(splited);
    }
    if (splited[0].equals("save-image")) {
      this.controller.saveImage(splited);
    }
  }

  public void saveImageToFile(int height, int width, int maxValue, ArrayList<ArrayList<Pixel>> pixels) {
    TextView view = new TextView(this.state);
    File save;
    PrintWriter pw;
    Scanner sc = new Scanner(System.in);
    try {
      view.destination.append("Name the file" + "\n");
    }
    catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
    String fileName = sc.next();
    try {
      save = new File(fileName + ".ppm");
    }
    catch (Exception e) {
      try {
        view.destination.append("Please enter a valid name.");
        return;
      }
      catch (Exception f) {
        throw new IllegalStateException(f.getMessage());
      }
    }
    try {
      FileWriter fw = new FileWriter(save);
      pw = new PrintWriter(fw);
    }
    catch(Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
    pw.write("P3" + "\n");
    pw.write(width + " " + height + "\n");
    pw.write(maxValue + "\n");
    for (int i = 0; i < pixels.size(); i++) {
      for (int k = 0; k < pixels.get(0).size(); k++) {
        ArrayList<Integer> x = pixels.get(i).get(k).getRGBAConvertRGB();
        pw.write(x.get(0) + " " + x.get(1) + " " + x.get(2) + " ");
      }
      pw.write("\n");
    }
    pw.close();
  }
}
