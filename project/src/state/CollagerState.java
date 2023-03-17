package state;
import java.util.ArrayList;
import classes.Layer;
import classes.Pixel;
import classes.Project;

/**
 * Class representation that holds the current state of the game.
 */
public class CollagerState {
  public boolean active;
  public Project currentProject;
  public ArrayList<ArrayList<Pixel>> imageToBeAdded;

  /**
   * Constructor for CollageState that represents the initial state of
   * the game.
   */
  public CollagerState() {
    this.active = false;
    this.imageToBeAdded = new ArrayList<ArrayList<Pixel>>();
  }
}
