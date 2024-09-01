import java.sql.SQLOutput;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Minefield {
    //flags are flagged by adding F to the end of the string state of a cell
    /**
     * Global Section
     */
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[33;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";

    /*
     * Class Variable Section
     *
     */
    private int rows, cols;
    private Cell[][] field; // [row][col]
    private int initFlags;

    /*Things to Note:
     * Please review ALL files given before attempting to write these functions.
     * Understand the Cell.java class to know what object our array contains and what methods you can utilize
     * Understand the StackGen.java class to know what type of stack you will be working with and methods you can utilize
     * Understand the QGen.java class to know what type of queue you will be working with and methods you can utilize
     */

    /**
     * Minefield
     * <p>
     * Build a 2-d Cell array representing your minefield.
     * Constructor
     *
     * @param rows    Number of rows.
     * @param columns Number of columns.
     * @param flags   Number of flags, should be equal to mines
     */
    public Minefield(int rows, int columns, int flags) {
        this.rows = rows;
        this.cols = columns;
        field = new Cell[rows][columns];
        initFlags = flags;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                field[r][c] = new Cell(false, "-");
            }
        }
        //populateFlags();
    }

    private void populateFlags() { //extra code
        int numFlags = 0;
        int r, c;
        Random rand = new Random();
        while (numFlags < initFlags) {
            // Generate random integers in range 0 to [rows/cols]-1
            r = rand.nextInt(rows);
            c = rand.nextInt(cols);
            //Flag random row after checking for duplicates
            if (field[r][c].getStatus().equals("-")) {
                field[r][c].setStatus("M");
                numFlags++;
            }
        }
        System.out.println("flags populated");
    }

    /**
     * evaluateField
     *
     * @function: Evaluate entire array.
     * When a mine is found check the surrounding adjacent tiles. If another mine is found during this check, increment adjacent cells status by 1.
     */
    public void evaluateField() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (field[r][c].getStatus().equals("M")) {
                    updateMineBorder(r, c);
                } else if (field[r][c].getStatus().equals("-")) {
                    field[r][c].setStatus("0");
                }
            }
        }
    }

    private void updateMineBorder(int r, int c) { //updates the values of all neighbors by 1
        int offset[][] = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};//8 possible offsets of (rowOffset,colOffset)
        int currRow, currCol;
        for (int i = 0; i < 8; i++) {
            currRow = r + offset[i][0];
            currCol = c + offset[i][1];
            if ((currRow < rows && currRow >= 0) && (currCol < cols && currCol >= 0)) {
                String currCell = field[currRow][currCol].getStatus();
                boolean flagged = currCell.contains("F");
                if (currCell.equals(null)) {
                    System.out.println("nullUpdateError");
                } else {
                    currCell = currCell.substring(0, 1);
                    if (currCell.equals("M")) {
                        //mine
                    } else if (currCell.equals("-") || currCell.equals("0")) {
                        field[currRow][currCol].setStatus("1" + ((flagged) ? "F" : ""));
                    } else if (isInt(currCell)) {
                        int num = Integer.parseInt(currCell);
                        if (num < 0 || num > 8) {
                            System.out.println("flag overload error");
                        } else {
                            num++;
                            field[currRow][currCol].setStatus(Integer.toString(num) + ((flagged) ? "F" : ""));
                        }
                    } else {
                        System.out.println("Unknown marker");
                    }
                }
            }
        }
    }

    private boolean isInt(String str) {
        if (str == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * createMines
     * <p>
     * Randomly generate coordinates for possible mine locations.
     * If the coordinate has not already been generated and is not equal to the starting cell set the cell to be a mine.
     * utilize rand.nextInt()
     *
     * @param x     Start x, avoid placing on this square.
     * @param y     Start y, avoid placing on this square.
     * @param mines Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        int numFlags = 0;
        int r, c;
        Random rand = new Random();
        while (numFlags < mines) {
            //Generate random integers in range 0 to [rows/cols]-1
            r = rand.nextInt(rows);
            c = rand.nextInt(cols);
            //Flag random row after checking for duplicates
            if (field[r][c].getStatus().equals("-") && c != x && r != y) {
                field[r][c].setStatus("M");
                numFlags++;
            }
        }
        System.out.println("flags populated");
    }

    /**
     * guess
     * <p>
     * Check if the guessed cell is inbounds (if not done in the Main class).
     * Either place a flag on the designated cell if the flag boolean is true or clear it.
     * If the cell has a 0 call the revealZeroes() method or if the cell has a mine end the game.
     * At the end reveal the cell to the user.
     *
     * @param x    The x value the user entered.
     * @param y    The y value the user entered.
     * @param flag A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) {
        if (inBounds(x, y)) {
            if (flag) { //flagging toggle
                if (field[y][x].getRevealed() == true) {
                    System.out.println("Warning: already revealed");
                }
                if (field[y][x].getStatus().contains("F")) {
                    field[y][x].setStatus(field[y][x].getStatus().substring(0, 1));
                    return true;
                } else {
                    field[y][x].setStatus(field[y][x].getStatus() + "F");
                    return false;
                }
            } else {//guessing
                String currData = field[y][x].getStatus();
                boolean flagged = currData.contains("F");
                currData = currData.substring(0, 1);
                if (field[y][x].getRevealed() == true) {
                    System.out.println("Warning: already revealed");
                    //return false;
                }
                if (currData.equals("M")) {
                    return true;
                } else if (currData.equals("0")) {
                    revealZeroes(x, y);
                } else if (flagged) {
                    System.out.println("overwriting flag");
                } else {
                    field[y][x].setRevealed(true);
                }
            }
        }
        return false;
    }

    private boolean inBounds(int x, int y) {
        if (x < cols && x >= 0 && y >= 0 && y < rows) {
            return true;
        }
        return false;
    }

    /**
     * gameOver
     * <p>
     * Ways a game of Minesweeper ends:
     * 1. player guesses a cell with a mine: game over -> player loses
     * 2. player has revealed the last cell without revealing any mines -> player wins
     *
     * @return boolean Return false if game is not over and squares have yet to be revealed/flagged, otheriwse return true.
     */
    public boolean gameOver() {
        boolean allRevealed = true;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell temp = field[r][c];
                if (temp.getStatus().substring(0, 1).equals("M")) {
                    if (temp.getStatus().length() > 1) {
                        //flagged
                    } else {
                        allRevealed = false;//not flagged
                    }
                } else {
                    if (!(temp.getRevealed())) {
                        allRevealed = false;//not revealed
                    }
                }
            }
        }
        return allRevealed;
    }

    /**
     * Reveal the cells that contain zeroes that surround the inputted cell.
     * Continue revealing 0-cells in every direction until no more 0-cells are found in any direction.
     * Utilize a STACK to accomplish this.
     * <p>
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
        Stack1Gen stack = new Stack1Gen<int[]>();
        stack.push(new int[]{x, y});
        while (!stack.isEmpty()) {
            int[] temp = (int[]) stack.pop();
            field[temp[1]][temp[0]].setRevealed(true);
            if (inBounds(temp[0] - 1, temp[1]) && !field[temp[1]][temp[0] - 1].getRevealed() && field[temp[1]][temp[0] - 1].getStatus().substring(0, 1).equals("0")) {
                stack.push(new int[]{temp[0] - 1, temp[1]});
            }
            if (inBounds(temp[0] + 1, temp[1]) && !field[temp[1]][temp[0] + 1].getRevealed() && field[temp[1]][temp[0] + 1].getStatus().substring(0, 1).equals("0")) {
                stack.push(new int[]{temp[0] + 1, temp[1]});
            }
            if (inBounds(temp[0], temp[1] + 1) && !field[temp[1] + 1][temp[0]].getRevealed() && field[temp[1] + 1][temp[0]].getStatus().substring(0, 1).equals("0")) {
                stack.push(new int[]{temp[0], temp[1] + 1});
            }
            if (inBounds(temp[0], temp[1] - 1) && !field[temp[1] - 1][temp[0]].getRevealed() && field[temp[1] - 1][temp[0]].getStatus().substring(0, 1).equals("0")) {
                stack.push(new int[]{temp[0], temp[1] - 1});
            }
        }
    }

    /**
     * revealStartingArea
     * <p>
     * On the starting move only reveal the neighboring cells of the inital cell and continue revealing the surrounding concealed cells until a mine is found.
     * Utilize a QUEUE to accomplish this.
     * <p>
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a queue be useful for this function?
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealStartingArea(int x, int y) {
        Q1Gen queue = new Q1Gen<int[]>();
        queue.add(new int[]{x, y});
        while (queue.length() > 0) {
            int[] temp = (int[]) queue.remove();
            if (inBounds(temp[0], temp[1]) && !field[temp[1]][temp[0]].getRevealed()) {
                field[temp[1]][temp[0]].setRevealed(true);
                if (field[temp[1]][temp[0]].getStatus().substring(0, 1).equals("0")) {//this may break can deleted
                    revealZeroes(temp[0], temp[1]);
                }//end delete
                if (field[temp[1]][temp[0]].getStatus().substring(0, 1).equals("M")) {
                    break;
                }
                queue.add(new int[]{temp[0] - 1, temp[1]});
                queue.add(new int[]{temp[0] + 1, temp[1]});
                queue.add(new int[]{temp[0], temp[1] + 1});
                queue.add(new int[]{temp[0], temp[1] - 1});
            }
        }
    }

    /**
     * For both printing methods utilize the ANSI colour codes provided!
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     * debug
     *
     * @function This method should print the entire minefield, regardless if the user has guessed a square.
     * *This method should print out when debug mode has been selected.
     */
    public void debug() {
        System.out.println("debug: ");
        //Top of field
        System.out.print("   ");
        for (int i = 0; i < cols; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        //rest of field
        for (int r = 0; r < rows; r++) {
            System.out.print(r + " ");
            if (r < 10) {
                System.out.print(" ");
            }
            for (int c = 0; c < cols; c++) {
                System.out.print(colorConv(field[r][c].getStatus().substring(0, 1)) + " ");
                if (c > 9) {
                    System.out.print(" ");
                }
                //System.out.print(field[r][c].getStatus());
            }
            System.out.println();
        }
    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */
    public String toString() {
        String output = "";
        //Top of field
        output += ("   ");
        for (int i = 0; i < cols; i++) {
            output += (i + " ");
        }
        output += "\n";
        //rest of field
        for (int r = 0; r < rows; r++) {
            output += (r + " ");
            if (r < 10) {
                output += (" ");
            }
            for (int c = 0; c < cols; c++) {
                String currString = field[r][c].getStatus();
                boolean rev = field[r][c].getRevealed();
                if (currString.length() > 1) {
                    output += colorConv("F") + " ";
                } else if (rev) {
                    output += colorConv(currString.substring(0, 1)) + " ";
                } else {
                    output += ("-" + " ");
                }
                if (c > 9) {
                    output += (" ");
                }
            }
            output += "\n";
        }
        return output;
    }

    private String colorConv(String input) { //color converter based on number/content
        int clr = -1;
        if (isInt(input)) {
            clr = Integer.parseInt(input);
        } else if (input.equals("M")) {
            clr = 11;
        } else if (input.equals("F")) {
            clr = 10;
        } else {
            clr = -1;
        }
        String color = ANSI_GREY_BACKGROUND;
        switch (clr) {
            case 0:
                color = ANSI_YELLOW;
                break;
            case 1:
                color = ANSI_CYAN;
                break;
            case 2:
                color = ANSI_GREEN;
                break;
            case 3:
                color = ANSI_PURPLE;
                break;
            case 4:
                color = ANSI_BLUE;
                break;
            case 5:
                color = ANSI_BLUE_BRIGHT;
                break;
            case 6:
                color = ANSI_BLUE_BRIGHT;
                break;
            case 7:
                color = ANSI_YELLOW_BRIGHT;
                break;
            case 8:
                color = ANSI_YELLOW_BRIGHT;
                break;
            case 9:
                color = ANSI_YELLOW_BRIGHT;
                break;
            case 10:
                color = ANSI_RED;
                break;
            case 11:
                color = ANSI_RED_BRIGHT;
                break;
            default:
                break;
        }
        return color + input + ANSI_GREY_BACKGROUND;
    }
}
