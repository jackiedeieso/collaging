package view;

import classes.Project;
import state.CollagerState;

public class TextView {
  public Appendable destination;
  public CollagerState state;

  public TextView(CollagerState state) {
    this.destination = System.out;
    this.state = state;
  }
}
