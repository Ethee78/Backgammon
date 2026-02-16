# Backgammon
A JavaFX application featuring an AI opponent. This will use the expectiminimax algorithm to deal with the probability involved in a game of backgammon. This is still a work in progress and currently the AI will find all possible moves, and will pick moves prioritizing the pieces that are furthest away from their home. I plan to come back to this project shortly to implement the more complex AI agent.

### Motivation
I wanted to make this project after finishing a university module in Introduction to Artificial Intelligence, which included building an Othello game-playing agent. Probabilistic games were briefly discussed but were outside the course scope, so I created this project to explore decision making in stochastic games.

### Running the project
Requires JavaFX downloaded to run. The repo includes a run.bat file (made for windows), to use this you need to change the below line to the path to your JavaFX folder:
```
    set JAVAFX_PATH=D:\javafx-sdk-21.0.10\lib
```
Once this is set to the correct path, the project can be compiled and run by just calling this run.bat file. 

The project can also be run by compiling and then running the Backgammon.java file using JavaFX.

Change the lines assigning p1 and p2 near the end of the Backgammon class' initialiseGame method in order to switch whether the associated player is controlled by an AI or human player.

### Assets
Dice graphics - https://poltergasm.itch.io/i-love-dice

### References
- Russell, S. & Norvig, P. *Artificial Intelligence: A Modern Approach*, Chapter 6: reference for expectiminimax and stochastic game playing. Used for planning; not yet implemented.
- Kans, A. & Charatan, Q. *Java in Two Semesters* – guidance with JavaFX and UI implementation.