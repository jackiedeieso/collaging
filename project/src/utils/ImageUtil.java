package utils;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import classes.Pixel;
import controller.CollagerController;
import state.CollagerState;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 */
public class ImageUtil {

  CollagerState state;
  CollagerController controller;

  /**
   * This is the constructor for the ImageUtil class, which is used to scan
   * and import a PPM file.
   * @param state the current state of the game.
   * @param controller represents the directs the program.
   */
  public ImageUtil(CollagerState state, CollagerController controller) {
    this.state = state;
    this.controller = controller;
  }

  /**
   * Read an image file in the PPM format and print the colors.
   * @param filename the path of the file.
   */
  public void readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      return;
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    ArrayList<ArrayList<Pixel>> convertedPixels = new ArrayList<ArrayList<Pixel>>();
    for (int i = 0; i < height; i++) {
      convertedPixels.add(new ArrayList<Pixel>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        convertedPixels.get(i).add(new Pixel(r, g, b, this.state, this.controller));
      }
    }
    this.state.imageToBeAdded = convertedPixels;
  }
}

