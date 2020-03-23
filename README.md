# RISC Revolution1 Group9
![pipeline](https://gitlab.oit.duke.edu/yy258/ece651-spr20-g9/badges/master/pipeline.svg)
![coverage](https://gitlab.oit.duke.edu/yy258/ece651-spr20-g9/badges/master/coverage.svg?job=test)

## Steps
1. Go to the root of multiproject, then `gradle run-server`. Wait until it prints "Now connect players".
2. `gradle run-player`. The first player will be asked to input the number of all players. Then just follow the printed instructions.

## Code Review
Most of our code reviews can be found in ["comments" tab in activity](https://gitlab.oit.duke.edu/yy258/ece651-spr20-g9/activity) / [merge requests](https://gitlab.oit.duke.edu/yy258/ece651-spr20-g9/-/merge_requests?scope=all&utf8=%E2%9C%93&state=merged). 

## Initial UML Review
* It was a general design of the whole project. As for some implementation details, we cannot consider throughly in the initial UML. 
* We started by thinking about what classes we need to realize the functionality. Then, we improved it by adding design principles and interfaces. However, we cannot determine what parameters to use for each function, thereby leaving some design unfeasible when implemented later.
* It has some over-sized classes, which will contribute to embarrassingly paralleled problem when we assign the tasks to team members.
![](./initial_uml.png)

## Final UML
![](./final_uml.png)