import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) throws FileNotFoundException {
        boolean testmodeEnabled = false; //[true] turns on testing mode which runs from file
        System.out.println("Starting...");
        boolean playerIsBlack = false; //player starts as white
        String moveHistory = "";
        Board chess = new Board();
        //System.out.println(chess);
        Scanner input;
        if (testmodeEnabled) { //for testing with file
            File f = new File("test.txt");
            input = new Scanner(f);
        } else { //normal user input
            input = new Scanner(System.in);
        }
        String in = "";
        Fen.load("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", chess); //load starting board position

        boolean forfeited = false;
        while (!chess.isGameOver() && !forfeited) {
            //print board and asks user for input
            System.out.println("Board:");
            System.out.println(chess);
            System.out.println((playerIsBlack ? "Black" : "White") + " to move.");
            System.out.println("What is your move? (format: [startRow] [startCol] [endRow] [endCol])");
            System.out.println("Move: ");
            //if(input.hasNext()) {
                in = input.nextLine();
            //}
            if (testmodeEnabled) {
                System.out.println(in);
            }
            moveHistory += in + "\n";//storing the moves in history
            if (in.equals("ff")) {
                // checks if current player forfeited
                System.out.println("Game over, " + (playerIsBlack ? "Black" : "White") + " forfeited.");
                forfeited = true;
            } else if ((in.equals("print history"))) {
                //prints the previous commands and moves
                System.out.println(moveHistory);
            } else if (in.equals("skip")) {
                //skips current move
                System.out.println((playerIsBlack ? "Black" : "White") + " skipped their turn.");
                playerIsBlack = !playerIsBlack;
            } else if (in.equals("amogus")) {
                //suspicious command
                Fen.load("PrrrrrPP/" + "Prrrrrrr/" + "nnnnrrrr/" + "nnnnrrrr/" + "Prrrrrrr/" + "PrrrrrPP/" + "PrrPrrPP/" + "PrrPrrPP", chess);
                System.out.println(chess);
                playerIsBlack = !playerIsBlack;
            } else {
                //main code for reading user move
                String[] inputArray = in.split(" ");
                int[] currMove = {-1, -1, -1, -1};
                //chooses if the format is [1 2 3 4] or [12 34], reads and executes move
                if (inputArray.length == 4) {
                    int[] move = {Integer.parseInt(inputArray[0]), Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2]), Integer.parseInt(inputArray[3])};//stores current move [startRow] [startCol] [endRow] [endCol]
                    currMove = move;
                } else if (inputArray.length == 2) {
                    int[] move = {Integer.parseInt(inputArray[0]) / 10, Integer.parseInt(inputArray[0]) % 10, Integer.parseInt(inputArray[1]) / 10, Integer.parseInt(inputArray[1]) % 10};//stores current move [startRow] [startCol] [endRow] [endCol]
                    currMove = move;
                }
                if (currMove[0] != -1) {
                    if (chess.verifySourceAndDestination(currMove[0], currMove[1], currMove[2], currMove[3], playerIsBlack)) {
                        Boolean moved = chess.movePiece(currMove[0], currMove[1], currMove[2], currMove[3]);
                        if (!testmodeEnabled && moved) {
                            playerIsBlack = !playerIsBlack;//swap colors
                        }
                    } else {
                        System.out.println("invalid move...");
                    }
                } else {
                    System.out.println("invalid command");
                }
            }
            //System.out.println(inputArray[0]+inputArray[1]+inputArray[2]+inputArray[3]);
        }
        input.close();
        System.out.println("Board:");
        System.out.println(chess);
        System.out.println("Game over, " + (!playerIsBlack ? "Black" : "White") + " wins!");
    }
}