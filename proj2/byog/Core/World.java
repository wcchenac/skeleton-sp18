package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class World implements Serializable {
	//private static final long serialVersionUID = 123L;
	private static int WIDTH;
	private static int HEIGHT;
	public static Random ran;
	private static TETile[][] tileWorld;
	public static Position playerP;
	public static Map<String , Position> stuff;
	// Random walk direction: +X, -X, +Y, -Y
	private static int[] xMove = {1, -1, 0, 0};
	private static int[] yMove = {0, 0, 1, -1};
	public static int keyGet;
	public static boolean gameOver = false;


	public World(int w, int h, long seed) {
		this.WIDTH = w;
		this.HEIGHT = h;
		ran = new Random(seed);
		stuff= new HashMap<>();
	}

	public World(int w, int h, long seed, Position p, Map<String, Position> m, int k) {
		this.WIDTH = w;
		this.HEIGHT = h;
		ran = new Random(seed);
		this.playerP = p;
		this.stuff = m;
		this.keyGet = k;
	}

	private static void initialize() {
		// Initial 2-D array content
		tileWorld = new TETile[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x += 1) {
			for (int y = 0; y < HEIGHT; y += 1) {
				tileWorld[x][y] = Tileset.NOTHING;
			}
		}
	}

	private static int floorAmount() {
		int floorAmount = ran.nextInt(201) + 500;       // (0 ~ 200) + 500;
		return floorAmount;
	}

	private static Position randomPosition() {
		int x = ran.nextInt(WIDTH / 2) + 1;     // (0 ~ 39) + 1
		int y = ran.nextInt(HEIGHT / 2) + 1;    // (0 ~ 14) + 1
		Position p = new Position(x, y);
		return p;
	}

	private static boolean isSafe(int x, int y) {
		return (x>= 1 && x < WIDTH - 1 && y >= 1 && y < HEIGHT - 1 && tileWorld[x][y] == Tileset.NOTHING);
	}

	private static boolean floorDevelop(int x, int y, int floorAmount, int[] xMove, int[] yMove) {
		if (floorAmount == 0) return true;
		int k = ran.nextInt(4);
		int nextX, nextY, counter = 0;
		for (int j = k;  counter != 4; j = Math.floorMod(j + 1, 4), counter += 1) {
			//int length = ran.nextInt(6) + 1;
			nextX = x + xMove[j];
			nextY = y + yMove[j];
			if (isSafe(nextX, nextY)) {
				tileWorld[nextX][nextY] = Tileset.FLOOR;
				if (floorDevelop(nextX, nextY, floorAmount - 1, xMove, yMove)) {
					return true;
				} else {
					tileWorld[nextX][nextY] = Tileset.NOTHING;
				}
			}
		}
		return false;
	}

	private static void fillWall() {
		// At certain floor position, fill in walls(total 8 direction) when there is nothing
		for (int x = 0; x < WIDTH; x += 1) {
			for (int y = 0; y < HEIGHT; y += 1) {
				if (tileWorld[x][y] == Tileset.FLOOR) {
					if (tileWorld[x + 1][y] == Tileset.NOTHING) tileWorld[x + 1][y] = Tileset.WALL;
					if (tileWorld[x - 1][y] == Tileset.NOTHING) tileWorld[x - 1][y] = Tileset.WALL;
					if (tileWorld[x][y + 1] == Tileset.NOTHING) tileWorld[x][y + 1] = Tileset.WALL;
					if (tileWorld[x][y - 1] == Tileset.NOTHING) tileWorld[x][y - 1] = Tileset.WALL;
					if (tileWorld[x + 1][y + 1] == Tileset.NOTHING) tileWorld[x + 1][y + 1] = Tileset.WALL;
					if (tileWorld[x + 1][y - 1] == Tileset.NOTHING) tileWorld[x + 1][y - 1] = Tileset.WALL;
					if (tileWorld[x - 1][y + 1] == Tileset.NOTHING) tileWorld[x - 1][y + 1] = Tileset.WALL;
					if (tileWorld[x - 1][y - 1] == Tileset.NOTHING) tileWorld[x - 1][y - 1] = Tileset.WALL;
				}
			}
		}
	}

	private static void fillDoor(Position p) {
		int x = p.x;
		int y = p.y;
		tileWorld[x][y] = Tileset.LOCKED_DOOR;
		stuff.put("Door", p);
	}

	private static void keyGeneration() {
		int create = 5;
		while (create != 0) {
			int x = ran.nextInt(WIDTH - 1);
			int y = ran.nextInt(HEIGHT - 1);
			if (tileWorld[x][y] == Tileset.FLOOR) {
				tileWorld[x][y] = Tileset.FLOWER;
				String s = "key" + create;
				Position p = new Position(x, y);
				stuff.put(s, p);
				create -= 1;
			}
		}
	}

	private static void playerCreation() {
		int create = 0;
		while (create == 0) {
			int x = ran.nextInt(WIDTH - 1);
			int y = ran.nextInt(HEIGHT - 1);
			if (tileWorld[x][y] == Tileset.FLOOR) {
				tileWorld[x][y] = Tileset.PLAYER;
				playerP = new Position(x, y);
				stuff.put("Player", playerP);
				create += 1;
			}
		}
	}

	public static void playerAction(char c) {
		switch (c) {
			case 'a' :
				if (tileWorld[playerP.x + xMove[1]][playerP.y + yMove[1]] == Tileset.FLOWER) {
					keyGet += 1;
					positionUpdate(1);
					removeKey();
				} else if (tileWorld[playerP.x + xMove[1]][playerP.y + yMove[1]] == Tileset.FLOOR) {
					positionUpdate(1);
				} else if (tileWorld[playerP.x + xMove[1]][playerP.y + yMove[1]] == Tileset.LOCKED_DOOR) {
					if (keyGet == 5) {
						positionUpdate(1);
						gameOver = true;
						System.out.println("Congratulation");
					} else {
						System.out.println("The door is locked!");
					}
				} else {
					System.out.println("There is a wall!");
				}
				break;
			case 'd' :
				if (tileWorld[playerP.x + xMove[0]][playerP.y + yMove[0]] == Tileset.FLOWER) {
					keyGet += 1;
					positionUpdate(0);
					removeKey();
				} else if (tileWorld[playerP.x + xMove[0]][playerP.y + yMove[0]] == Tileset.FLOOR) {
					positionUpdate(0);
				} else if (tileWorld[playerP.x + xMove[0]][playerP.y + yMove[0]] == Tileset.LOCKED_DOOR) {
					if (keyGet == 5) {
						positionUpdate(0);
						gameOver = true;
						System.out.println("Congratulation");
					} else {
						System.out.println("The door is locked!");
					}
				} else {
					System.out.println("There is a wall!");
				}
				break;
			case 'w' :
				if (tileWorld[playerP.x + xMove[2]][playerP.y + yMove[2]] == Tileset.FLOWER) {
					keyGet += 1;
					positionUpdate(2);
					removeKey();
				} else if (tileWorld[playerP.x + xMove[2]][playerP.y + yMove[2]] == Tileset.FLOOR) {
					positionUpdate(2);
				} else if (tileWorld[playerP.x + xMove[2]][playerP.y + yMove[2]] == Tileset.LOCKED_DOOR) {
					if (keyGet == 5) {
						positionUpdate(2);
						gameOver = true;
						System.out.println("Congratulation");
					} else {
						System.out.println("The door is locked!");
					}
				} else {
					System.out.println("There is a wall!");
				}
				break;
			case 's' :
				if (tileWorld[playerP.x + xMove[3]][playerP.y + yMove[3]] == Tileset.FLOWER) {
					keyGet += 1;
					positionUpdate(3);
					removeKey();
				} else if (tileWorld[playerP.x + xMove[3]][playerP.y + yMove[3]] == Tileset.FLOOR) {
					positionUpdate(3);
				} else if (tileWorld[playerP.x + xMove[3]][playerP.y + yMove[3]] == Tileset.LOCKED_DOOR) {
					if (keyGet == 5) {
						positionUpdate(3);
						gameOver = true;
						System.out.println("Congratulation");
					} else {
						System.out.println("The door is locked!");
					}
				} else {
					System.out.println("There is a wall!");
				}
				break;
			default :
				break;
		}
	}

	private static void positionUpdate(int direction) {
		tileWorld[playerP.x][playerP.y] = Tileset.FLOOR;
		playerP.x += xMove[direction];
		playerP.y += yMove[direction];
		tileWorld[playerP.x][playerP.y] = Tileset.PLAYER;
	}

	private static void removeKey() {
		int counter = 0;
		int i = 1;
		while (counter == 0) {
			String s = "key" + i;
			if (stuff.get(s) != null) {
				Position p = stuff.get(s);
				if (p.x == playerP.x && p.y == playerP.y) {
					stuff.put(s, null);
					counter = 1;
				}
			}
			i += 1;
		}
	}

	private static void rollbackKey() {
		int counter = 0;
		int i = 1;
		while (counter != 5) {
			String s = "key" + i;
			if (stuff.get(s) != null) {
				Position p = stuff.get(s);
				tileWorld[p.x][p.y] = Tileset.FLOWER;
			}
			counter += 1;
			i += 1;
		}
	}

	public static TETile[][] createNewWorld() {
		initialize();

		// Determine maze size
		int floorAmount = floorAmount();

		// Determine maze creation start position
		Position start = randomPosition();

		// Use 1st start position as door location
		fillDoor(start);

		// Fill in floor
		if (!floorDevelop(start.x, start.y, floorAmount, xMove, yMove)) {
			return null;
		} else {
			// Fill walls by searching floor area
			fillWall();
		}
		keyGeneration();
		keyGet = 0;
		playerCreation();
		return tileWorld;
	}

	public static TETile[][] loadWorld() {
		initialize();

		// Determine maze size
		int floorAmount = floorAmount();

		// Determine maze creation start position
		Position start = randomPosition();

		// Use 1st start position as door location
		fillDoor(start);

		// Fill in floor
		if (!floorDevelop(start.x, start.y, floorAmount, xMove, yMove)) {
			return null;
		} else {
			// Fill walls by searching floor area
			fillWall();
		}

		// Rollback key position
		rollbackKey();

		// Rollback player position
		Position Player = stuff.get("Player");
		tileWorld[Player.x][Player.y] = Tileset.PLAYER;
		return tileWorld;
	}
}