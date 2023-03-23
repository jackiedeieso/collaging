import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import classes.Pixel;
import classes.Project;
import controller.CollagerController;
import state.CollagerState;
import utils.ImageUtil;
import utils.Utils;
import view.TextView;
import static org.junit.Assert.assertEquals;

/**
 * Class representation of the tests used to check if the program is
 * running properly.
 */
public class Tests {
  Scanner sc;

  @Test
  public void testState() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    assertEquals(state.active, false);
    view.state.active = true;
    assertEquals(state.active, true);
    Utils utils = new Utils(state, controller);
    utils.state.active = false;
    assertEquals(state.active, false);
  }

  @Test
  public void testMakeNewProject() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String response = "new-project 400 500";
    Utils utils = new Utils(state, controller);
    assertEquals(state.currentProject, null);
    utils.possibleOptions(response);
    assertEquals(state.currentProject.toString(), "C1");
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).get(0).getRGB(),
            new ArrayList<Integer>(Arrays.asList(255, 255, 255)));
    assertEquals(state.currentProject.getLayers().size(), 1);
    assertEquals(state.currentProject.getLayers().get(0).getPixels().size(), 400);
    assertEquals(state.currentProject.getHeight(), 400);
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).size(), 500);
  }

  @Test
  public void testLoadProject() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String response = "load-project res/testLoadProject";
    assertEquals(state.currentProject, null);
    controller.loadProject(response.split(" "));
    assertEquals(state.currentProject.getHeight(), 20);
    assertEquals(state.currentProject.getWidth(), 10);
    assertEquals(state.currentProject.getLayers().size(), 2);
    assertEquals(state.currentProject.getLayers().get(0).getPixels().size(), 20);
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).size(), 10);
    assertEquals(state.currentProject.getLayers().get(0).toString(), "lay");
    assertEquals(state.currentProject.getLayers().get(1).toString(), "initial-layer");
  }

  @Test
  public void testAddLayer() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String response = "add-layer layer1";
    Utils utils = new Utils(state, controller);
    utils.possibleOptions("new-project 20 10");
    utils.possibleOptions(response);
    assertEquals(state.currentProject.getLayers().size(), 2);
    assertEquals(state.currentProject.getLayers().get(0).toString(), "layer1");
  }

  @Test
  public void testSaveImage() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/onlyRed".split(" "));
    controller.saveImage("save-image res/testSaveImage.ppm".split(" "));
    Scanner savedImageScanner = new Scanner(new FileInputStream("res/testSaveImage.ppm"));
    savedImageScanner.next();
    savedImageScanner.nextInt();
    savedImageScanner.nextInt();
    savedImageScanner.nextInt();
    assertEquals(savedImageScanner.nextInt(), 255);
    assertEquals(savedImageScanner.nextInt(), 0);
    assertEquals(savedImageScanner.nextInt(), 0);
    assertEquals(savedImageScanner.nextInt(), 255);
  }

  @Test
  public void testFormula() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller);
    utils.possibleOptions("new-project 1000 1000");
    Pixel pixel1 = new Pixel(173, 179, 151, 255, state, controller);
    Pixel pixel2 = new Pixel(155, 155, 155, 0, state, controller);
    assertEquals(state.currentProject.formula(pixel1, pixel2).toString(), "(173, 179, 151, 255)");
    pixel1 = new Pixel(173, 179, 151, 100, state, controller);
    pixel2 = new Pixel(155, 155, 155, 155, state, controller);
    assertEquals(state.currentProject.formula(pixel1, pixel2).toString(), "(164, 167, 152, 194)");
  }

  @Test
  public void testAddImageToLayer() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.makeNewProject("new-project 800 600".split(" "));
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).get(0).toString(),
            "(255, 255, 255, 255)");
    controller.addImageToLayer("add-image-to-layer initial-layer res/sample.ppm 0 0".split(" "));
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).get(0).toString(),
            "(173, 179, 151, 255)");
  }

  @Test
  public void testSetFilter() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.makeNewProject("new-project 800 600".split(" "));
    assertEquals(state.currentProject.getLayers().get(0).getCurrentFilter(), "normal");
    controller.setFilter("set-filter initial-layer red-component".split(" "));
    assertEquals(state.currentProject.getLayers().get(0).getCurrentFilter(), "red-component");
  }

  @Test
  public void testPossibleOptions() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils util = new Utils(state, controller);
    assertEquals(state.active, false);
    util.possibleOptions("new-project 100 100");
    assertEquals(state.active, true);
  }

  @Test
  public void testSaveImageToFile() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils util = new Utils(state, controller);
    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<ArrayList<Pixel>>();
    pixels.add(new ArrayList<Pixel>());
    pixels.get(0).add(new Pixel(100, 100, 100, 255, state, controller));
    util.saveImageToFile(1, 1, 255, pixels, "res/onePixelTest.ppm");
    Scanner saveImageScanner = new Scanner(new FileInputStream("res/onePixelTest.ppm"));
    saveImageScanner.next();
    saveImageScanner.nextInt();
    saveImageScanner.nextInt();
    saveImageScanner.nextInt();
    int red = saveImageScanner.nextInt();
    int green = saveImageScanner.nextInt();
    int blue = saveImageScanner.nextInt();
    assertEquals(red, 100);
    assertEquals(green, 100);
    assertEquals(blue, 100);
  }

  @Test
  public void getRGB() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(100, 100, 100, 255, state, controller);
    assertEquals(pix.getRGB(), new ArrayList<Integer>(Arrays.asList(100, 100, 100)));
  }

  @Test
  public void getRGBA() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(100, 100, 100, 255, state, controller);
    assertEquals(pix.getRGBA(), new ArrayList<Integer>(Arrays.asList(100, 100, 100, 255)));
  }

  @Test
  public void testRGBAConvertRGB() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(50, 60, 70, 100, state, controller);
    assertEquals(pix.getRGBAConvertRGB(), new ArrayList<Integer>(Arrays.asList(19, 23, 27)));
  }

  @Test
  public void testCheckRGBLimits() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(350, 60, 150, 255, state, controller);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.checkRGBLimits();
    assertEquals(pix.getRGB(), new ArrayList<Integer>(Arrays.asList(255, 60, 150)));
  }

  @Test
  public void testBrightenPixelValue() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(100, 50, 100, 255, state, controller);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.brightenPixelValue();
    assertEquals(pix.toString(), "(200, 150, 200, 255)");
  }

  @Test
  public void testBrightenPixelIntensity() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(100, 50, 100, 255, state, controller);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.brightenPixelIntensity();
    assertEquals(pix.toString(), "(183, 133, 183, 255)");
  }

  @Test
  public void testBrightenPixelLuma() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(20, 30, 40, 255, state, controller);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.brightenPixelLuma();
    assertEquals(pix.toString(), "(86, 96, 106, 255)");
  }

  @Test
  public void testDarkenPixelValue() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(30, 50, 100, 255, state, controller);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.darkenPixelValue();
    assertEquals(pix.toString(), "(0, 20, 70, 255)");
  }

  @Test
  public void testDarkenPixelIntensity() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(30, 50, 100, 255, state, controller);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.darkenPixelIntensity();
    assertEquals(pix.toString(), "(0, 0, 40, 255)");
  }

  @Test
  public void testDarkenPixelLuma() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Pixel pix = new Pixel(150, 20, 100, 255, state, controller);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.darkenPixelLuma();
    assertEquals(pix.toString(), "(15, 0, 0, 255)");
  }

  @Test
  public void testAddInitialLayer() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    state.currentProject = new Project("test", 100, 100, state, controller);
    state.currentProject.addInitialLayer();
    assertEquals(state.currentProject.getLayers().get(0).toString(), "initial-layer");
  }

  @Test
  public void testMarkFilter() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.makeNewProject("new-project 100 100".split(" "));
    assertEquals(state.currentProject.getLayers().get(0).getCurrentFilter(), "normal");
    state.currentProject.markFilter("initial-layer", "red-component");
    assertEquals(state.currentProject.getLayers().get(0).getCurrentFilter(), "red-component");
  }

  @Test
  public void testReadPPM() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    ImageUtil imageUtils = new ImageUtil(state, controller);
    imageUtils.readPPM("res/sample.ppm");
    assertEquals(state.imageToBeAdded.get(0).get(0).toString(), "(173, 179, 151, 255)");
  }

  @Test
  public void testRedComponent() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer red-component".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    assertEquals(regularScanner.nextInt(), 173);
    assertEquals(regularScanner.nextInt(), 0);
    assertEquals(regularScanner.nextInt(), 0);
  }

  @Test
  public void testGreenComponent() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer green-component".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    assertEquals(regularScanner.nextInt(), 0);
    assertEquals(regularScanner.nextInt(), 179);
    assertEquals(regularScanner.nextInt(), 0);
  }

  @Test
  public void testBlueComponent() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer blue-component".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    assertEquals(regularScanner.nextInt(), 0);
    assertEquals(regularScanner.nextInt(), 0);
    assertEquals(regularScanner.nextInt(), 151);
  }

  @Test
  public void testBrightenValue() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer brighten-value".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/sample.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner brightenValueScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    brightenValueScanner.next();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    int newRed = brightenValueScanner.nextInt();
    int newGreen = brightenValueScanner.nextInt();
    int newBlue = brightenValueScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 255);
    assertEquals(newGreen, 255);
    assertEquals(newBlue, 255);
  }

  @Test
  public void testBrightenLuma() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer brighten-luma".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/sample.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner brightenValueScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    brightenValueScanner.next();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    int newRed = brightenValueScanner.nextInt();
    int newGreen = brightenValueScanner.nextInt();
    int newBlue = brightenValueScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 255);
    assertEquals(newGreen, 255);
    assertEquals(newBlue, 255);
  }

  @Test
  public void testBrightenIntensity() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer brighten-intensity".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/sample.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner brightenValueScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    brightenValueScanner.next();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    int newRed = brightenValueScanner.nextInt();
    int newGreen = brightenValueScanner.nextInt();
    int newBlue = brightenValueScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 255);
    assertEquals(newGreen, 255);
    assertEquals(newBlue, 255);
  }

  @Test
  public void testDarkenValue() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer darken-value".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/sample.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner brightenValueScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    brightenValueScanner.next();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    int newRed = brightenValueScanner.nextInt();
    int newGreen = brightenValueScanner.nextInt();
    int newBlue = brightenValueScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 22);
    assertEquals(newGreen, 28);
    assertEquals(newBlue, 0);
  }

  @Test
  public void testDarkenLuma() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer darken-luma".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/sample.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner brightenValueScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    brightenValueScanner.next();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    int newRed = brightenValueScanner.nextInt();
    int newGreen = brightenValueScanner.nextInt();
    int newBlue = brightenValueScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 0);
    assertEquals(newGreen, 0);
    assertEquals(newBlue, 0);
  }

  @Test
  public void testDarkenIntensity() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    controller.loadProject("load-project res/testerProject".split(" "));
    controller.setFilter("set-filter initial-layer darken-intensity".split(" "));
    controller.saveImage("save-image res/testProject.ppm".split(" "));
    Scanner regularScanner = new Scanner(new FileInputStream("res/sample.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner brightenValueScanner = new Scanner(new FileInputStream("res/testProject.ppm"));
    brightenValueScanner.next();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    brightenValueScanner.nextInt();
    int newRed = brightenValueScanner.nextInt();
    int newGreen = brightenValueScanner.nextInt();
    int newBlue = brightenValueScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 6);
    assertEquals(newGreen, 12);
    assertEquals(newBlue, 0);
  }
}
