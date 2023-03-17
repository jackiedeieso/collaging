# Assignment 4: Collager

For this assignment, we were asked to create a collager, which would allow a user to interact with an application similar to Gimp or Photoshop. The user is able to create a new project, add layers to this project, save the project, quit, and then reopen it at a later time if desired. In addition, when an image is added, the user is able to add a filter onto the image. The filters include: red-component, green-component, blue-component, brighten-value, brighten-intensity, brighten-luma, darken-value, darken-intensity, and darken-luma. 

# Design
In order to create a proper design for this assignment, we considered various components that may be added in the future, and that would be important to abstract to avoid issues with our program in the future. 

# Classes & Interfaces 

* Layer: The class Layer is used as a representation of a layer in a project. There is an initial layer when a new project is created - titled initial-layer - which represents the bottom-most layer in the project. This layer has a unique height and width input by the user. The first layer is then created, which is simply a plain white background. After the initial creation of the intial layer, the user is able to add layers. When a user creates their own layer, they are able to give it a unique name. This class is constructed such that it has two constructors, one for the initial layer of the game - which will be the same every time, except for width and height - and one for when the users wants to create a new layer. This allows for structure within the initial layer, and flexibilty in the unique layers.

* Pixel: 

* Project

* CollagerController

* Main

* CollagerState

* ImageUtil

* ImageUtils

* TextView 
