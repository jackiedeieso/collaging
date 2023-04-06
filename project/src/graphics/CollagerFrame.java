package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import classes.PixelRGB;
import controller.CollagerController;
import state.CollagerState;
import utils.Utils;
import view.TextView;

/**
 * A class representation of the Java Swing frame.
 */
public class CollagerFrame extends JFrame implements ActionListener {

  PreviewPanel picturePanel;
  JLabel pictureLabel;

  JPanel dialogBox;
  JButton newProjectButton;
  JButton loadProjectButton;
  JButton saveProjectButton;
  JButton addLayerButton;
  JButton addImageToLayerButton;
  JButton setFilterButton;
  JButton saveImageButton;
  JButton quitButton;
  JPanel commandPanel;
  JList<String> listOfResponses;
  DefaultListModel<String> outputList;
  TextView view;
  Utils utils;
  CollagerState state;
  CollagerController controller;

  /**
   * Constructor for the CollagerFrame class. Can be called when starting the GUI.
   * @param state
   * @param controller
   * @param utils
   * @param view
   * @param outputList
   */
  public CollagerFrame(CollagerState state, CollagerController controller, Utils utils, TextView view, DefaultListModel<String> outputList) {

    // Starter setup
    this.state = state;
    this.controller = controller;
    this.utils = utils;
    this.view = view;

    // frame settings
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1200, 950);
    this.setVisible(true);
    this.setLayout(new BorderLayout(2, 2));

    // picture panel
    this.picturePanel = new PreviewPanel(this.state);
    this.picturePanel.setSize(800, 800);
    this.add(picturePanel, BorderLayout.WEST);

    // commandPanel setup
    this.commandPanel = new JPanel();
    this.commandPanel.setLayout(null);
    this.commandPanel.setSize(280, 280);
    this.addCommandButtons();
    this.add(this.commandPanel);

    // output setup
    this.outputList = outputList;
    this.outputList.addElement("---Collager Responses---");
    this.dialogBox = new JPanel();
    this.listOfResponses = new JList<>(this.outputList);
    this.dialogBox.add(this.listOfResponses);
    this.dialogBox.setSize( 800, 100);
    this.add(this.dialogBox, BorderLayout.SOUTH);
    this.getContentPane();
    this.pack();
  }

  public void addCommandButtons() {

    // newProject button
    this.newProjectButton = new JButton();
    this.newProjectButton.setBounds(0, 0, 140, 70);
    this.newProjectButton.setText("new-project");
    this.newProjectButton.addActionListener(this);
    this.commandPanel.add(newProjectButton);

    // loadProject button
    this.loadProjectButton = new JButton();
    this.loadProjectButton.setBounds(140, 0, 140, 70);
    this.loadProjectButton.setText("load-project");
    this.loadProjectButton.addActionListener(this);
    this.commandPanel.add(loadProjectButton);

    // saveProject button
    this.saveProjectButton = new JButton();
    this.saveProjectButton.setBounds(0, 70, 140, 70);
    this.saveProjectButton.setText("save-project");
    this.saveProjectButton.addActionListener(this);
    this.commandPanel.add(saveProjectButton);

    // addLayer button
    this.addLayerButton = new JButton();
    this.addLayerButton.setBounds(140, 70, 140, 70);
    this.addLayerButton.setText("add-layer");
    this.addLayerButton.addActionListener(this);
    this.commandPanel.add(addLayerButton);

    // addImageToLayer button
    this.addImageToLayerButton = new JButton();
    this.addImageToLayerButton.setBounds(0, 140, 140, 70);
    this.addImageToLayerButton.setText("add-image-to-layer");
    this.addImageToLayerButton.addActionListener(this);
    this.commandPanel.add(addImageToLayerButton);

    // setFilter button
    this.setFilterButton = new JButton();
    this.setFilterButton.setBounds(140, 140, 140, 70);
    this.setFilterButton.setText("set-filter");
    this.setFilterButton.addActionListener(this);
    this.commandPanel.add(setFilterButton);

    // saveImage button
    this.saveImageButton = new JButton();
    this.saveImageButton.setBounds(0, 210, 140, 70);
    this.saveImageButton.setText("save-image");
    this.saveImageButton.addActionListener(this);
    this.commandPanel.add(saveImageButton);

    // quit button
    this.quitButton = new JButton();
    this.quitButton.setBounds(140, 210, 140, 70);
    this.quitButton.setText("quit");
    this.quitButton.addActionListener(this);
    this.commandPanel.add(quitButton);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource()==this.newProjectButton) {
      String heightWidth = JOptionPane.showInputDialog("Enter the Width and Height of the new project" + "\n"
              + "Format: 1000 1000");
      this.utils.possibleOptions("new-project " + heightWidth);
    }
    if (e.getSource()==this.loadProjectButton) {
      String projectPath = JOptionPane.showInputDialog("Enter the file path of the project");
      this.utils.possibleOptions("load-project " + projectPath);
    }
    if (e.getSource()==this.saveProjectButton) {
      String saveProjectPath = JOptionPane.showInputDialog("Enter the path you want to save the file");
      this.utils.possibleOptions("save-project " + saveProjectPath);
    }
    if (e.getSource()==this.addLayerButton) {
      String layerName = JOptionPane.showInputDialog("Enter the name of the new layer");
      this.utils.possibleOptions("add-layer " + layerName);
    }
    if (e.getSource()==this.addImageToLayerButton) {
      String commandBulk = JOptionPane.showInputDialog("Enter the image directory, the layer, and the x/y position"
              + "\n" + "Format: initial-layer tako.ppm 0 0");
      this.utils.possibleOptions("add-image-to-layer " + commandBulk);
    }
    if (e.getSource()==this.setFilterButton) {
      String commandBulk = JOptionPane.showInputDialog("Enter the layer name and the filter name"
              + "\n" + "Format: initial-layer red-component");
      this.utils.possibleOptions("set-filter " + commandBulk);
    }
    if (e.getSource()==this.saveImageButton) {
      String imageName = JOptionPane.showInputDialog("Enter the directory to save the image");
      this.utils.possibleOptions("save-image " + imageName);
    }
    if (e.getSource()==this.quitButton) {
      this.dispose();
    }
    if (this.state.active) {
      this.state.previewPixels = new ArrayList<ArrayList<PixelRGB>>();
      this.state.currentProject.forPreview = true;
      this.utils.possibleOptions("save-image preview");
      this.picturePanel.repaint();
      this.state.currentProject.forPreview = false;
    }


  }

}
