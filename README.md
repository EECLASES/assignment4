# assignment4
This is the repository for EE 422C assignment 4. Includes Critter.java files

Other than our own Critter classes, we used a separate static CritterWorld class to keep track of our Critter population and babies. 

In our code, we used protected methods to get and set private fields of our Critters. 

Our walk() and run() methods utilized a helper move() method that reused code that would have been shared between the two methods.

Our controller consists of a while loop that parses through commands and makes calls to Critter.java methods in order to simulate the Critter world. Error handling is done in the controller when parsing commands.

Our worldTimeStep() method consists of multiple helper methods that abstract the multiple tasks to complete during a single time step. Specifically, doEncounter() has another helper method to handle individual pairwise fights.
