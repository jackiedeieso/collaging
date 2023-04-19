package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.DefaultListModel;

import model.CollagerModel;
import state.CollagerState;
import utils.Utils;
import view.CollagerGUIBuffer;
import view.TextView;

/**
 * Class representation of the main.
 */
public class Main {
  /**
   * A method that is used to run the entirety of the program.
   *
   * @param args represents arguments input by the user.
   * @throws IOException if there is nothing in args.
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("Please put in Arguments.");
      System.out.println("Put user if you'd like to input commands manually.");
      System.out.println("Put commands if you'd like "
              + "the commands to be input automatically");
    } else if (args[0].equals("-commands") && args.length == 2) {
      CollagerState commandState = new CollagerState();
      TextView commandView = new TextView();
      Scanner sc = new Scanner(new FileInputStream(args[1]));
      CollagerModel commandController = new CollagerModel(commandState, commandView, sc);
      Utils utils = new Utils(commandState, commandController, commandView);
      String response = "";
      do {
        response = sc.nextLine();
        utils.possibleOptions(response);
      }
      while (!response.equalsIgnoreCase("quit"));
    } else if (args[0].equals("-user")) {
      Scanner sc = new Scanner(System.in);
      if (args.length == 2) {
        if (args[1].equals("-GUI")) {
          CollagerState guiState = new CollagerState();
          DefaultListModel<String> outputList = new DefaultListModel<>();
          CollagerGUIBuffer guiBuffer = new CollagerGUIBuffer(outputList);
          TextView guiTextView = new TextView(guiBuffer, outputList);
          CollagerModel guiController = new CollagerModel(guiState, guiTextView, sc);
          Utils guiUtils = new Utils(guiState, guiController, guiTextView);
          guiUtils.startGUI(guiState, guiController, guiTextView, outputList);
          return;
        }
      }
      if (args.length == 1) {
        CollagerState textState = new CollagerState();
        TextView textView = new TextView();
        CollagerModel textController = new CollagerModel(textState, textView, sc);
        Utils textUtils = new Utils(textState, textController, textView);
        String response = "";
        do {
          if (textController.enterCommandStopper) {
            textController.enterCommandStopper = false;
          } else {
            textView.destination.append("Enter Command \n");
          }
          response = sc.nextLine();
          textUtils.possibleOptions(response);
        }
        while (!response.equalsIgnoreCase("quit"));
      }
    } else {
      System.out.println("Please change Arguments.");
      System.out.println("Put user if you'd like to input commands manually.");
      System.out.println("Put commands followed by the file path if you'd like \" +\n"
              + "\"the commands to be input automatically");
    }
  }
}
