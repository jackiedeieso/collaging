package view;

import classes.Project;

public class TextView {
  public Appendable destination;

  public TextView(Project p) {
    this.destination = System.out;
  }

  public TextView() {
    this.destination = System.out;
  }
}
