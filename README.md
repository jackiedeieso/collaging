# Assignment 4: Collager

For this assignment, we were asked to create a collager, which would allow a user to interact with an application similar to Gimp or Photoshop. The user is able to create a new project, add layers to this project, save the project, quit, and then reopen it at a later time if desired. In addition, when an image is added, the user is able to add a filter onto the image. The filters include: red-component, green-component, blue-component, brighten-value, brighten-intensity, brighten-luma, darken-value, darken-intensity, and darken-luma. 

# Design
In order to create a proper design for this assignment, we considered various components that may be added in the future, and that would be important to abstract to avoid issues with our program in the future. 

# Classes & Interfaces 

* Layer: The class Layer is used as a representation of a layer in a project. There is an initial layer when a new project is created - titled initial-layer - which represents the bottom-most layer in the project. This layer has a unique height and width input by the user. The first layer is then created, which is simply a plain white background. After the initial creation of the intial layer, the user is able to add layers. When a user creates their own layer, they are able to give it a unique name. This class is constructed such that it has two constructors, one for the initial layer of the game - which will be the same every time, except for width and height - and one for when the users wants to create a new layer. This allows for structure within the initial layer, and flexibilty in the unique layers.

* Pixel: The class Pixel is used as a representation for a Pixel in the project. There are two different types of Pixels, three-component pixels and four-component pixels. For three component pixels, there is a red value, a green value, and a blue value. For four-component Pixels, it is RGB values, with an additional value: the Alpha value. This value determines how transparent a given Pixel is. In this class, there are three different constructors. The first constructor is made for three-component pixels, and simply sets the alpha value to 255. The second constructor is used for four-component pixels, and allows for a unique alpha value to be interpreted. For the last constructor, we decided to create an empty constructor. We were experiencing a bug, and this got rid of the bug. 

* Project: The class Project is a representation of a singular project. A project contains multiple layers, which may or may not have images. This class is what controls all of the commands that allow a user to make a project, load a project in, save a project, save an image, add a layer, and add an image to a layer. There are two constructors, one that is used for creating a new project, and another for loading an existing project into the program.  

* CollagerController: The class CollagerController is the interactive command line for the program. This allows the user to input commands and receive output. This has been particularly helpful in the debugging process, and allows for a user interaction with the program.

* Main: The class Main runs the program in its entirety. Once the user enters a command, the Main sends the user's input to the Utilities class, where the information is split up and sent to the correct method in the controller.

* CollagerState: the class CollagerState serves as a place where the current project data is held. It allows multiple class and methods to affect the same project object, without a direct connection. 

* ImageUtil: the class ImageUtil contains the method readPPM, which reads a PPM file, and saves it so it can be added to a layer. 

* Utils: the class Utils is where miscellaneous helper methods are placed.  

* TextView: the class TextView controls what the program responds with when a user inputs a command. This is also used when the user types in an incorrect command, which will prompt the user to type in a valid command.
