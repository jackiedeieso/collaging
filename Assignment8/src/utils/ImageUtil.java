package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import classes.PixelRGB;
import model.CollagerModel;
import state.CollagerState;
import view.TextView;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 */
public class ImageUtil {

  CollagerState state;
  CollagerModel controller;

  TextView view;

  /**
   * This is the constructor for the ImageUtil class, which is used to scan
   * and import a PPM file.
   * @param state the current state of the game.
   * @param controller represents the directs the program.
   */
  public ImageUtil(CollagerState state, CollagerModel controller, TextView view) {
    this.state = state;
    this.controller = controller;
    this.view = view;
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
    ArrayList<ArrayList<PixelRGB>> convertedPixels = new ArrayList<ArrayList<PixelRGB>>();
    for (int i = 0; i < height; i++) {
      convertedPixels.add(new ArrayList<PixelRGB>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        convertedPixels.get(i).add(new PixelRGB(r, g, b, this.state, this.controller, this.view));
      }
    }
    this.state.imageToBeAdded = convertedPixels;
  }

  /**
   * used to import other types of images besides .ppm.
   * @param fileName is the name of the file being imported.
   */
  public void readImage(String fileName) {
    File file = new File(fileName);
    BufferedImage img;
    try {
      img = ImageIO.read(file);
    } catch (Exception e) {
      try {
        this.view.destination.append(e.getMessage());
        return;
      } catch (Exception ex) {
        throw new IllegalStateException(ex.getMessage());
      }
    }
    ArrayList<ArrayList<PixelRGB>> tempRGB = new ArrayList<ArrayList<PixelRGB>>();
    for (int i = 0; i < img.getHeight(); i++) {
      tempRGB.add(new ArrayList<PixelRGB>());
      for (int k = 0; k < img.getWidth(); k++) {
        int tempPixel = img.getRGB(k, i);
        int red = (tempPixel & 0xff0000) >> 16;
        int green = (tempPixel & 0xff00) >> 8;
        int blue = (tempPixel & 0xff);
        tempRGB.get(i).add(new PixelRGB(red, green, blue, this.state, this.controller, this.view));
      }
    }
    this.state.imageToBeAdded = tempRGB;
  }
}

