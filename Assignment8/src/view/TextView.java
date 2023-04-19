package view;

import javax.swing.DefaultListModel;

import interfaces.ViewPrivacy;

/**
 * This is a class that represents the TextView, which allows the user to
 * read output.
 */
public class TextView implements ViewPrivacy {

  public Appendable destination;
  public DefaultListModel<String> outputList;

  /**
   * This is the constructor for the TextView class, which allows
   * a user to see output and allows for input.
   */
  public TextView() {
    this.destination = System.out;
  }

  /**
   * This is the second constructor for the TextView class which is used for
   * the GUI.
   * @param collagerGUIBuffer represents the buffer that takes the program
   *                          output and sends it to the GUI text.
   * @param outputList represents the object that contains everything that is
   *                   presented on the screen for the log.
   */
  public TextView(CollagerGUIBuffer collagerGUIBuffer,
                  DefaultListModel<String> outputList) {
    this.destination = collagerGUIBuffer;
    this.outputList = outputList;
  }

  public void communicate(String msg) {
    try {
      this.destination.append(msg);
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
