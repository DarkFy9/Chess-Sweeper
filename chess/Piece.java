import java.util.Scanner;

public class Piece {
    // Create Instance Variables
    private char character;
    private int row, col;
    private boolean isBlack;

    /**
     * Constructor.
     *
     * @param character The character representing the piece.
     * @param row       The row on the board the piece occupies.
     * @param col       The column on the board the piece occupies.
     * @param isBlack   The color of the piece.
     */
    public Piece(char character, int row, int col, boolean isBlack) {
        this.character = character;
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    /**
     * Determines if moving this piece is legal.
     *
     * @param board  The current state of the board.
     * @param endRow The destination row of the move.
     * @param endCol The destination column of the move.
     * @return If the piece can legally move to the provided destination on the board.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        switch (this.character) {
            case '\u2659':
            case '\u265f':
                Pawn pawn = new Pawn(row, col, isBlack);
                return pawn.isMoveLegal(board, endRow, endCol);
            case '\u2656':
            case '\u265c':
                Rook rook = new Rook(row, col, isBlack);
                return rook.isMoveLegal(board, endRow, endCol);
            case '\u265e':
            case '\u2658':
                Knight knight = new Knight(row, col, isBlack);
                return knight.isMoveLegal(board, endRow, endCol);
            case '\u265d':
            case '\u2657':
                Bishop bishop = new Bishop(row, col, isBlack);
                return bishop.isMoveLegal(board, endRow, endCol);
            case '\u265b':
            case '\u2655':
                Queen queen = new Queen(row, col, isBlack);
                return queen.isMoveLegal(board, endRow, endCol);
            case '\u265a':
            case '\u2654':
                King king = new King(row, col, isBlack);
                return king.isMoveLegal(board, endRow, endCol);
            default:
                return false;
        }
    }

    /**
     * Sets the position of the piece.
     *
     * @param row The row to move the piece to.
     * @param col The column to move the piece to.
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Return the color of the piece.
     *
     * @return The color of the piece.
     */
    public boolean getIsBlack() {
        return isBlack;
    }

    /**
     * Handle promotion of a pawn.
     *
     * @param row     Current row of the pawn
     * @param isBlack Color of the pawn
     */
    public void promotePawn(int row, boolean isBlack) {
        boolean isntResolved = false;
        //offsets characters to the black pieces from unicode when black
        int offset = 0;
        if (isBlack) {
            offset = 6;
        }
        if (character == (char) ('\u2659' + offset)) {
            if (isBlack && row == 7) {
                isntResolved = true;
            } else if (!isBlack && row == 0) {
                isntResolved = true;
            }
        }
        //System.out.println(isntResolved);
        //asks for user decision of promotion piece
        while (isntResolved) {
            Scanner inputt = new Scanner(System.in);
            System.out.println("Enter piece for pawn to promote to: ");
            System.out.println("Enter ['queen', 'rook', 'bishop', or 'knight']: ");
            String inpt = "";
            //inpt = inputt.nextLine();
            inpt = inputt.nextLine();
            isntResolved = false;
            inpt = inpt.toUpperCase();
            switch (inpt) {
                case "Q":
                case "QUEEN":
                    character = (char) ('\u2655' + offset);
                    break;
                case "R":
                case "ROOK":
                    character = (char) ('\u2656' + offset);
                    break;
                case "K":
                case "KNIGHT":
                    character = (char) ('\u2658' + offset);
                    break;
                case "B":
                case "BISHOP":
                    character = (char) ('\u2657' + offset);
                    break;
                default:
                    System.out.println("Not a valid piece.");
                    isntResolved = true;
                    break;
            }
           // inputt.close();
        }
    }


    /**
     * Returns a string representation of the piece.
     *
     * @return A string representation of the piece.
     */
    public String toString() {
        String output = "";
        output = Character.toString(character);
        return output;
    }


}
