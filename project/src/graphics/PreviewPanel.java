package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.image.BufferedImage;

import javax.swing.*;

import state.CollagerState;

/**
 * A class representation of a panel in the collager GUI.
 */
public class PreviewPanel extends Panel {
  boolean previewReady;
  CollagerState state;

  /**
   * Constructor for the PreviewPanel class. This is used to
   * intialize the current state of the collager.
   * @param state represents the current state of the collager.
   */
  PreviewPanel(CollagerState state) {
    super();
    this.previewReady = false;
    this.state = state;
    // make picture preview panel.
    this.setVisible(true);
    this.setPreferredSize(new Dimension(4000, 4000));
  }

  /**
   * A method that is used to change the size of the scroll frame based
   * on the size of the project.
   */
  public void changeSize() {
    this.repaint();
    this.revalidate();
  }
}
