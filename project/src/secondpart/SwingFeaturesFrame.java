import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class opens the main window, that has different elements illustrated in
 * it. It also doubles up as all the listeners for simplicity. Such a design is
 * not recommended in general.
 */

public class SwingFeaturesFrame extends JFrame implements ActionListener, ItemListener, ListSelectionListener {
  private JPasswordField pfield;
  private JButton pButton;
  private JLabel pDisplay;
  private JPanel mainPanel;
  private JScrollPane mainScrollPane;

  private JLabel checkboxDisplay;
  private JLabel radioDisplay;
  private JLabel comboboxDisplay;
  private JLabel colorChooserDisplay;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JLabel inputDisplay;
  private JLabel optionDisplay;

  private JList<String> listOfStrings;
  private JList<Integer> listOfIntegers;

  public SwingFeaturesFrame() {
    super();
    setTitle("Swing features");
    setSize(400, 400);


    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //text area
    JTextArea textArea = new JTextArea(10, 20);
    textArea.setBorder(BorderFactory.createTitledBorder("Regular text area"));
    mainPanel.add(textArea);

    //text area with a scrollbar
    JTextArea sTextArea = new JTextArea(10, 20);
    JScrollPane scrollPane = new JScrollPane(sTextArea);
    sTextArea.setLineWrap(true);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Scrollable text area"));
    mainPanel.add(scrollPane);

    //password fields

    JPanel pPanel = new JPanel();
    pPanel.setBorder(BorderFactory.createTitledBorder("Using Password fields"));
    mainPanel.add(pPanel);

    pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.PAGE_AXIS));
    pfield = new JPasswordField(10);
    pPanel.add(pfield);
    pButton = new JButton("Echo password");
    pButton.addActionListener(this);
    pButton.setActionCommand("password button");
    pPanel.add(pButton);
    pDisplay = new JLabel("Password will appear here");
    pDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    pPanel.add(pDisplay);

    //checkboxes

    JPanel checkBoxPanel = new JPanel();
    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Checkboxes"));

    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));

    JCheckBox[] checkBoxes = new JCheckBox[5];
    ButtonGroup group = new ButtonGroup();
    for (int i = 0; i < checkBoxes.length; i++) {
      checkBoxes[i] = new JCheckBox("Option " + (i + 1));
      checkBoxes[i].setSelected(false);
      checkBoxes[i].setActionCommand("CB" + (i + 1));
      checkBoxes[i].addItemListener(this);
      //	group.add(checkBoxes[i]);
      checkBoxPanel.add(checkBoxes[i]);
    }
    checkboxDisplay = new JLabel("Which one did the user touch?");
    checkBoxPanel.add(checkboxDisplay);
    mainPanel.add(checkBoxPanel);


    //radio buttons
    JPanel radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Radio buttons"));

    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[5];

    //buttons groups are used to combine radio buttons. Only one radio
    // button in each group can be selected.
    ButtonGroup rGroup1 = new ButtonGroup();
    ButtonGroup rGroup2 = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton("Option " + (i + 1));
      //radioButtons[i].setSelected(false);

      radioButtons[i].setActionCommand("RB" + (i + 1));
      radioButtons[i].addActionListener(this);
      if (i < 2)
        rGroup1.add(radioButtons[i]);
      else
        rGroup2.add(radioButtons[i]);
      radioPanel.add(radioButtons[i]);

    }
    radioButtons[4].doClick();
    radioDisplay = new JLabel("Which one did the user select?");
    radioPanel.add(radioDisplay);
    mainPanel.add(radioPanel);

    //combo boxes

    JPanel comboboxPanel = new JPanel();
    comboboxPanel.setBorder(BorderFactory.createTitledBorder("Combo boxes"));
    comboboxPanel.setLayout(new BoxLayout(comboboxPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(comboboxPanel);

    comboboxDisplay = new JLabel("Cold Stone Creamery: Which size do you "
            + "want?");
    comboboxPanel.add(comboboxDisplay);
    String[] options = {"Like it", "Love it", "Gotta have it"};
    JComboBox<String> combobox = new JComboBox<String>();
    //the event listener when an option is selected
    combobox.setActionCommand("Size options");
    combobox.addActionListener(this);
    for (int i = 0; i < options.length; i++) {
      combobox.addItem(options[i]);
    }

    comboboxPanel.add(combobox);

    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    String[] images = {"Jellyfish.jpg", "Koala.jpg", "Penguins.jpg"};
    JLabel[] imageLabel = new JLabel[images.length+1];
    JScrollPane[] imageScrollPane = new JScrollPane[images.length+1];

    for (int i = 0; i < imageLabel.length; i++) {
      imageLabel[i] = new JLabel();
      imageScrollPane[i] = new JScrollPane(imageLabel[i]);
      if(i < images.length) {
	  imageLabel[i].setIcon(new ImageIcon(images[i]));
      } else {
	  imageLabel[i].setIcon(new ImageIcon(createImageFromScratch()));
      }
      imageScrollPane[i].setPreferredSize(new Dimension(100, 600));
      imagePanel.add(imageScrollPane[i]);
    }


    //Selection lists
    JPanel selectionListPanel = new JPanel();
    selectionListPanel.setBorder(BorderFactory.createTitledBorder("Selection lists"));
    selectionListPanel.setLayout(new BoxLayout(selectionListPanel, BoxLayout.X_AXIS));
    mainPanel.add(selectionListPanel);

    DefaultListModel<String> dataForListOfStrings = new DefaultListModel<>();
    dataForListOfStrings.addElement("Apple");
    dataForListOfStrings.addElement("Bear");
    dataForListOfStrings.addElement("Cave");
    dataForListOfStrings.addElement("Decorate");
    dataForListOfStrings.addElement("Exciting");
    dataForListOfStrings.addElement("Flicker");
    listOfStrings = new JList<>(dataForListOfStrings);
    listOfStrings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOfStrings.addListSelectionListener(this);
    selectionListPanel.add(listOfStrings);


    DefaultListModel<Integer> dataForListOfIntegers = new DefaultListModel<>();
    for (int i = 0; i < 1000; i++)
      dataForListOfIntegers.addElement(i);
    listOfIntegers = new JList<>(dataForListOfIntegers);
    listOfIntegers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOfIntegers.addListSelectionListener(this);
    selectionListPanel.add(new JScrollPane(listOfIntegers));


    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //color chooser
    JPanel colorChooserPanel = new JPanel();
    colorChooserPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(colorChooserPanel);
    JButton colorChooserButton = new JButton("Choose a color");
    colorChooserButton.setActionCommand("Color chooser");
    colorChooserButton.addActionListener(this);
    colorChooserPanel.add(colorChooserButton);
    colorChooserDisplay = new JLabel("      ");
    colorChooserDisplay.setOpaque(true); //so that background color shows up
    colorChooserDisplay.setBackground(Color.WHITE);
    colorChooserPanel.add(colorChooserDisplay);

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);

    //JOptionsPane message dialog
    JPanel messageDialogPanel = new JPanel();
    messageDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(messageDialogPanel);

    JButton messageButton = new JButton("Click for a message");
    messageButton.setActionCommand("Message");
    messageButton.addActionListener(this);
    messageDialogPanel.add(messageButton);

    //JOptionsPane input dialog
    JPanel inputDialogPanel = new JPanel();
    inputDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(inputDialogPanel);

    JButton inputButton = new JButton("Click to enter username");
    inputButton.setActionCommand("Input");
    inputButton.addActionListener(this);
    inputDialogPanel.add(inputButton);

    inputDisplay = new JLabel("Default");
    inputDialogPanel.add(inputDisplay);

    //JOptionsPane options dialog
    JPanel optionsDialogPanel = new JPanel();
    optionsDialogPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(optionsDialogPanel);

    JButton optionButton = new JButton("Click to enter options");
    optionButton.setActionCommand("Option");
    optionButton.addActionListener(this);
    optionsDialogPanel.add(optionButton);

    optionDisplay = new JLabel("Default");
    optionsDialogPanel.add(optionDisplay);


  }


  @Override
  public void actionPerformed(ActionEvent arg0) {
    // TODO Auto-generated method stub
    switch (arg0.getActionCommand()) {
      case "password button":
        pDisplay.setText(new String(pfield.getPassword()));
        pfield.setText("");
        break;
      case "RB1":
        radioDisplay.setText("Radio button 1 was selected");
        break;

      case "RB2":
        radioDisplay.setText("Radio button 2 was selected");
        break;

      case "RB3":
        radioDisplay.setText("Radio button 3 was selected");
        break;

      case "RB4":
        radioDisplay.setText("Radio button 4 was selected");
        break;

      case "Size options":
        if (arg0.getSource() instanceof JComboBox) {
          JComboBox<String> box = (JComboBox<String>) arg0.getSource();
          comboboxDisplay.setText("You selected: " + (String) box.getSelectedItem());


        }
        break;
      case "Color chooser":
        Color col = JColorChooser.showDialog(SwingFeaturesFrame.this, "Choose a color", colorChooserDisplay.getBackground());
        colorChooserDisplay.setBackground(col);
        break;
      case "Open file": {
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(SwingFeaturesFrame.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          fileOpenDisplay.setText(f.getAbsolutePath());
        }
      }
      break;
      case "Save file": {
        final JFileChooser fchooser = new JFileChooser(".");
        int retvalue = fchooser.showSaveDialog(SwingFeaturesFrame.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          fileSaveDisplay.setText(f.getAbsolutePath());
        }
      }
      break;
      case "Message":
        JOptionPane.showMessageDialog(SwingFeaturesFrame.this, "This is a demo message", "Message", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(SwingFeaturesFrame.this, "You are about to be deleted.", "Last Chance", JOptionPane.WARNING_MESSAGE);
        JOptionPane.showMessageDialog(SwingFeaturesFrame.this, "You have been deleted.", "Too late", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(SwingFeaturesFrame.this, "Please start again.", "What to do next", JOptionPane.INFORMATION_MESSAGE);
        break;
      case "Input":
        inputDisplay.setText(JOptionPane.showInputDialog("Please enter your username"));
        break;
      case "Option": {
        String[] options = {"Uno", "Dos", "Tres", "Cuatro", "Cinco", "seis", "siete", "ocho", "nueve", "dies"};
        int retvalue = JOptionPane.showOptionDialog(SwingFeaturesFrame.this, "Please choose number", "Options", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[4]);
        optionDisplay.setText(options[retvalue]);
      }
      break;
    }
  }

  @Override
  public void itemStateChanged(ItemEvent arg0) {
    // TODO Auto-generated method stub
    String who = ((JCheckBox) arg0.getItemSelectable()).getActionCommand();

    switch (who) {
      case "CB1":
        if (arg0.getStateChange() == ItemEvent.SELECTED) {
          checkboxDisplay.setText("Check box 1 was selected");
        } else {
          checkboxDisplay.setText("Check box 1 was deselected");
        }
        break;
      case "CB2":
        if (arg0.getStateChange() == ItemEvent.SELECTED) {
          checkboxDisplay.setText("Check box 2 was selected");
        } else {
          checkboxDisplay.setText("Check box 2 was deselected");
        }
        break;
      case "CB3":
        if (arg0.getStateChange() == ItemEvent.SELECTED) {
          checkboxDisplay.setText("Check box 3 was selected");
        } else {
          checkboxDisplay.setText("Check box 3 was deselected");
        }
        break;
      case "CB4":
        if (arg0.getStateChange() == ItemEvent.SELECTED) {
          checkboxDisplay.setText("Check box 4 was selected");
        } else {
          checkboxDisplay.setText("Check box 4 was deselected");
        }
        break;

      case "CB5":
        if (arg0.getStateChange() == ItemEvent.SELECTED) {
          checkboxDisplay.setText("Check box 5 was selected");
        } else {
          checkboxDisplay.setText("Check box 5 was deselected");
        }
        break;

    }
  }


    /*
     * Creates an image object for viewing such that each pixel's value is
     * represented is a single 32-bit number such that 8 bits of the
     * number correspond to a particular component of a 4-component
     * representation (R,G,B,A). The format of the number is below:
     *
     * AAAAAAAARRRRRRRRGGGGGGGGBBBBBBBB
     *   alpha    red    green   blue
     *
     * @return a new handcrafted image for display
     */
    
  private Image createImageFromScratch() {
      BufferedImage image = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);

      //Iterating so x moves to the right and y moves down
      for(int x = 0; x < image.getWidth(); x++) {
	  for(int y = 0; y < image.getHeight(); y++) {
	      // Making every pixel the color chosen by Section 2 of CS3500
	      // in Spring 2023
	      int r = 99;
	      int g = 137;
	      int b = 160;

	      //Demonstrating we can change the alpha of individual pixels
	      //and they will appear diffrently on the label
	      int a = 255;
	      if(y * image.getWidth() + x >= 35000) {
		  a = 100;
	      }
	      if (y * image.getWidth() +x >= 90000) {
		  a = 0;
	      }
	      if (y * image.getWidth() + x >= 110000) {
		  a = 255;
	      }

	      // Taking the 4 values and creating a single number
	      
	      // For Systems folks: this is a use of bit-packing
	      // It's a compact representation of a pixel made fast!
	      
	      // For everyone: Notice how this representation loses ALL
	      // meaning without any flexibility? Also how you need to know
	      // something about manipulating bits to get anything out of
	      // this? Maybe not the best to use...
	      int argb = a << 24;
	      argb |= r << 16;
	      argb |= g << 8;
	      argb |= b;
	      image.setRGB(x, y, argb);
	  }
      }
      return image;
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    // We don't know which list called this callback, because we're using it
    // for two lists.  In practice, you should use separate listeners
    JOptionPane.showMessageDialog(SwingFeaturesFrame.this,
            "The source object is " + e.getSource(), "Source", JOptionPane.PLAIN_MESSAGE);
    // Regardless, the event information tells us which index was selected
    JOptionPane.showMessageDialog(SwingFeaturesFrame.this,
            "The changing index is " + e.getFirstIndex(), "Index", JOptionPane.PLAIN_MESSAGE);
    // This gets us the string value that's currently selected
    JOptionPane.showMessageDialog(SwingFeaturesFrame.this,
            "The current string item is " + this.listOfStrings.getSelectedValue(), "Selected string", JOptionPane.PLAIN_MESSAGE);
    // This gets us the integer value that's currently selected
    JOptionPane.showMessageDialog(SwingFeaturesFrame.this,
            "The current number item is " + this.listOfIntegers.getSelectedValue(), "Selected integer", JOptionPane.PLAIN_MESSAGE);
  }
}
