package main;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import controller.CollagerController;
import state.CollagerState;
import utils.Utils;
import view.TextView;

/**
 * Class representation of the main.
 */
public class Main {
  /**
   * A method that is used to run the entirety of the program.
   * @param args represents arguments input by the user.
   * @throws IOException if there is nothing in args.
   */
  public static void main(String []args) throws IOException {
    if (args.length == 0) {
      System.out.println("Please put in Arguments.");
      System.out.println("Put user if you'd like to input commands manually.");
      System.out.println("Put commands if you'd like the commands to be input automatically");
    }
    else if (args[0].equals("commands") && args.length == 2) {
      Scanner sc = new Scanner(new FileInputStream(args[1]));
      CollagerState state = new CollagerState();
      TextView view = new TextView(state);
      CollagerController controller = new CollagerController(state, view, sc);
      Utils utils = new Utils(state, controller);
      String response = "";
      do {
        response = sc.nextLine();
        utils.possibleOptions(response);
      } while (!response.toLowerCase().equals("quit"));
    }
    else if (args[0].equals("user")) {
      CollagerState state = new CollagerState();
      Scanner sc = new Scanner(System.in);
      TextView view = new TextView(state);
      CollagerController controller = new CollagerController(state, view, sc);
      Utils utils = new Utils(state, controller);
      String response = "";
      do {
        if (controller.enterCommandStopper) {
          controller.enterCommandStopper = false;
        }
        else {
          view.destination.append("Enter Command \n");
        }
        response = sc.nextLine();
        utils.possibleOptions(response);
      } while (!response.toLowerCase().equals("quit"));
    }
    else {
      System.out.println("Please change Arguments.");
      System.out.println("Put user if you'd like to input commands manually.");
      System.out.println("Put commands followed by the file path if you'd like the commands to be input automatically");
    }
  }
}
