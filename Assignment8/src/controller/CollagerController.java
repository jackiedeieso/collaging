package controller;

import javax.swing.text.View;

import interfaces.ModelPrivacy;
import interfaces.StatePrivacy;
import interfaces.ViewPrivacy;

public class CollagerController {

  ModelPrivacy model;

  StatePrivacy state;

  ViewPrivacy view;

  public CollagerController(ModelPrivacy model, StatePrivacy state, ViewPrivacy view) {
    this.model = model;
    this.state = state;
    this.view = view;
  }

  public void possibleOptions(String response) {
    boolean throwMessage = true;
    String[] splited = response.split(" ");
    if (splited[0].equals("new-project")) {
      this.model.makeNewProject(splited);
      throwMessage = false;
    }
    if (splited[0].equals("load-project")) {
      this.model.loadProject(splited);
      throwMessage = false;
    }
    if (splited[0].equals("quit")) {
      throwMessage = false;
    }
    if (this.state.getActiveState()) {
      if (splited[0].equals("save-project")) {
        this.model.saveProject(splited);
      }
      if (splited[0].equals("add-layer")) {
        this.model.addLayer(splited);
      }
      if (splited[0].equals("save-image")) {
        this.model.saveImage(splited);
      }
      if (splited[0].equals("add-image-to-layer")) {
        this.model.addImageToLayer(splited);
      }
      if (splited[0].equals("set-filter")) {
        this.model.setFilter(splited);
      }
    }
    if (!this.state.getActiveState() && throwMessage) {
      if (splited[0].equals("save-project") || splited[0].equals("add-layer")
              || splited[0].equals("save-image") || splited[0].equals("add-image-to-layer")) {
        this.view.communicate("Cannot do command without "
                + "importing or making a project." + "\n");
      } else {
        this.view.communicate("Unknown Command. Re-Type" + "\n");
      }
    }
  }
}
