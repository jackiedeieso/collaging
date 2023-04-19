package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import classes.Layer;
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
  JScrollPane jp;

  JLabel imageLabel;

  ImageIcon img;
  JPanel mainPanel;
  JPanel dialogBox;
  JButton newProjectButton;
  JButton loadProjectButton;
  JButton saveProjectButton;
  JButton addLayerButton;
  JButton addImageToLayerButton;
  JButton setFilterButton;
  JButton saveImageButton;
  JButton quitButton;

  JButton layerButton;
  JPanel commandPanel;
  JList<String> listOfResponses;
  DefaultListModel<String> outputList;
  TextView view;
  Utils utils;
  CollagerState state;
  CollagerController controller;

  /**
   * Constructor for the class CollagerFrame. Allows for initialization
   * of significant parameters for starting a GUI.
   * @param state represents the current state of the collager.
   * @param controller represents the controller class that run methods for the main.
   * @param utils represents the utility class.
   * @param view represents the current view of the collager.
   * @param outputList represents where the view sends its response for the GUI.
   */
  public CollagerFrame(CollagerState state, CollagerController controller,
                       Utils utils, TextView view, DefaultListModel<String> outputList) {
    super();
    // Starter setup
    this.state = state;
    this.controller = controller;
    this.utils = utils;
    this.view = view;
    this.mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(2, 2));

    // frame settings
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1200, 950);
    this.setTitle("Collager");
    this.setVisible(true);
    this.setLayout(new BorderLayout(0, 0));

    // picture panel
    JPanel holderPanel = new JPanel();
    holderPanel.setPreferredSize(new Dimension(800, 800));
    holderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    holderPanel.setVisible(true);
    holderPanel.setBackground(Color.WHITE);

    // scroll box
    this.picturePanel = new PreviewPanel(this.state);
    this.img = new ImageIcon();
    this.imageLabel = new JLabel(this.img);
    this.imageLabel.setPreferredSize(new Dimension(4000, 4000));
    this.imageLabel.setVisible(true);
    this.jp = new JScrollPane(this.imageLabel);
    this.jp.setPreferredSize(new Dimension(750, 750));
    this.jp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.jp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    holderPanel.add(this.jp);
    this.jp.setVisible(true);
    this.mainPanel.add(holderPanel, BorderLayout.WEST);

    // commandPanel setup
    JPanel rightSidePanel = new JPanel();
    rightSidePanel.setLayout(new BorderLayout());
    this.commandPanel = new JPanel();
    this.commandPanel.setLayout(null);
    this.commandPanel.setSize(280, 280);
    this.commandPanel.setVisible(true);
    this.addCommandButtons();
    rightSidePanel.add(this.commandPanel);
    rightSidePanel.setVisible(true);
    rightSidePanel.setPreferredSize(new Dimension(280, 800));
    this.mainPanel.add(rightSidePanel, BorderLayout.EAST);

    // output setup
    this.outputList = outputList;
    this.outputList.addElement("---Collager Responses---");
    this.dialogBox = new JPanel();
    this.listOfResponses = new JList<>(this.outputList);
    this.dialogBox.add(this.listOfResponses);
    this.dialogBox.setPreferredSize(new Dimension(800, 100));
    this.dialogBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.mainPanel.add(this.dialogBox, BorderLayout.SOUTH);
    this.add(this.mainPanel);
    this.getContentPane();
    this.pack();
  }


  public void paintVoid() {
    if (this.state.previewPixels.size() > 0) {
      BufferedImage tempImage = new BufferedImage(this.state.previewPixels
              .get(0).size(), this.state.previewPixels.size(), BufferedImage.TYPE_INT_RGB);
      for (int i = 0; i < this.state.previewPixels.size(); i++) {
        for (int k = 0; k < this.state.previewPixels.get(i).size(); k++) {
          Color tempColor = new Color(
                  this.state.previewPixels.get(i).get(k).getColorInt("Red"),
                  this.state.previewPixels.get(i).get(k).getColorInt("Green"),
                  this.state.previewPixels.get(i).get(k).getColorInt("Blue"));
          tempImage.setRGB(k, i, tempColor.getRGB());
        }
        this.imageLabel.setIcon(new ImageIcon(tempImage));
        this.imageLabel.revalidate();
        this.revalidate();
      }
    }
  }

  /**
   * A method that is used to assign a task to each button, which allows
   * for proper use of the buttons displayed in the GUI.
   */
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

    // choose filter button
    this.layerButton = new JButton();
    this.layerButton.setBounds(0, 280, 280, 70);
    this.layerButton.setText("layers");
    this.layerButton.addActionListener(this);
    this.commandPanel.add(this.layerButton);
  }

  class ListSelectionHandler implements ListSelectionListener {

    int selectedLayer;

    CollagerState state;

    PreviewPanel picturePanel;

    Utils utils;

    JFrame x;

    public ListSelectionHandler(CollagerState state, PreviewPanel picturePanel,
                                Utils utils, JFrame x) {
      this.state = state;
      this.picturePanel = picturePanel;
      this.utils = utils;
      this.x = x;
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */

    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (!e.getValueIsAdjusting()) {
        this.selectedLayer = e.getFirstIndex();
        Layer tempLayer = this.state.currentProject.getLayers().get(this.selectedLayer);
        this.state.currentProject.getLayers().remove(this.selectedLayer);
        this.state.currentProject.getLayers().add(0, tempLayer);
        this.state.currentProject.forPreview = true;
        this.utils.possibleOptions("save-image preview");
        this.state.currentProject.forPreview = false;
        x.dispose();
      }
    }
  }

  /**
   * A method that listens for events, and allows the program
   * to perform specific tasks based on these events.
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.newProjectButton) {
      String heightWidth = JOptionPane.showInputDialog(
              "Enter the Width and Height of the new project" + "\n"
              + "Format: 1000 1000");
      this.utils.possibleOptions("new-project " + heightWidth);
    }
    if (e.getSource() == this.loadProjectButton) {
      String projectPath = JOptionPane.showInputDialog("Enter the file path of the project");
      this.utils.possibleOptions("load-project " + projectPath);
    }
    if (e.getSource() == this.saveProjectButton) {
      String saveProjectPath = JOptionPane.showInputDialog(
              "Enter the path you want to save the file");
      this.utils.possibleOptions("save-project " + saveProjectPath);
    }
    if (e.getSource() == this.addLayerButton) {
      String layerName = JOptionPane.showInputDialog("Enter the name of the new layer");
      this.utils.possibleOptions("add-layer " + layerName);
    }
    if (e.getSource() == this.addImageToLayerButton) {
      String commandBulk = JOptionPane.showInputDialog(
              "Enter the image directory, the layer, and the x/y position"
              + "\n" + "Format: initial-layer tako.ppm 0 0");
      this.utils.possibleOptions("add-image-to-layer " + commandBulk);
    }
    if (e.getSource() == this.setFilterButton) {
      String commandBulk = JOptionPane.showInputDialog("Enter the layer name and the filter name"
              + "\n" + "Format: initial-layer red-component");
      this.utils.possibleOptions("set-filter " + commandBulk);
    }
    if (e.getSource() == this.saveImageButton) {
      String imageName = JOptionPane.showInputDialog("Enter the directory to save the image");
      this.utils.possibleOptions("save-image " + imageName);
    }
    if (e.getSource() == this.quitButton) {
      this.dispose();
    }
    if (e.getSource() == this.layerButton) {
      if (this.state.active) {
        JFrame x = new JFrame();
        JPanel y = new JPanel();
        x.setVisible(true);
        x.setPreferredSize(new Dimension(300, 300));
        DefaultListModel<String> layerNames = new DefaultListModel<>();
        for (int i = 0; i < this.state.currentProject.getLayers().size(); i++) {
          layerNames.add(i, this.state.currentProject.getLayers().get(i).toString());
        }
        JList<String> jList = new JList<>(layerNames);
        JScrollPane jScrollPane = new JScrollPane(jList);
        jList.setVisible(true);
        ListSelectionHandler handler = new ListSelectionHandler(this.state,
                this.picturePanel, this.utils, x);
        jList.addListSelectionListener(handler);
        y.setPreferredSize(new Dimension(300, 300));
        y.setVisible(true);
        y.add(jList);
        x.add(y);
        x.getContentPane();
        x.pack();
      } else {
        try {
          this.view.destination.append("Must create project before editing layers.");
        } catch (Exception ex) {
          throw new IllegalStateException(ex.getMessage());
        }
      }
    }
    if (this.state.active) {
      this.state.previewPixels = new ArrayList<ArrayList<PixelRGB>>();
      this.state.currentProject.forPreview = true;
      this.utils.possibleOptions("save-image preview");
      this.picturePanel.changeSize();
      this.state.currentProject.forPreview = false;
      this.paintVoid();
      this.repaint();
      this.revalidate();
      this.jp.revalidate();
    }
  }
}
