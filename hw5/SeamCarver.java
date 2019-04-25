import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	private Picture p;

	public SeamCarver(Picture picture) {
		p = new Picture(picture);
	}

	// current picture
	public Picture picture() {
		return p;
	}

	// width of current picture
	public int width() {
		return p.width();
	}

	// height of current picture
	public int height() {
		return p.height();
	}

	// Return an array recorded [R, G, B]
	private int[] pixelNeighborColor(int col, int row, int xShift, int yShift) {
		int newRGB;
		if (col + xShift >= width()) {
			newRGB = (p.getRGB(0, row + yShift));
		} else if (col + xShift < 0) {
			newRGB = (p.getRGB(width() - 1, row + yShift));
		} else if (row + yShift >= height()) {
			newRGB = (p.getRGB(col + xShift, 0));
		} else if (row + yShift < 0) {
			newRGB = (p.getRGB(col + xShift, height() - 1));
		} else {
			newRGB = (p.getRGB(col + xShift, row + yShift));
		}
		return new int[]{((newRGB >> 16) & 0xFF), ((newRGB >>  8) & 0xFF), ((newRGB >>  0) & 0xFF)};
	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (x < 0 || x >= width()) {
			throw new IndexOutOfBoundsException("The input col is not between 0 and " + (width() - 1) + ".");
		}
		if (y < 0 || y >= height()) {
			throw new IndexOutOfBoundsException("The input row is not between 0 and " + (height() - 1) + ".");
		}
		int[] pixelUp = pixelNeighborColor(x, y, 0, -1);
		int[] pixelDown = pixelNeighborColor(x, y, 0, 1);
		int[] pixelLeft = pixelNeighborColor(x, y, -1, 0);
		int[] pixelRight = pixelNeighborColor(x, y, 1, 0);

		double gradientX = (pixelLeft[0] - pixelRight[0]) * (pixelLeft[0] - pixelRight[0]) +
				(pixelLeft[1] - pixelRight[1]) * (pixelLeft[1] - pixelRight[1]) +
				(pixelLeft[2] - pixelRight[2]) * (pixelLeft[2] - pixelRight[2]);
		double gradientY = (pixelDown[0] - pixelUp[0]) * (pixelDown[0] - pixelUp[0]) +
				(pixelDown[1] - pixelUp[1]) * (pixelDown[1] - pixelUp[1]) +
				(pixelDown[2] - pixelUp[2]) * (pixelDown[2] - pixelUp[2]);
		return (gradientX + gradientY);
	}

	// Calculate minimum cost to (col, row) in -Y direction
	private double calculateMinCost(double[][] mcp, int col, int row) {
		// assume top is minimum
		double minimum = mcp[col][row - 1];

		if (col - 1 >= 0 && mcp[col - 1][row - 1] < minimum) {
			minimum = mcp[col - 1][row - 1];
		}
		if (col + 1 < width() && mcp[col + 1][row - 1] < minimum) {
			minimum = mcp[col + 1][row - 1];
		}
		return minimum;
	}

	// Find minimum cost path as Col index near (col, row)
	private int findMinCostPath(double[][] mcp, int col, int row) {
		// assume (col, row) is min
		double min = mcp[col][row];
		int colIndex = col;
		if (col - 1 >= 0 && mcp[col - 1][row] < min) {
			min = mcp[col - 1][row];
			colIndex = col - 1;
		}
		if (col + 1 < width() && mcp[col + 1][row] < min) {
			min = mcp[col + 1][row];
			colIndex = col + 1;
		}
		return colIndex;
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		double[][] MCP = new double[width()][height()];
		int[] result = new int[height()];

		// calculate the cost energy to get (col, row0 pixel
		for (int j = 0; j < height(); j += 1) {
			for (int i = 0; i < width(); i += 1) {
				if (j == 0) {
					MCP[i][0] = energy(i, 0);
				} else {
					MCP[i][j] = energy(i, j) + calculateMinCost(MCP, i, j);
				}
			}
		}

		// Iterate every entry point from row height - 1, then find the minimum cost path above the entry point
		int xIndex = 0;
		for (int y = height() - 1; y >= 0; y -= 1) {
			double min = Double.MAX_VALUE;
			for (int x = 0; x < width(); x += 1) {
				if (y == height() - 1) {
					if (MCP[x][y] < min) {
						min = MCP[x][y];
						xIndex = x;
					}
				} else {
					xIndex = findMinCostPath(MCP, xIndex, y);
					break;
				}
			}
			result[y] = xIndex;
		}
		return result;
	}

	// Transpose the image
	private void transpose() {
		Picture temp = new Picture(height(), width());
		for (int j = 0; j < height(); j += 1) {
			for (int i = 0; i < width(); i += 1) {
				temp.setRGB(j, i , p.getRGB(i, j));
			}
		}
		p = temp;
	}

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		transpose();
		int[] result = findVerticalSeam();
		transpose();
		return result;
	}

	// remove horizontal seam from picture
	public void removeHorizontalSeam(int[] seam) {
		p = SeamRemover.removeHorizontalSeam(p, seam);
	}

	// remove vertical seam from picture
	public void removeVerticalSeam(int[] seam) {
		p = SeamRemover.removeVerticalSeam(p, seam);
	}
}
