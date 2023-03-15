package main;

import java.io.IOException;
import java.util.Scanner;

import controller.CollagerController;
import state.CollagerState;
import utils.Utils;
import view.TextView;


public class Main {
  public static void main(String []args) throws IOException {
    CollagerState state = new CollagerState();
    Scanner sc = new Scanner(System.in);
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view);
    Utils utils = new Utils(state, controller);
    String response = "";
    do {
      view.destination.append("Enter Command \n");
      response = sc.nextLine();
      utils.possibleOptions(response);
    } while (!response.toLowerCase().equals("quit"));
  }
}
