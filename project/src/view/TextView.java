package view;

import javax.swing.*;

import state.CollagerState;

/**
 * This is a class that represents the TextView, which allows the user to
 * read output.
 */
public class TextView {
  public Appendable destination;

  public CollagerState state;
  public DefaultListModel<String> outputList;

  /**
   * This is the constructor for the TextView class, which allows
   * a user to see output and allows for input.
   * @param state represents the current state of the game.
   */
  public TextView(CollagerState state) {
    this.destination = System.out;
    this.state = state;
  }

  public TextView(CollagerState state, CollagerGUIBuffer collagerGUIBuffer, DefaultListModel<String> outputList) {
    this.state = state;
    this.destination = collagerGUIBuffer;
    this.outputList = outputList;
  }

}
