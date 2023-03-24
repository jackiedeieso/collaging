package state;

import java.util.ArrayList;
import java.util.Arrays;

import classes.PixelRGB;
import classes.Project;

/**
 * Class representation that holds the current state of the game.
 */
public class CollagerState {
  public boolean active;
  public Project currentProject;
  public ArrayList<ArrayList<PixelRGB>> imageToBeAdded;

  public ArrayList<String> possibleFilters;

  /**
   * Constructor for CollageState that represents the initial state of
   * the game.
   */
  public CollagerState() {
    this.active = false;
    this.imageToBeAdded = new ArrayList<ArrayList<PixelRGB>>();
    this.possibleFilters = new ArrayList<String>(Arrays.asList(
            "normal", "red-component", "green-component", "blue-component",
            "brighten-value", "brighten-luma", "brighten-intensity",
                    "darken-value", "darken-luma", "darken-intensity", "blend-difference", "blend-multiply"));
  }
}
