public class Queen {

    /**
     * Constructor.
     * @param row   The current row of the pawn.
     * @param col   The current column of the pawn.
     * @param isBlack   The color of the pawn.
     */
    public Queen(int row, int col, boolean isBlack) {
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
        if (board.verifyHorizontal(this.row, this.col, endRow, endCol) || board.verifyVertical(this.row, this.col, endRow, endCol) || board.verifyDiagonal(this.row, this.col, endRow, endCol)) {
            // Case 1: Forward movement to empty square.
            if(board.getPiece(endRow, endCol) == null){
                return true;
            } else if (board.getPiece(endRow, endCol).getIsBlack() != this.isBlack) {
                // There is a piece of the opposite color to be captured.
                return true;
            } else {
                System.out.println("test1");
                return false;
            }
        } else {
            // Case 2: Moving in a non-linear/non-diagonal square. (illegal move)
            System.out.println("test2");
            return false;
        }
    }

    // Instance variables
    private int row;
    private int col;
    private boolean isBlack;

}
