# Collager
For this assignment, we were asked to create a collager, which would allow a user to interact with an application similar to Gimp or Photoshop. The user is able to create a new project, add layers to this project, save the project, quit, and then reopen it at a later time if desired. In addition, when an image is added, the user is able to add a filter onto the image. There are many filters that a user can add, and these filters are applied using the RGB/RGBA system. An RGB value is a way to store a given color, using red, green, and blue components. The filters include: red-component, green-component, blue-component, brighten-value, brighten-intensity, brighten-luma, darken-value, darken-intensity, and darken-luma. All of these filters are meant to change a given layer, and that layer only. In addition to this, there are three filters that use the layer and the composite layer beneath it. These include blend-difference, blend-multiply, and blend-screen. Two of these filters, blend-multiply and blend-screen, are calculated using HSL value, which is another way to store a color. HSL represents hue, saturation, and lightness. Most features are available through all formats, such as GUI and text. However, only the GUI view has the ability to preview the image before it is saved. The features that are not available include not being able to open a file, the user has to type in the directory in order to access a file or a project. In addition, the user cannot scroll on the preview box if the project is larger than 750 by 750. 


# Design
In order to create a proper design for this assignment, we considered various components that may be added in the future, and that would be important to abstract to avoid issues with our program in the future. This program has been designed so that it can be added to at every step. Starting with the main, the user inputs their desired command, which is then sent to the controller class so the right method can be called on the current project. Due to this, methods and different commands can be added. The CollagerModel class communicates with the currentProject field. This field exists in the state class. The CollagerState class is designed as a holder for important data throughout the program. The CollagerState class contains sensitive information that is all necessary. This design is useful because it allows the program to make edits to the project with ease, but can be still as protected as necessary. The layer class holds the filterType and applies it when the image is saved. This is necessary for the project to load properly and for the filter to be changed. If the filter was applied immediately, it would be much harder to revert or change the filter because the RGB values had already been changed. By only applying the filter when the final image is being saved, it is much easier to load projects and change the filter settings on each layer.

# Classes & Interfaces 
* Layer: The class Layer is used as a representation of a layer in a project. There is an initial layer when a new project is created - titled initial-layer - which represents the bottom-most layer in the project. This layer has a unique height and width input by the user. The first layer is then created, which is simply a plain white background. After the initial creation of the intial layer, the user is able to add layers. When a user creates their own layer, they are able to give it a unique name. This class is constructed such that it has two constructors, one for the initial layer of the game - which will be the same every time, except for width and height - and one for when the users wants to create a new layer. This allows for structure within the initial layer, and flexibilty in the unique layers.

* PixelRGB: The class Pixel is used as a representation for an RGB or RGBA Pixel in the project. There are two different types of Pixels, three-component pixels and four-component pixels. For three component pixels, there is a red value, a green value, and a blue value. For four-component Pixels, it is RGB values, with an additional value: the Alpha value. This value determines how transparent a given Pixel is. In this class, there are three different constructors. The first constructor is made for three-component pixels, and simply sets the alpha value to 255. The second constructor is used for four-component pixels, and allows for a unique alpha value to be interpreted. For the last constructor, we decided to create an empty constructor. We were experiencing a bug, and this got rid of the bug. 

* PixelHSL: The class PixelHSL is used as a representation for a Pixel with HSL values. HSL stands for hue, saturation, and lightness, and is another way of storing information in a pixel. The hue value is betweeen 0 and 360, while the saturation and brightnesss values are from 0 to 1. The first contructor in this class is used for means of calling the state, controller, and Utils classes. The second constructor is used to convert a RGB Pixel into an HSL Pixel. This is meant for specific filters that are applied using the HSL format. 

* Project: The class Project is a representation of a singular project. A project contains multiple layers, which may or may not have images. This class is what controls all of the commands that allow a user to make a project, load a project in, save a project, save an image, add a layer, and add an image to a layer. There are two constructors, one that is used for creating a new project, and another for loading an existing project into the program.  

* CollagerController: The class CollagerController is the interactive command line for the program. This allows the user to input commands and receive output. This has been particularly helpful in the debugging process, and allows for a user interaction with the program.

* Main: The class Main runs the program in its entirety. Once the user enters a command, the Main sends the user's input to the Utilities class, where the information is split up and sent to the correct method in the controller.

* CollagerState: The class CollagerState serves as a place where the current project data is held. It allows multiple class and methods to affect the same project object, without a direct connection. 

* ImageUtil: The class ImageUtil contains the method readPPM, which reads a PPM file, and saves it so it can be added to a layer. 

* Utils: The class Utils is where miscellaneous helper methods are placed.  

* TextView: The class TextView controls what the program responds with when a user inputs a command. This is also used when the user types in an incorrect command, which will prompt the user to type in a valid command.

* CollagerGUIBuffer: The class CollagerGUIBuffer is a representation of the Appendable used in order to run the Collager GUI. 

* CollagerFrame: The class CollagerFrame is a representation of the window, or frame, that the GUI uses. The constructor doesn't take in anything, but is used to design and organize the panel. Some of these design choices include creating buttons, scrollboxes, etc. 

* PreviewPanel: This class is a representation of the panel used for project. This class is mainly used for importing images onto given layers.

# Where is the USEME?
You can find the USEME in the main project file. You will see a file titled USEME.

# Citations 
* The picture is a personal picture of my dog, and all code was written without internet sources! No citations needed.
