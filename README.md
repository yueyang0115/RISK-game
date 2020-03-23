# RISC Revolution1 Group9
![pipeline](https://gitlab.oit.duke.edu/yy258/ece651-spr20-g9/badges/master/pipeline.svg)
![coverage](https://gitlab.oit.duke.edu/yy258/ece651-spr20-g9/badges/master/coverage.svg?job=test)

## Steps
1. Go to the root of multiproject, then `gradle run-server`. Wait until it prints "Now connect players".
2. `gradle run-player`. The first player will be asked to input the number of all players. Then just follow the printed instructions.

## Code Review
Most of our code reviews can be found in ["comments" tab in activity](https://gitlab.oit.duke.edu/yy258/ece651-spr20-g9/activity) / [merge requests](https://gitlab.oit.duke.edu/yy258/ece651-spr20-g9/-/merge_requests?scope=all&utf8=%E2%9C%93&state=merged). 

## Sprint History
#### Sprint 1 (by Mar 13)
##### Done:
* Finished the initialization function, generating maps according to the number of all players (2-5) _defined by the server_ (changed in Sprint 2).
* Set up the socket to communicate between server and player.
* Server uses  `PlayerHandler` (extends `Thread`)  to handle and connect every player.
* Server sends `id`, `playerNum` and the `territoryMap` JSON Object (as String) to the player.
* Player can parse and print the received `territoryMap`.
* Finished the function to read actions on player-side, and the serialization and deserialization of the `Action` class.
* Finished the function to change the `territoryMap` based on the actions. Add one unit to every territory after every round. 

##### Todo:
* Finish the function to check the validity of `Action`.
* Improve the main functions of player and server, using the already implemented functions.

#### Sprint 2 (by Mar 21)
##### Done:
* According to feedback by TA: now the first player inputs the number of all players
* Finished player and server side, so that they can operate on one consistent communication process.
* Finished CI/CD set-up (“Test” failed before).
* Almost finished `checkAction` and `doAction` on the server-side. 

##### Todo:
* Continue working on the situation when we need to revoke the previous actions after finding an invalid one.

## Initial UML Review
* It was a general design of the whole project. As for some implementation details, we cannot consider throughly in the initial UML. 
* We started by thinking about what classes we need to realize the functionality. Then, we improved it by adding design principles and interfaces. However, we cannot determine what parameters to use for each function, thereby leaving some design unfeasible when implemented later.
* It has some over-sized classes, which will contribute to embarrassingly paralleled problem when we assign the tasks to team members.
![](./initial_uml.png)

## Final UML
![](./final_uml.png)