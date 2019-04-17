package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;

	private static int calRow(int size, int row) {
		int calRow= row;
		if(row >= size) {
			calRow = 2 * size - calRow - 1;
		}
		return calRow;
	}

	private static int hexRowLength(int size, int calRow) {
		return (size + 2 * calRow);
	}

	private static int xOffset(int size, int calRow) {
		return (size - calRow - 1);
	}

	private static void addHexagon(TETile[][] world, int size) {
		int x, y;
		for (y = 0; y < size * 2; y ++) {
			for (x = 0; x < 3 * size - 2; x++) {
				int calRow = calRow(size, y);
				if (x >= xOffset(size, calRow) && x < xOffset(size, calRow) + hexRowLength(size, calRow)) {
					world[x][y] = Tileset.TREE;
				}
			}
		}
	}

	private static void addHexagon(TETile[][] world, int size, int xPos, int yPos, TETile t) {
		int x, y;
		for (y = 0; y < size * 2; y ++) {
			for (x = 0; x < 3 * size - 2; x++) {
				int calRow = calRow(size, y);
				int caLX = x + xPos;
				int calY = y + yPos;
				if (x >= xOffset(size, calRow) && x < xOffset(size, calRow) + hexRowLength(size, calRow)) {
					world[caLX][calY] = t;
				}
			}
		}
	}

	private static void startPosition1(TETile[][] world, int size) {
		int x = 0;
		int y = size * 2;
		TETile t = Tileset.TREE;
		for (int i = 0; i < 3; i++) {
			addHexagon(world, size, x, y, t);
			y += size * 2;
		}
	}

	private static void startPosition2(TETile[][] world, int size) {
		int x = (2 * size - 1);
		int y = size;
		TETile t = Tileset.WALL;
		for (int i = 0; i < 4; i++) {
			addHexagon(world, size, x, y, t);
			y += size * 2;
		}
	}

	private static void startPosition3(TETile[][] world, int size) {
		int x = (2 * size - 1) * 2;
		int y = 0;
		TETile t = Tileset.GRASS;
		for (int i = 0; i < 5; i++) {
			addHexagon(world, size, x, y, t);
			y += size * 2;
		}
	}

	private static void startPosition4(TETile[][] world, int size) {
		int x = (2 * size - 1) * 3;
		int y = size;
		TETile t = Tileset.WATER;
		for (int i = 0; i < 4; i++) {
			addHexagon(world, size, x, y, t);
			y += size * 2;
		}
	}

	private static void startPosition5(TETile[][] world, int size) {
		int x = (2 * size - 1) * 4;
		int y = size * 2;
		TETile t = Tileset.FLOOR;
		for (int i = 0; i < 3; i++) {
			addHexagon(world, size, x, y, t);
			y += size * 2;
		}
	}

	private static void addTesselationHexagon(TETile[][] world, int size) {
		startPosition1(world, size);
		startPosition2(world, size);
		startPosition3(world, size);
		startPosition4(world, size);
		startPosition5(world, size);
	}

	private static void addHorizontalHallWay(TETile[][] world, int xPos, int yPos, int w) {
		int i, j;
		for (j = yPos; j < yPos + 3; j++) {
			for (i = xPos; i < xPos + w; i++) {
				if (j == yPos || j == yPos + 3 - 1) {
					world[i][j] = Tileset.WALL;
				} else {
					world[i][j] = Tileset.FLOOR;
				}
			}
		}
	}

	private static void addHollowRectangle(TETile[][] world, int xPos, int yPos, int h, int w) {
		int i, j;
		for (j = yPos; j < yPos + h; j++) {
			for (i = xPos; i < xPos + w; i++) {
				if (i == xPos || i == xPos + w - 1 || j == yPos || j == yPos + h - 1) {
					world[i][j] = Tileset.WALL;
				} else {
					world[i][j] = Tileset.FLOOR;
				}
			}
		}
	}

	private static void addVerticalHallWay(TETile[][] world, int xPos, int yPos, int h) {
		int i, j;
		for (j = yPos; j < yPos + h; j++) {
			for (i = xPos; i < xPos + 3; i++) {
				if (i == xPos || i == xPos + 3 - 1) {
					world[i][j] = Tileset.WALL;
				} else {
					world[i][j] = Tileset.FLOOR;
				}
			}
		}
	}

	public static void main(String[] args) {
		TERenderer ter = new TERenderer();
		ter.initialize(WIDTH, HEIGHT);

		TETile[][] HexWorld = new TETile[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x += 1) {
			for (int y = 0; y < HEIGHT; y += 1) {
				HexWorld[x][y] = Tileset.NOTHING;
			}
		}

	//	addTesselationHexagon(HexWorld, 4);
		addHollowRectangle(HexWorld, 0, 0, 4,8);
		addHollowRectangle(HexWorld, 9, 1, 5, 4);
		addHorizontalHallWay(HexWorld, 7, 1, 3);
	//	addVerticalHallWay(HexWorld, 13, 2, 4);

		ter.renderFrame(HexWorld);
	}
}
