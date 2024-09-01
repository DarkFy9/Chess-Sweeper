Minesweeper
Hewitt Li
--------------------------------
Instructions and assumptions:
You may compile and run the program from the [Main] class which has a main function.
After starting the program, the program will display a minefield in the console, in which they will ask for the player to enter a move.
The move/guess may be entered in this format:
- [guessX guessY] example: 2,3 or 2 3
A flag can be placed or unplaced after the starting move by entering:
- [guessX guessY] [flag] example: 2,3 flag or 2 3 f
Note: if the game is set to hard, numbers must be inputed as a 2 digit number i.e. '1' becomes '01', and '11' is still '11'.
failure to do so will cause the input to be rejected.
If the move is invalid, "move is invalid" etc. will be printed. The move must be a valid move or else users move may be ignored
The game is won if all the tiles are revealed and all the mines are flagged.
The game will be lost if a mine is guessed without the intent of flagging it.
Note: The reveal starting area code will call revealzeros if a zero is hit, this is to make the game more playable.
If this will cause any issues, the code that can be removed is marked.
--------------------------------
Potential bugs:
Due to the number of colors provided, the numbers up until 6 all have different colors, but numbers 6 and over may have overlaping colors for visual clarity (this is a stylistic choice, that can be changed if necessary)
The spacing may be longer after x = 10 but everything should line up and be functional
No major bugs have been found yet, text output could be inconsistent across devices
--------------------------------
Addtional Features:
I have attached a ZIP with version of minesweeper that uses processing to make a GUI, this will probably not work without some setup in intelliJ (setting up libraries), but hopefully is cool. :)
The main file to run is [visual], and the folder [library] will have to be added as a library to intelliJ.
Do not feel obligated to try it, its just something I did for fun and may not work. :D
--------------------------------
