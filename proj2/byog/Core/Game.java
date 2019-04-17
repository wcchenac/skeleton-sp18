package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Map;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static long seed;
    private static World w;
    private static TETile[][] tileWorld;
    private static TERenderer ter;
    private static final String saveGame = ":q";
    private static final String newGame = "n";
    private static final String loadGame = "l";

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    	ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.enableDoubleBuffering();

        do {
	        mainPanel();
	        mainReadUserInput();
	        gameReadUserInput();
        } while (true);

        // TODO: After mainPanel/ Enter game, read player action from keyboard

    }

    private void mainPanel() {
        StdDraw.clear(Color.black);
        Font gameTitle = new Font("Arial",Font.BOLD, 75);
        Font option = new Font("Arial",Font.BOLD, 25);
        drawFrame(gameTitle, WIDTH / 2, 24, "CS61B");
        drawFrame(option, WIDTH / 2, 15, "New Game (N)");
        drawFrame(option, WIDTH / 2, 12, "Save Game (S)");
        drawFrame(option, WIDTH / 2, 9, "Load Game (L)");
        drawFrame(option, WIDTH / 2, 6, "Quit Game (Q)");
    }

    private void drawFrame(Font f, int x, int y, String s) {
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(f);
        StdDraw.text(x, y, s);
        StdDraw.show();
    }

    private void mainReadUserInput() {
        Font userInput = new Font("Monaco",Font.BOLD, 14);
        StdDraw.setFont(userInput);
        String input = "";
        int index = 0;
        while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            } else {
                char key = StdDraw.nextKeyTyped();
                input += String.valueOf(key);
                if (input.toLowerCase().charAt(index) == 'n') {
                    drawFrame(userInput, WIDTH / 2, 3, "Please enter a seed, then press 'Spare' ");
                }
                if (input.toLowerCase().contains("n") && input.contains(" ")) {
                    seed = seedStrip(input);
                    w = new World(WIDTH, HEIGHT, seed);
                    tileWorld = w.createNewWorld();
                    ter.renderFrame(tileWorld);
                    break;
                }
                if (input.toLowerCase().charAt(index) == 's') {
                    saveGame();
                    quitGame();
                }
                if (input.toLowerCase().charAt(index) == 'l') {
                    loadGame();
                    ter.renderFrame(tileWorld);
                    break;
                }

                if (input.toLowerCase().charAt(index) == 'q') {
                    quitGame();
                }
            }
            index += 1;
        }
    }

    private void gameReadUserInput() {
	    String input = "";
	    int index = 0;
    	while (true) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            } else {
                char key = StdDraw.nextKeyTyped();
	            input += String.valueOf(key);
                if (input.toLowerCase().charAt(index) == 'm') {
                	mainPanel();
                	break;
                } else {
                	playAction(w, input.toLowerCase().charAt(index));
                	ter.renderFrame(tileWorld);
                }
                index += 1;
            }
        }
    	w.stuff.put("Player", w.playerP);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // Confirm input actions are effective or not
        confirmInputActions(input);

        // Recognize first & last alphabet in lowercase
        String firstAlphabet = input.toLowerCase().substring(0,1);
        String lastAlphabet = input.toLowerCase().substring(input.length() - 2, input.length());

        for (int index = 0; index < input.length(); index += 1) {
            if (index == 0) {
                // Recognize first alphabet
                if (firstAlphabet.equals(newGame)) {
                    // if start with n, create new world
                    w = new World(WIDTH, HEIGHT, seed);
                    tileWorld = w.createNewWorld();
                    ter.renderFrame(tileWorld);
                } else if (firstAlphabet.equals(loadGame)) {
                    // if start with l, load previous world
                    loadGame();
                    ter.renderFrame(tileWorld);
                }
            } else if (input.charAt(index) > 48 && input.charAt(index) < 58) {
                // if the character at given index is integer, do nothing
                continue;
            } else if (input.charAt(index) == 58 && lastAlphabet.equals(saveGame))/*(index == input.length() - 2 && lastAlphabet.equals(saveGame))*/ {
                // when index is at penultimate, if last & penultimate character is equal ":q"
                saveGame();
                break;
            } else {
                playAction(w, input.toLowerCase().charAt(index));
                ter.renderFrame(tileWorld);
            }
        }
        w.stuff.put("Player", w.playerP);
    //    ter.renderFrame(tileWorld);
    //    System.out.println(w.keyGet);
        return tileWorld;
    }

    private void confirmInputActions(String input) {
        String firstAlphabet = input.toLowerCase().substring(0,1);

        // 1st character != l or n
        if (!firstAlphabet.equals(newGame) && !firstAlphabet.equals(loadGame)) {
            quitGame();
        }
        // 1st character ==n && 2nd character != integer
        else if (firstAlphabet.equals(newGame) && !(input.charAt(1) > 48 && input.charAt(1) < 58)) {
            quitGame();
        }
        // 1st character ==n && 2nd character == integer
        else if (firstAlphabet.equals(newGame) && (input.charAt(1) > 48 && input.charAt(1) < 58)) {
            // Strip alphabet out, reserve continuous integer
            seed = seedStrip(input);
        }
    }

    private long seedStrip(String input) {
        //  Seed determination: once there is non-integer, stop record
        //  e.g. if case "n123s3", seed is 123
        String s_number = "";
        for (int i = 1; i < input.length(); i+= 1) {
            char key = input.charAt(i);
            if (key > 48 && key < 58) {
                s_number += String.valueOf(key);
            }
            if (!(input.charAt(i + 1) > 48 && input.charAt(i + 1) < 58)) {
                // if there is an alphabet, stop for loop
                break;
            }
        }
        return (long) Integer.parseInt(s_number);
    }

    private void playAction(World w, char c) {
        w.playerAction(c);
    }

    private void saveGame() {
        File f = new File("./save.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject(seed);
            os.writeObject(w.playerP);
            os.writeObject(w.stuff);
            os.writeObject(w.keyGet);

            os.close();
            fs.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            quitGame();
        } catch (IOException e) {
            System.out.println(e);
            quitGame();
        }
    }

    private void loadGame() {
        File f = new File("./save.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);

                seed  = (Long) os.readObject();
                Position p = (Position) os.readObject();
                Map<String, Position> m = (Map<String, Position>) os.readObject();
                int k = (int) os.readObject();

                w = new World(WIDTH, HEIGHT, seed, p, m, k);
                tileWorld = w.loadWorld();

                os.close();
                fs.close();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                quitGame();
            } catch (IOException e) {
                System.out.println(e);
                quitGame();
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                quitGame();
            }
        }
    }

    private void quitGame() {
        System.exit(0);
    }
}
