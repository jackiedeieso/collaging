import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import classes.PixelHSL;
import classes.PixelRGB;
import controller.CollagerController;
import secondpart.RepresentationConverter;
import state.CollagerState;
import utils.Utils;
import view.TextView;

/**
 * Class representation of the test class for
 * Assignment 5.
 */
public class Tests02 {
  Scanner sc;

  @Test
  public void testBlendPixelDifference() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    PixelRGB pix = new PixelRGB(100, 50, 100, 255, state, controller, view);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.blendPixelDifference(1, 3, 3);
    assertEquals(pix.toString(), "(100, 50, 100, 255)");
  }

  @Test
  public void testBlendDifference() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    utils.possibleOptions("new-project 1000 1000");
    utils.possibleOptions("add-layer purple");
    utils.possibleOptions("add-layer mainImage");
    utils.possibleOptions("add-image-to-layer mainImage tako.ppm 0 0");
    utils.possibleOptions("add-image-to-layer purple PurpleSquare.ppm 0 0");
    utils.possibleOptions("save-image testBlendDifferenceBeforeFilter.ppm");
    utils.possibleOptions("set-filter mainImage blend-difference");
    utils.possibleOptions("save-image testBlendDifference.ppm");
    Scanner regularScanner = new Scanner(new FileInputStream("testBlendDifferenceBeforeFilter.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner blendDifferenceScanner = new Scanner(new FileInputStream("testBlendDifference.ppm"));
    blendDifferenceScanner.next();
    blendDifferenceScanner.nextInt();
    blendDifferenceScanner.nextInt();
    blendDifferenceScanner.nextInt();
    int newRed = blendDifferenceScanner.nextInt();
    int newGreen = blendDifferenceScanner.nextInt();
    int newBlue = blendDifferenceScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 10);
    assertEquals(newGreen, 106);
    assertEquals(newBlue, 12);
  }

  @Test
  public void testBlendPixelMultiply() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    utils.possibleOptions("new-project 100 100");
    utils.possibleOptions("add-layer purple");
    utils.possibleOptions("add-layer mainImage");
    utils.possibleOptions("add-image-to-layer purple purple.ppm 0 0");
    utils.possibleOptions("add-image-to-layer mainImage pupper.ppm 0 0");
    utils.possibleOptions("set-filter mainImage blend-multiply");
    PixelRGB pixR = new PixelRGB(100, 50, 100, 255, state, controller, view);
    PixelHSL pix = new PixelHSL(pixR, state, controller, view);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.blendPixelMultiply(1, 3, 3);
    assertEquals(new PixelRGB(pix, state, controller).toString(), "(100, 50, 50, 255)");
  }

  @Test
  public void testSaveProject() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    utils.possibleOptions("new-project 1000 1000");
    utils.possibleOptions("add-image-to-layer initial-layer tako.ppm 0 0");
    utils.possibleOptions("set-filter initial-layer red-component");
    utils.possibleOptions("save-project testHehe");
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).get(0).toString(), "(173, 179, 151, 255)");
    utils.possibleOptions("save-image testHehe.ppm");
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).get(0).toString(), "(173, 179, 151, 255)");
  }

  @Test
  public void testBlendMultiply() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    utils.possibleOptions("new-project 100 100");
    utils.possibleOptions("add-layer purple");
    utils.possibleOptions("add-layer mainImage");
    utils.possibleOptions("add-image-to-layer mainImage tako.ppm 0 0");
    utils.possibleOptions("add-image-to-layer purple purpleSquare.ppm 0 300");
    utils.possibleOptions("save-image testBlendMultiplyBeforeFilter.ppm");
    utils.possibleOptions("set-filter mainImage blend-multiply");
    utils.possibleOptions("save-project testBlendMultiply");
    utils.possibleOptions("save-image testBlendMultiply.ppm");
    Scanner regularScanner = new Scanner(new FileInputStream("res/BlendMultiplyBeforeFilter.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner blendMultiplyScanner = new Scanner(new FileInputStream("res/stestBlendMultiply.ppm"));
    blendMultiplyScanner.next();
    blendMultiplyScanner.nextInt();
    blendMultiplyScanner.nextInt();
    blendMultiplyScanner.nextInt();
    int newRed = blendMultiplyScanner.nextInt();
    int newGreen = blendMultiplyScanner.nextInt();
    int newBlue = blendMultiplyScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 22);
    assertEquals(newGreen, 0);
    assertEquals(newBlue, 28);
  }

  @Test
  public void testBlendPixelScreen() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    utils.possibleOptions("new-project 100 100");
    utils.possibleOptions("add-layer purple");
    utils.possibleOptions("add-layer mainImage");
    utils.possibleOptions("add-image-to-layer purple purple.ppm 0 0");
    utils.possibleOptions("add-image-to-layer mainImage tako.ppm 0 0");
    utils.possibleOptions("set-filter mainImage blend-multiply");
    PixelRGB pixR = new PixelRGB(100, 50, 100, 255, state, controller, view);
    PixelHSL pix = new PixelHSL(pixR, state, controller, view);
    controller.makeNewProject("new-project 100 100".split(" "));
    pix.blendPixelScreen(1, 3, 3);
    assertEquals(new PixelRGB(pix, state, controller).toString(), "(100, 50, 50, 255)");
  }

  @Test
  public void testBlendScreen() throws FileNotFoundException {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    utils.possibleOptions("new-project 1000 1000");
    utils.possibleOptions("add-layer purple");
    utils.possibleOptions("add-layer mainImage");
    utils.possibleOptions("add-image-to-layer mainImage tako.ppm 0 0");
    utils.possibleOptions("add-image-to-layer purple purpleSquare.ppm 0 300");
    utils.possibleOptions("set-filter mainImage blend-screen");
    utils.possibleOptions("save-project testBlendScreen");
    utils.possibleOptions("save-image testBlendScreen.ppm");
    Scanner regularScanner = new Scanner(new FileInputStream("res/BlendScreenBeforeFilter.ppm"));
    regularScanner.next();
    regularScanner.nextInt();
    regularScanner.nextInt();
    regularScanner.nextInt();
    int oldRed = regularScanner.nextInt();
    int oldGreen = regularScanner.nextInt();
    int oldBlue = regularScanner.nextInt();
    Scanner blendScreenScanner = new Scanner(new FileInputStream("res/testBlendScreen.ppm"));
    blendScreenScanner.next();
    blendScreenScanner.nextInt();
    blendScreenScanner.nextInt();
    blendScreenScanner.nextInt();
    int newRed = blendScreenScanner.nextInt();
    int newGreen = blendScreenScanner.nextInt();
    int newBlue = blendScreenScanner.nextInt();
    assertEquals(oldRed, 173);
    assertEquals(oldGreen, 179);
    assertEquals(oldBlue, 151);
    assertEquals(newRed, 22);
    assertEquals(newGreen, 0);
    assertEquals(newBlue, 28);
  }

  @Test
  public void testHSLToRGB() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    RepresentationConverter converter = new RepresentationConverter();
    PixelRGB testPixel = new PixelRGB(147, 74, 150, state, controller, view);
    PixelHSL testConvert = new PixelHSL(testPixel, state, controller, view);
    assertEquals(new PixelRGB(testConvert, state, controller).toString(), "(147, 74, 150, 255)");
  }

  @Test
  public void testNewImage() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller, view);
    utils.possibleOptions("new-project 100 100");
    utils.possibleOptions("save-image testNewImage.ppm");
  }
}
