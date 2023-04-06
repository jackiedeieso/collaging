import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import classes.PixelHSL;
import classes.PixelRGB;
import controller.CollagerController;
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
    Utils utils = new Utils(state, controller);
  }

  @Test
  public void testBlendDifference() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller);
    utils.possibleOptions("new-project 1000 1000");
    utils.possibleOptions("add-layer purple");
    utils.possibleOptions("add-layer mainImage");
    utils.possibleOptions("add-image-to-layer mainImage tako.ppm 0 0");
    utils.possibleOptions("add-image-to-layer purple PurpleSquare.ppm 0 0");
    utils.possibleOptions("save-image testBlendDifferenceBeforeFilter");
    utils.possibleOptions("set-filter mainImage blend-difference");
    utils.possibleOptions("save-image testBlendDifference.ppm");
  }

  @Test
  public void testBlendPixelMultiply() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller);
    utils.possibleOptions("new-project 100 100");
    utils.possibleOptions("add-layer purple");
    utils.possibleOptions("add-layer mainImage");
    utils.possibleOptions("add-image-to-layer purple purple.ppm 0 0");
    utils.possibleOptions("add-image-to-layer mainImage tako.ppm 0 0");
    utils.possibleOptions("set-filter mainImage blend-multiply");

  }

  @Test
  public void testSaveProject() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller);
    utils.possibleOptions("new-project 1000 1000");
    utils.possibleOptions("add-image-to-layer initial-layer tako.ppm 0 0");
    utils.possibleOptions("set-filter initial-layer red-component");
    utils.possibleOptions("save-project testHehe");
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).get(0).toString(), "(173, 179, 151, 255)");
    utils.possibleOptions("save-image testHehe.ppm");
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).get(0).toString(), "(173, 179, 151, 255)");
  }

  @Test
  public void testBlendMultiply() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller);
    utils.possibleOptions("new-project 1000 1000");
    utils.possibleOptions("add-layer purple");
    utils.possibleOptions("add-layer mainImage");

    utils.possibleOptions("add-image-to-layer mainImage tako.ppm 0 0");
    utils.possibleOptions("add-image-to-layer purple purpleSquare.ppm 0 300");

    utils.possibleOptions("set-filter mainImage blend-multiply");
    utils.possibleOptions("save-project testBlendMultiply");
    utils.possibleOptions("save-image testBlendMultiply.ppm");
  }

  @Test
  public void testHSLToRGB() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller);
    PixelRGB testPixel = new PixelRGB(147, 74, 150, state, controller);
    PixelHSL testConvert = new PixelHSL(testPixel, state, controller);
    assertEquals(new PixelRGB(testConvert, state, controller).toString(), "(147, 74, 150)");
  }
}
