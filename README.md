Hahn Suh Choi
This Java Interpreter contains
	- Core.java 
	- Main.java
	- Scanner.java
	- Variables.java
	- this README
	- class for each non-terminal grammar

Each non-terminal has its own class, and creates a new class whenever the grammar calls for it.
The semantic checks are made during the parse, calling static functions in the Parser class whenever necessary.
Variables.java contains the map of the global variables, the stack of all the local variables, 
and the default values of each map, stored in a map of maps and strings of the default keys.
All of the invalid inputs are checked during the .execute() function, whenever relevant.

The fictional language being interpreted is located in "Interpreter Language.png"
