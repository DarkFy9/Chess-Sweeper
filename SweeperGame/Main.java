//Import Section
import java.util.Random;
import java.util.Scanner;

/*
 * Provided in this class is the neccessary code to get started with your game's implementation
 * You will find a while loop that should take your minefield's gameOver() method as its conditional
 * Then you will prompt the user with input and manipulate the data as before in project 2
 * 
 * Things to Note:
 * 1. Think back to project 1 when we asked our user to give a shape. In this project we will be asking the user to provide a mode. Then create a minefield accordingly
 * 2. You must implement a way to check if we are playing in debug mode or not.
 * 3. When working inside your while loop think about what happens each turn. We get input, user our methods, check their return values. repeat.
 * 4. Once while loop is complete figure out how to determine if the user won or lost. Print appropriate statement.
 */

public class Main {
    static boolean debugOn = false;
    static boolean lost = false;
    static boolean longInput = false;
    static int rows, cols, flags;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String inp = "";
        System.out.println("Debug mode? [on/off]");
        inp = input.nextLine();
        if(inp.equals("on")){
            debugOn = true;
        }
        System.out.println("Difficulty? [easy/med/hard]");
        inp = input.nextLine();
        if(inp.equals("easy")){
            rows = 5;
            cols = 5;
            flags = 5;
        }else if(inp.equals("med")){
            rows = 9;
            cols = 9;
            flags = 12;
        } else if(inp.equals("hard")){
            rows = 20;
            cols = 20;
            flags = 40;
            longInput = true;
        }else{
            System.out.println("Input error, default: easy is selected");
            rows = 5;
            cols = 5;
            flags = 5;
        }
    // – Easy: Rows: 5 Columns: 5 Mines: 5 Flags: 5
    // – Medium: Rows: 9 Columns: 9 Mines: 12 Flags: 12
    // – Hard: Rows: 20 Columns: 20 Mines: 40 Flags: 40
        Minefield minefield = new Minefield(rows, cols, flags);
        int guessx = 3;
        int guessy = 3;
        System.out.println("Enter starting move: X,Y");
        inp = input.nextLine();
        try {
            if(longInput){
                guessx = Integer.parseInt(inp.substring(0, 2));
                guessy = Integer.parseInt(inp.substring(3, 5));
            }else {
                guessx = Integer.parseInt(inp.substring(0, 1));
                guessy = Integer.parseInt(inp.substring(2, 3));
            }
        }catch(NumberFormatException e){
            System.out.println("default [3,3] used");
        }
        if(inBounds(guessx,guessy)){
            //nothing
        }else{
            System.out.println("default [3,3] used");
            guessx = 3;
            guessy = 3;
        }
        minefield.createMines(guessx, guessy, flags);
        minefield.evaluateField();
        minefield.revealStartingArea(guessx,guessy);
        System.out.println("Minefield: ");
        System.out.println(minefield.toString());
        if(debugOn){
            System.out.println("Debug: ");
            minefield.debug();
        }
        while (!minefield.gameOver()&&!lost) {
            System.out.println("Flags left - " + flags);
            System.out.println("Enter guess: X,Y [empty]/Flag");
            boolean flagOn = false;
            inp = input.nextLine();
            if(longInput){
                if (inp.length() > 5) {
                    flagOn = true;
                    System.out.println("flagged");
                }
            }else {
                if (inp.length() > 3) {
                    flagOn = true;
                    System.out.println("flagged");
                }
            }
            try {
                if(longInput){
                    guessx = Integer.parseInt(inp.substring(0, 2));
                    guessy = Integer.parseInt(inp.substring(3, 5));
                }else {
                    guessx = Integer.parseInt(inp.substring(0, 1));
                    guessy = Integer.parseInt(inp.substring(2, 3));
                }
                boolean guess = minefield.guess(guessx,guessy,flagOn);
                if(flagOn){
                    if(guess){
                        flags +=1; //remove flag
                    }else{
                        flags-= 1; //place flag
                        if(flags<0) {
                            System.out.println("Warning: too many flags used.");
                        }
                    }
                }else{
                    if(guess){
                        lost = true; //hit mine
                    }
                }
                System.out.println("Minefield: ");
                System.out.println(minefield.toString());
                if(debugOn){
                    System.out.println("Debug: ");
                    minefield.debug();
                }
            }catch(NumberFormatException e){
                System.out.println("input error, try again");
                if(longInput){
                    System.out.println("Your game is on hard mode, please enter your numbers as two digits");
                    System.out.println("refer to README for more details");
                }
            }catch(StringIndexOutOfBoundsException e){
                System.out.println("input error, try again");
                if(longInput){
                    System.out.println("Your game is on hard mode, please enter your numbers as two digits");
                    System.out.println("refer to README for more details");
                }
            }
        }
        if(lost){
            System.out.println("Mine hit, Game Over. oof");
        }else{
            System.out.println("You win, all mines found!");
        }
        input.close();
    }
    private static boolean inBounds(int x, int y){
        if(x<cols&&x>=0&&y>=0&&y<rows){
            return true;
        }
        return false;
    }
}
