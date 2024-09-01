import java.lang.Math;
public class Knight {

    /**
     * Constructor.
     * @param row   The current row of the pawn.
     * @param col   The current column of the pawn.
     * @param isBlack   The color of the pawn.
     */
    public Knight(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    /**
     * Checks if a move to a destination square is legal.
     * @param board     The game board.
     * @param endRow    The row of the destination square.
     * @param endCol    The column of the destination square.
     * @return True if the move to the destination square is legal, false otherwise.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        //check for L shape movement pattern
        int rowDiff = Math.abs(row-endRow);
        int colDiff = Math.abs(col-endCol);
        if (board.verifySourceAndDestination(this.row, this.col, endRow, endCol,isBlack)&&((rowDiff==1&&colDiff==2)||(rowDiff==2&&colDiff==1))) {
            // Case 1: movement to empty square.
            if(board.getPiece(endRow, endCol) == null){
                return true;
            } else if (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) {
                // There is a piece of the opposite color to be captured.
                return true;
            } else {
                return false;
            }
        } else {
            // Case 2: Moving in a non-legal square. (illegal move)
            return false;
        }
    }

    // Instance variables
    private int row;
    private int col;
    private boolean isBlack;

}
