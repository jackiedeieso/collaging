package view;

import javax.swing.DefaultListModel;

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

  /**
   * This is the second constructor for the TextView class which is used for
   * the GUI.
   * @param state represents the current state of the collager.
   * @param collagerGUIBuffer represents the buffer that takes the program
   *                          output and sends it to the GUI text.
   * @param outputList represents the object that contains everything that is
   *                   presented on the screen for the log.
   */
  public TextView(CollagerState state, CollagerGUIBuffer collagerGUIBuffer,
                  DefaultListModel<String> outputList) {
    this.state = state;
    this.destination = collagerGUIBuffer;
    this.outputList = outputList;
  }
}
