# Assignment 4: Collager
For this assignment, we were asked to create a collager, which would allow a user to interact with an application similar to Gimp or Photoshop. The user is able to create a new project, add layers to this project, save the project, quit, and then reopen it at a later time if desired. In addition, when an image is added, the user is able to add a filter onto the image. The filters include: red-component, green-component, blue-component, brighten-value, brighten-intensity, brighten-luma, darken-value, darken-intensity, and darken-luma. 

# Design
In order to create a proper design for this assignment, we considered various components that may be added in the future, and that would be important to abstract to avoid issues with our program in the future. This program has been designed so that it can be added to at every step. Starting with the main, the user inputs their desired command, which is then sent to the utils class so the right method can be called on the current project. Due to this, methods and different commands can be added. The CollagerController class communicates with the currentProject field. This field exists in the state class. The CollagerState class is designed as a holder for important data throughout the program. The CollagerState class allows for any of the other classes to access and make edits to the currentProject. This design is useful because it allows the program to make edits to the project with ease. The layer class holds the filterType and applies it when the image is saved. This is necessary for the project to load properly and for the filter to be changed. If the filter was applied immediately, it would be much harder to revert or change the filter because the RGB values had already been changed. By only applying the filter when the final image is being saved, it is much easier to load projects and change the filter settings on each layer.

# Classes & Interfaces 
* Layer: The class Layer is used as a representation of a layer in a project. There is an initial layer when a new project is created - titled initial-layer - which represents the bottom-most layer in the project. This layer has a unique height and width input by the user. The first layer is then created, which is simply a plain white background. After the initial creation of the intial layer, the user is able to add layers. When a user creates their own layer, they are able to give it a unique name. This class is constructed such that it has two constructors, one for the initial layer of the game - which will be the same every time, except for width and height - and one for when the users wants to create a new layer. This allows for structure within the initial layer, and flexibilty in the unique layers.

* PixelRGB: The class Pixel is used as a representation for an RGB or RGBA Pixel in the project. There are two different types of Pixels, three-component pixels and four-component pixels. For three component pixels, there is a red value, a green value, and a blue value. For four-component Pixels, it is RGB values, with an additional value: the Alpha value. This value determines how transparent a given Pixel is. In this class, there are three different constructors. The first constructor is made for three-component pixels, and simply sets the alpha value to 255. The second constructor is used for four-component pixels, and allows for a unique alpha value to be interpreted. For the last constructor, we decided to create an empty constructor. We were experiencing a bug, and this got rid of the bug. 

* PixelHSL: The class PixelHSL is used as a representation for a Pixel with HSL values. HSL stands for hue, saturation, and lightness, and is another way of storing information in a pixel. The hue value is betweeen 0 and 360, while the saturation and brightnesss values are from 0 to 1. The first contructor in this class is used for means of calling the state, controller, and Utils classes. The second constructor is used to convert a RGB Pixel into an HSL Pixel. This is meant for specific filters that are applied using the HSL format. 

* Project: The class Project is a representation of a singular project. A project contains multiple layers, which may or may not have images. This class is what controls all of the commands that allow a user to make a project, load a project in, save a project, save an image, add a layer, and add an image to a layer. There are two constructors, one that is used for creating a new project, and another for loading an existing project into the program.  

* CollagerController: The class CollagerController is the interactive command line for the program. This allows the user to input commands and receive output. This has been particularly helpful in the debugging process, and allows for a user interaction with the program.

* Main: The class Main runs the program in its entirety. Once the user enters a command, the Main sends the user's input to the Utilities class, where the information is split up and sent to the correct method in the controller.

* CollagerState: the class CollagerState serves as a place where the current project data is held. It allows multiple class and methods to affect the same project object, without a direct connection. 

* ImageUtil: the class ImageUtil contains the method readPPM, which reads a PPM file, and saves it so it can be added to a layer. 

* Utils: the class Utils is where miscellaneous helper methods are placed.  

* TextView: the class TextView controls what the program responds with when a user inputs a command. This is also used when the user types in an incorrect command, which will prompt the user to type in a valid command.

* SwingFeatures: This is a class that plays a part in opening the main window for the Graphical User Interface (GUI) of 

* SwingFeaturesFrame: This is a class that opens the main window for the GUI used. There is only one constructor, and it is empty. 

* RepresentationConverter: This is a class that contains utility methods to convert an RGB representation to HSL and back, and print those results.

* Frame: Thisclass is a representation of a frame, which is used for the GUI. 

# Command Line
* new-project canvas-height canvas-width: Creates a new project file. The new project comes with one layer, which is named "initial-layer" and can be accessed with that name.

* load-project path-to-project-file: loads a .txt file with the right format into the program. (The project files are saved as .txt).

* save-project: saves a project in a .txt format. After running this command, the program asks for a name, this name will be used to name the project file.

* add-layer layer-name: adds a layer with the name that is given by the user. This layer automatically goes to the top of the layer-list.

* add-image-to-layer layer-name image-name x-pos y-pos: adds a ppm image to the given layer. The top left corner of the ppm is placed at (x-pos, y-pos).

* set-filter layer-name filter-option: This command marks a layer as having the given filter. When save-image is used, the filter is applied to the layer. The filters that can be used are normal, red-component, green-component, blue-component, brighten-value, brighten-intensity, brighten-luma, darken-value, darken-intensity, and darken-luma.

* save-image file-name: Saves the project as a ppm file with the given file-name. This method also applies all filters to their layers.

* quit: ends the program and quits out.

# Command Script
* new-project 100 100
* add-layer blue-layer
* add-image-to-layer blue-layer res/sample.ppm 0 0
* set-filter blue-layer blue-component
* add-layer red-layer
* add-image-to-layer red-layer res/sample.ppm 20 20
* set-filter red-layer red-component
* add-layer brightened-layer
* add-image-to-layer brightened-layer res/sample.ppm 40 40
* set-filter brightened-layer brighten-luma
* add-layer darkened-layer
* add-image-to-layer darkened-layer res/sample.ppm 60 60
* set-filter darkened-layer darken-value
* save-project
* res/CommandsProject
* save-image res/CommandsProject.ppm
* quit

# Citations 
* The picture is a personal picture of my dog, and all code was written without internet sources! No citations needed.
