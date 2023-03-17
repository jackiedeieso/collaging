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

/**
 * class for Utils
 */
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
    boolean throwMessage = true;
    TextView view = new TextView(this.state);
    String[] splited = response.split(" ");
    if (splited[0].equals("new-project")) {
      this.controller.makeNewProject(splited);
      throwMessage = false;
    }
    if (splited[0].equals("load-project")) {
      this.controller.loadProject(splited);
      throwMessage = false;
    }
    if (splited[0].equals("quit")) {
      throwMessage = false;
    }
    if (this.state.active) {
      if (splited[0].equals("save-project")) {
        this.controller.saveProject();
      }
      if (splited[0].equals("add-layer")) {
        this.controller.addLayer(splited);
      }
      if (splited[0].equals("save-image")) {
        this.controller.saveImage(splited);
      }
      if (splited[0].equals("add-image-to-layer")) {
        this.controller.addImageToLayer(splited);
      }
      if (splited[0].equals("set-filter")) {
        this.controller.setFilter(splited);
      }
    }
    if (!this.state.active && throwMessage) {
      if (splited[0].equals("save-project") || splited[0].equals("add-layer")
              || splited[0].equals("save-image") || splited[0].equals("add-image-to-layer")) {
        try {
          view.destination.append("Cannot do command without importing or making a project." + "\n");
        }
        catch (Exception e) {
          throw new IllegalStateException(e.getMessage());
        }
      }
      else {
        try {
          view.destination.append("Unknown Command. Re-Type" + "\n");
        }
        catch (Exception e) {
          throw new IllegalStateException(e.getMessage());
        }
      }
    }
  }

  public void saveImageToFile(int height, int width, int maxValue, ArrayList<ArrayList<Pixel>> pixels, String name) {
    TextView view = new TextView(this.state);
    File save;
    PrintWriter pw;
    Scanner sc = new Scanner(System.in);
    String fileName = name;
    try {
      save = new File(fileName);
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
