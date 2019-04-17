package hw4.puzzle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board implements WorldState {

	private class Position {
		private int x;
		private int y;

		// Row i, Column j
		private Position(int i, int j) {
			this.x = j;
			this.y = i;
		}
	}

	private int[][] tileWorld;
	private int size;
	private Position blank;
	private Map<Integer, Position> answer = new HashMap<>();
	//private int hamming;
	private int manhattan;

	/** Constructs a board from an N-by-N array of tiles where tiles[i][j] = tile at row i, column j. */
	public Board(int[][] tiles) {
		size = tiles.length;

		// Create new 2D-array for immutable
		tileWorld = new int[size][size];

		for (int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				// Copy take in 2D-array to new 2D-array
				tileWorld[i][j] = tiles[i][j];
				// Store the correct value at row i, column j
				Position p = new Position(i, j);
				answer.put(answerAtTile(i, j), p);
				// Cache the position of 0 in puzzle
				if (tiles[i][j] == 0) {
					blank = new Position(i, j);
				}
			}
		}

		// Estimate distance to Goal
	//	hamming = hamming();
		manhattan = manhattan();
	}

	/** Returns correct value at row i, column j; the position of 0 is at [size-1][size-1]*/
	private int answerAtTile(int i, int j) {
		if (i < 0 || i >= size || j < 0 || j >= size) {
			throw new IndexOutOfBoundsException();
		} else if (i == size - 1 && j == size - 1) {
			return 0;
		} else {
			return (j + 1) + (i * size);
		}
	}

	/** Returns value of tile at row i, column j (or 0 if blank)*/
	public int tileAt(int i, int j) {
		if (i < 0 || i >= size || j < 0 || j >= size) {
			throw new IndexOutOfBoundsException();
		} else {
			return tileWorld[i][j];
		}
	}

	/** Returns the board size N. */
	public int size() {
		return size;
	}

	/** Returns a new Board after moving blank position.
	 *  Take in the blank position and what position to move(row i/ column j). */
	private Board exchange(Position p, int i, int j) {
		// Full Copy the current Board for create new Board use
		int[][] temp = new int[size][size];
		for (int y = 0; y < size; y += 1) {
			for (int x = 0; x < size; x += 1) {
				temp[y][x] = tileWorld[y][x];
			}
		}

		// Exchange blank with its nearby
		if (p.y + i >= size || p.y + i < 0 || p.x + j >= size || p.x + j < 0) {
			return null;
		} else {
			temp[p.y][p.x] = temp[p.y + i][p.x + j];
			temp[p.y + i][p.x + j] = 0;
		}
		return new Board(temp);
	}

	/**Returns the neighbors of the current board. */
	@Override
	public Iterable<WorldState> neighbors() {
		Set<WorldState> neighbor = new HashSet<>();

		Board newUp = exchange(blank, 1, 0);
		Board newDown = exchange(blank, -1, 0);
		Board newRight = exchange(blank, 0, 1);
		Board newLeft = exchange(blank, 0, -1);

		if (newUp != null) {
			neighbor.add(newUp);
		}
		if (newDown != null) {
			neighbor.add(newDown);
		}
		if (newRight != null) {
			neighbor.add(newRight);
		}
		if (newLeft != null) {
			neighbor.add(newLeft);
		}
		return neighbor;
	}

	/** Hamming estimate : The number of tiles in the wrong position. */
	public int hamming() {
		int distance = 0;
		for (int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				if (tileWorld[i][j] == 0) {
					continue;
				} else if (tileWorld[i][j] != answerAtTile(i, j)) {
					// The current value at [i][j] and the value should at answer.
					Position p = answer.get(tileAt(i, j));
					int yDiff = Math.abs(i - p.y);
					int xDiff = Math.abs(j - p.x);
					distance += Math.max(xDiff, yDiff);
				}
			}
		}
		return distance;
	}

	/** Manhattan estimate : The sum of the vertical and horizontal distance from the start and goal position. */
	public int manhattan() {
		int distance = 0;
		for (int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				if (tileWorld[i][j] == 0) {
					continue;
				} else if (tileWorld[i][j] != answerAtTile(i, j)) {
					// The current value at [i][j] and the value should at answer.
					Position p = answer.get(tileAt(i, j));
					int yDiff = Math.abs(i - p.y);
					int xDiff = Math.abs(j - p.x);
					distance = distance + xDiff + yDiff;
				}
			}
		}
		return distance;
	}

	/** Estimated distance to goal. This method should simply return the results of manhattan()
	 *  when submitted to Gradescope.*/
	public int estimatedDistanceToGoal() {
		return manhattan;
	}

	/** Returns true if this board's tile values are the same position as y's. */
	public boolean equals(Object y) {
		if (this == y) {
			return true;
		}

		if (y == null || this.getClass() != y.getClass()) {
			return false;
		}

		Board temp = (Board) y;
		for (int i = 0; i < size; i += 1) {
			for (int j = 0; j < size; j += 1) {
				if (tileWorld[i][j] != temp.tileWorld[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/** Returns the string representation of the board. */
	public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
