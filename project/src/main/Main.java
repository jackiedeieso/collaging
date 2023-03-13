package main;

import java.io.IOException;
import java.util.Scanner;

import utils.Utils;
import view.TextView;

public class Main {
  public static void main(String []args) throws IOException {
    TextView view = new TextView();
    Utils utils = new Utils();
    Scanner sc = new Scanner(System.in);
    String response = "";
    do {
      view.destination.append("Enter Command \n");
      response = sc.nextLine();
      utils.possibleOptions(response);
    } while (!response.toLowerCase().equals("quit"));
  }
}
