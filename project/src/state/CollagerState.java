package state;

import java.util.ArrayList;

import classes.Layer;
import classes.Pixel;
import classes.Project;

public class CollagerState {
  public boolean active;

  public Project currentProject;

  public CollagerState() {
    this.active = false;
  }
}
