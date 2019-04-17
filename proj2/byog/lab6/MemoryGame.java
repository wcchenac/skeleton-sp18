package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import java.util.Scanner;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        Scanner a = new Scanner(System.in);
        System.out.print("Please enter a seed : ");
        int seed = a.nextInt();

        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder s = new StringBuilder(n);
        while (s.length() < n) {
            s.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return s.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        int x = width / 2;
        int y = height / 2;

        Font bigFont = new Font("Arial",Font.BOLD, 30);
        Font smallFont = new Font("Arial",Font.BOLD, 30);

        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        //TODO: If game is not over, display relevant game information at the top of the screen

        // Draw GUI
        if (!(gameOver)) {
            StdDraw.setFont(smallFont);
            StdDraw.textLeft(1 , height - 1, "Round" + round);
            StdDraw.text(x, height - 1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }

        // Draw solution
        StdDraw.setFont(bigFont);
        StdDraw.text(x, y, s);
        StdDraw.show();

    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i += 1){
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(750);
            drawFrame(" ");
            StdDraw.pause(750);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String input = "";
        drawFrame(input);
        while (input.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            } else {
              char key = StdDraw.nextKeyTyped();
              input += String.valueOf(key);
              drawFrame(input);
            }
        }
        StdDraw.pause(500);
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        gameOver = false;
        playerTurn = false;
        round = 1;

        //TODO: Establish Game loop
        while (!gameOver) {
            playerTurn = false;

            drawFrame("Round " + round + "! Good luck!");
            StdDraw.pause(1500);

            String answer = generateRandomString(round);
            flashSequence(answer);

            playerTurn = true;
            String userInput = solicitNCharsInput(round);

            if (!userInput.equals(answer)) {
                gameOver = true;
                drawFrame("Game Over! Final level: " + round);
            } else {
                drawFrame("Correct, well done!");
                StdDraw.pause(1500);
                round += 1;
            }
        }

    }

}
