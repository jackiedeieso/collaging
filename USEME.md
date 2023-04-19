# How To Use The Collager GUI

* In order to open the Collager application in a text format, the user must type ***java -jar Collaging.jar -user***
* In order to open the Collager application as a GUI, the user must type ***java -jar Collaging.jar -user -GUI***
* In order to open the Collager application with given commands, the user must type ***java -jar Collaging.jar -commands file-path***
* Once the GUI is opened, the user will find that there is an open box on the left side, which represents where the project preview will be shown. In addition, on the right side, there are multiple buttons which allow a user to make a new projectt, load a project, add an image to a layer, add a layer, set a filter, save an image, and save a project. Finally, under these buttons there is a display of the layer, with the current layer highlighted. 
* When the user presses on one of the buttons, it creates a pop up window, that allows the user to type in what they want, depending on which button is pressed. 
* On the bottom, there is command output that shows the user if they type in the incorrect information for the given button. 
* The user can then create a new project, and if the project is too large for the space given to it, the user will be able to scroll in order to get a view of the entire image.
* When a layer is added, the layer is placed into a list, and you are able to access this list by pressing on the button ***Layer.***
* When you press on a layer in the list, and it is selected for 2 seconds, it will bring this layer to the top.
* The user is then able to edit the layer by putting a filter on it if they would like. 
* If the user desires to quit, they can press the quit button, which will close out the GUI window. 

# How To Sse The Collager Command Line
* new-project canvas-height canvas-width: Creates a new project file. The new project comes with one layer, which is named "initial-layer" and can be accessed with that name.

* load-project path-to-project-file: loads a .txt file with the right format into the program. (The project files are saved as .txt).

* save-project: saves a project in a .txt format. After running this command, the program asks for a name, this name will be used to name the project file.

* add-layer layer-name: adds a layer with the name that is given by the user. This layer automatically goes to the top of the layer-list.

* add-image-to-layer layer-name image-name x-pos y-pos: adds a ppm image to the given layer. The top left corner of the ppm is placed at (x-pos, y-pos).

* set-filter layer-name filter-option: This command marks a layer as having the given filter. When save-image is used, the filter is applied to the layer. The filters that can be used are normal, red-component, green-component, blue-component, brighten-value, brighten-intensity, brighten-luma, darken-value, darken-intensity, and darken-luma.

* save-image file-name: Saves the project as a ppm file with the given file-name. This method also applies all filters to their layers.

* quit: ends the program and quits out.


