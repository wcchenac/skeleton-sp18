package byog.Core;

import byog.TileEngine.TETile;

import java.util.Scanner;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game game = new Game();
            TETile[][] worldState = game.playWithInputString(args[0]);
            System.out.println(TETile.toString(worldState));
        } else {
            Game game = new Game();
            game.playWithKeyboard();
        }
    }

    /*
    public static void main(String[] args) {
        String s = "";
        //String s = "n2442";
        //String s = "n2442asssdwsas:q";
        //String s = "lwwa:q";
        //String s = "ldwwwwwwddddd:q";
        //String s = "lwwddwdddsddwdwdwwwdwwaa:q";
        //System.out.print("Enter a seed: ");

        if (s.length() == 0) {
            Game game = new Game();
            game.playWithKeyboard();
            //System.out.println("Can only have one argument - the input string");
            //System.exit(0);
        } else {
            Game game = new Game();
            TETile[][] worldState = game.playWithInputString(s);
            System.out.println(TETile.toString(worldState));
        }
    }
    */
}
