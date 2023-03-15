package state;

import java.util.ArrayList;

import classes.Layer;
import classes.Pixel;
import classes.Project;

public class CollagerState {
  public boolean active;

  public Project currentProject;

  public ArrayList<ArrayList<Pixel>> imageToBeAdded;

  public CollagerState() {
    this.active = false;
    this.imageToBeAdded = new ArrayList<ArrayList<Pixel>>();
  }
}
