Chess
Hewitt Li
--------------------------------
Instructions and assumptions:
You may compile and run the program from the [Game] class which has a main function.
After starting the program, the program will display a chess board in the console, in which they will ask for the white player to enter a move.
The move may be entered in these valid formats:
- [startrow startcolumn endrow endcolumn] example: 6 5 4 5
- [startrow_startcolumn endrow_endcolumn] example: 65 45
If the move is invalid, "move is invalid" etc. will be printed. The move must be a valid move for the current player color.
Additional commands/moves include:
- ff (forfiet)
- amogus (shhhhh-)
- print history (prints all past moves in a list)
- skip (skips current player color's turn)
If a command is invalid, "command is invalid" etc. will be printed.
A new chess board will be printed after every move, of the current state of the board.
Pieces move and operate within the normal rules of chess
If a pawn is promoted, then the player will be prompted to choose a new piece
The game is won when one player captures the other player's king
Limitations:
- Castling as in traditional chess is not enabled
- En passant is also not enabled
- There is no check for when the king is in check, nor is there checkmate.
--------------------------------
Potential bugs (important):
As stated in project documentation pieces be displayed as inverted colors depending on console theme.
A potentially major bug, is the display of blank spaces being inconsistent across devices.
If the spacing seems off or causes issues for testing, you may choose to swap back to the '\u2001' character
This bug should not affect the actual gameplay and just the display in console;
--------------------------------
Addtional Features:
There is an additional feature included!
I have included an Easter egg of some sorts, that I have included, which is a special suspicious command/move;
You can run this command by typing "amogus" into the input. This code is entirely optional and removable if needed and should not affect any other functions of the class.
:)
Additional commands have been outlined in the first section, these commands are optional and should not affect the functionality.
There is also a boolean [testmodeEnabled] which can be set to true to enable a mode that takes in a .txt file with moves in list format
The default file is "test.txt". This mode when enabled will run commands sequentially only from the file. I used this mode to quickly test similar moves.
--------------------------------
