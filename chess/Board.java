import java.lang.Math;

public class Board {

    // Instance variables
    private Piece[][] board;//[row][col] or [y][x]

    //
    // Construct an object of type Board using given arguments.
    public Board() {
        board = new Piece[8][8];
    }

    // Accessor Methods

    //
    // Return the Piece object stored at a given row and column
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    //
    // Update a single cell of the board to the new piece.
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // Game functionality methods

    //
    // Moves a Piece object from one cell in the board to another, provided that
    // this movement is legal. Returns a boolean to signify success or failure.
    // This method calls all necessary helper functions to determine if a move
    // is legal, and to execute the move if it is. Your Game class should not 
    // directly call any other method of this class.
    // Hint: this method should call isMoveLegal() on the starting piece. 
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        if (board[startRow][startCol].isMoveLegal(this, endRow, endCol)) {
            board[endRow][endCol] = board[startRow][startCol];
            board[startRow][startCol] = null;
            board[endRow][endCol].setPosition(endRow, endCol);
            board[endRow][endCol].promotePawn(endRow, board[endRow][endCol].getIsBlack());
            return true;
        } else {
            System.out.println("invalid move.");
        }
        return false;
    }

    //
    // Determines whether the game has been ended, i.e., if one player's King
    // has been captured.
    public boolean isGameOver() {
        //checks if there is both kings on the board, if not, game is over
        boolean blackKingAlive = false;
        boolean whiteKingAlive = false;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] != null) {
                    if (board[r][c].toString().charAt(0) == '\u2654') {
                        whiteKingAlive = true;
                    } else if (board[r][c].toString().charAt(0) == '\u265A') {
                        blackKingAlive = true;
                    }
                }
            }
        }
        if (blackKingAlive && whiteKingAlive) {
            return false;
        } else {
            return true;
        }
    }

    // Constructs a String that represents the Board object's 2D array.
    // Returns the fully constructed String.
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(" ");
        for (int i = 0; i < 8; i++) {
            out.append("\u2003");//"swapped from u2001"
            out.append(i);
        }
        out.append('\n');
        for (int i = 0; i < board.length; i++) {
            out.append(i);
            out.append("|");
            for (int j = 0; j < board[0].length; j++) {
                out.append(board[i][j] == null ? "\u2003" + "|" : board[i][j] + "|");//swapped form \u2001
            }
            out.append("\n");
        }
        return out.toString();
    }

    //
    // Sets every cell of the array to null. For debugging and grading purposes.
    public void clear() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                board[r][c] = null;
            }
        }
    }

    // Movement helper functions

    //
    // Ensure that the player's chosen move is even remotely legal.
    // Returns a boolean to signify whether:
    // - 'start' and 'end' fall within the array's bounds.
    // - 'start' contains a Piece object, i.e., not null.
    // - Player's color and color of 'start' Piece match.
    // - 'end' contains either no Piece or a Piece of the opposite color.
    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        if (startCol < 8 && startCol >= 0 && startRow < 8 && startRow >= 0) {
            if (endCol < 8 && endCol >= 0 && endRow < 8 && endRow >= 0) {
                if (board[startRow][startCol] != null) {
                    boolean isBlk = board[startRow][startCol].getIsBlack();
                    if (isBlack == isBlk) {
                        if (board[endRow][endCol] == null || board[endRow][endCol].getIsBlack() != isBlk) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //
    // Check whether the 'start' position and 'end' position are adjacent to each other
    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        if ((Math.abs(startRow - endRow) <= 1 && Math.abs(startCol - endCol) <= 1)) {//&&!(startCol==endCol&&startRow==endRow)
            return true;
        }
        return false;
    }

    //
    // Checks whether a given 'start' and 'end' position are a valid horizontal move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one row.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
        if (startRow == endRow) {
            for (int c = Math.min(startCol, endCol) + 1; c < Math.max(startCol, endCol); c++) {
                if (board[startRow][c] != null) {
                    return false;
                }
            }
        } else {
            return false;

        }
        return true;
    }

    //
    // Checks whether a given 'start' and 'end' position are a valid vertical move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one column.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
        if (startCol == endCol) {
            for (int r = Math.min(startRow, endRow) + 1; r < Math.max(startRow, endRow); r++) {
                if (board[r][startCol] != null) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    //
    // Checks whether a given 'start' and 'end' position are a valid diagonal move.
    // Returns a boolean to signify whether:
    // - The path from 'start' to 'end' is diagonal... change in row and col.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.
    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
        if (Math.abs(startCol - endCol) == Math.abs(startRow - endRow)) {
            int steps = Math.abs(startCol - endCol);
            int rowIncrement = 1;
            if (startRow > endRow) {
                rowIncrement = -1;
            }
            int colIncrement = 1;
            if (startCol > endCol) {
                colIncrement = -1;
            }
            for (int c = 1; c < steps; c++) {
                if (board[startRow + (rowIncrement * c)][startCol + (colIncrement * c)] != null) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
    //end of class
}
