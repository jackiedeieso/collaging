package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.image.BufferedImage;

import state.CollagerState;

/**
 * A class representation of a panel in the collager GUI.
 */
public class PreviewPanel extends Panel {
  boolean previewReady;
  private CollagerState state;

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
   * A method that is used to load a given PPM into the GUI.
   * @param g the specified Graphics window.
   */
  public void paint(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;
    if (this.state.previewPixels.size() > 0) {
      BufferedImage img = new BufferedImage(this.state.previewPixels
              .get(0).size(), this.state.previewPixels.size(), BufferedImage.TYPE_INT_RGB);
      for (int i = 0; i < this.state.previewPixels.size(); i++) {
        for (int k = 0; k < this.state.previewPixels.get(i).size(); k++) {
          Color tempColor = new Color(
                  this.state.previewPixels.get(i).get(k).getColorInt("Red"),
                  this.state.previewPixels.get(i).get(k).getColorInt("Green"),
                  this.state.previewPixels.get(i).get(k).getColorInt("Blue"));
          img.setRGB(k, i, tempColor.getRGB());
        }
      }
      g2D.drawImage(img, 0, 0, null);

    }
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
