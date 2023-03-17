import org.junit.Test;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import classes.Layer;
import classes.Pixel;
import controller.CollagerController;
import state.CollagerState;
import utils.Utils;
import view.TextView;
import static org.junit.Assert.assertEquals;

public class Tests {
  Scanner sc;

  public void defineScanner() {
    this.sc = new Scanner(System.in);
  }

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
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).get(0).getRGB(), new ArrayList<Integer>(Arrays.asList(255, 255, 255)));
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
    String response = "load-project layers.txt";
    Utils utils = new Utils(state, controller);
    assertEquals(state.currentProject, null);
    utils.possibleOptions(response);
    assertEquals(state.currentProject.getHeight(), 20);
    assertEquals(state.currentProject.getWidth(), 10);
    assertEquals(state.currentProject.getLayers().size(), 2);
    assertEquals(state.currentProject.getLayers().get(0).getPixels().size(), 20);
    assertEquals(state.currentProject.getLayers().get(0).getPixels().get(0).size(), 10);
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
  }

  @Test
  public void testSaveImage() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    Utils utils = new Utils(state, controller);
    utils.possibleOptions("new-project 20 10");
    utils.possibleOptions("add-layer dub");
    utils.possibleOptions("add-layer hello");
    utils.possibleOptions("save-image test");

    System.out.println(state.currentProject.getLayers().get(0).getPixels());
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
    assertEquals(state.currentProject.formula(pixel1, pixel2), new Pixel(173, 179, 151, 255, state, controller));
  }

  @Test
  public void testFindMax() {
    ArrayList<ArrayList<Pixel>> pix = new ArrayList<ArrayList<Pixel>>();
    Layer lay = new Layer(pix, "");
  }

  @Test
  public void testBrightenValue() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String input = "new-project 800 600";
    controller.makeNewProject(input.split(" "));
    input = "add-image-to-layer Initial sample.ppm 0 0";
    controller.addImageToLayer(input.split(" "));
    controller.setFilter("set-layer Initial brighten-value".split(" "));
    controller.saveImage("save-image brighten.ppm".split(" "));
  }

  @Test
  public void testBrightenLuma() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String input = "new-project 800 600";
    controller.makeNewProject(input.split(" "));
    input = "add-image-to-layer Initial sample.ppm 0 0";
    controller.addImageToLayer(input.split(" "));
    controller.setFilter("set-layer Initial brighten-luma".split(" "));
    controller.saveImage("save-image brightenLuma.ppm".split(" "));
  }

  @Test
  public void testBrightenIntensity() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String input = "new-project 800 600";
    controller.makeNewProject(input.split(" "));
    input = "add-image-to-layer Initial sample.ppm 0 0";
    controller.addImageToLayer(input.split(" "));
    controller.setFilter("set-layer Initial brighten-intensity".split(" "));
    controller.saveImage("save-image brightenIntensity.ppm".split(" "));
  }

  @Test
  public void testDarkenValue() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String input = "new-project 800 600";
    controller.makeNewProject(input.split(" "));
    input = "add-image-to-layer Initial sample.ppm 0 0";
    controller.addImageToLayer(input.split(" "));
    controller.setFilter("set-layer Initial darken-value".split(" "));
    controller.saveImage("save-image darken.ppm".split(" "));
  }

  @Test
  public void testDarkenLuma() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String input = "new-project 800 600";
    controller.makeNewProject(input.split(" "));
    input = "add-image-to-layer Initial sample.ppm 0 0";
    controller.addImageToLayer(input.split(" "));
    controller.setFilter("set-layer Initial darken-luma".split(" "));
    controller.saveImage("save-image darkenLuma.ppm".split(" "));
  }

  @Test
  public void testDarkenIntensity() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view, this.sc);
    String input = "new-project 800 600";
    controller.makeNewProject(input.split(" "));
    input = "add-image-to-layer Initial sample.ppm 0 0";
    controller.addImageToLayer(input.split(" "));
    controller.setFilter("set-layer Initial darken-intensity".split(" "));
    controller.saveImage("save-image darkenIntensity.ppm".split(" "));
  }

}
