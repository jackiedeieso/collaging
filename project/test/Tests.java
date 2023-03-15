import org.junit.Test;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import controller.CollagerController;
import state.CollagerState;
import utils.Utils;
import view.TextView;
import static org.junit.Assert.assertEquals;

public class Tests {

  @Test
  public void testState() {
    CollagerState state = new CollagerState();
    TextView view = new TextView(state);
    CollagerController controller = new CollagerController(state, view);
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
    CollagerController controller = new CollagerController(state, view);
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
    CollagerController controller = new CollagerController(state, view);
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
    CollagerController controller = new CollagerController(state, view);
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
    CollagerController controller = new CollagerController(state, view);
    Utils utils = new Utils(state, controller);
    utils.possibleOptions("new-project 20 10");
    utils.possibleOptions("add-layer dub");
    utils.possibleOptions("add-layer hello");
    utils.possibleOptions("save-image test");

    System.out.println(state.currentProject.getLayers().get(0).getPixels());
  }
}
