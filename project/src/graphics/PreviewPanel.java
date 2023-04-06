package graphics;

import java.awt.*;

import javax.swing.*;

import state.CollagerState;

public class PreviewPanel extends Panel {

  boolean previewReady;

  CollagerState state;

  PreviewPanel(CollagerState state){
    this.previewReady = false;
    this.state = state;
    // make picture preview panel.
    this.setBounds(10, 30, 800, 800);
    this.setLayout(null);


  }

  public void paint(Graphics g) {
    Graphics2D g2D = (Graphics2D) g;
    if (this.state.previewPixels.size() > 0) {
      for (int i = 0; i < this.state.previewPixels.size(); i++) {
        for (int k = 0; k < this.state.previewPixels.get(i).size(); k++) {
          Color tempColor = new Color(
                  this.state.previewPixels.get(k).get(i).getColorInt("Red"),
                  this.state.previewPixels.get(k).get(i).getColorInt("Green"),
                  this.state.previewPixels.get(k).get(i).getColorInt("Blue"));
          g2D.setColor(tempColor);
          g2D.fillRect(i, k, 1, 1);
        }
      }
    }
  }

}
