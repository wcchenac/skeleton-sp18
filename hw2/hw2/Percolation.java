package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private static boolean[][] gridOpen;
	private int source, end;
	private int openSite;
	private int gridWidth;
	private WeightedQuickUnionUF unionSet;
	private WeightedQuickUnionUF unionSetNoBackWash;

	// create N-by-N grid, with all sites initially blocked
	public Percolation(int N) {
		if (N < 0) {
			throw new IllegalArgumentException("Please enter an positive integer");
		}
		gridOpen = new boolean[N][N];
		for (int j = 0; j < N; j += 1) {
			for (int i = 0; i < N; i += 1) {
				gridOpen[i][j] = false;
			}
		}
		gridWidth = N;
		openSite = 0;
		unionSet = new WeightedQuickUnionUF(N * N + 2);             // N*N + source + end
		unionSetNoBackWash = new WeightedQuickUnionUF(N * N + 1);   // N*N + source
		source = 0;
		end = N * N + 1;
	}

	private int convert(int x, int y) {
		return (gridWidth * y + x + 1);     //set an integer for (col, row), e.g. (0, 0) = 1
	}

	// open the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (col >= gridOpen.length || col < 0) {
			throw new IndexOutOfBoundsException("Column is out of bound.");
		} else if (row >= gridOpen[0].length || row < 0) {
			throw new IndexOutOfBoundsException("Row is out of bound.");
		} else if (isOpen(row, col)) {
			return;
		} else {
			gridOpen[col][row] = true;
			openSite += 1;
			if (row == 0) {
				unionSet.union(0, convert(col, row));
				unionSetNoBackWash.union(0, convert(col, row));
			}
			if (row == gridOpen[0].length - 1) {
				unionSet.union(end, convert(col, row));
			}
		}
		// verify Down/Up/Right/Left site and union
		if ((row + 1) < gridOpen[0].length && isOpen(row + 1, col)) {
			unionSet.union(convert(col, row), convert(col, row + 1));
			unionSetNoBackWash.union(convert(col, row), convert(col, row + 1));
		}
		if ((row - 1) >= 0 && isOpen(row - 1, col)) {
			unionSet.union(convert(col, row), convert(col, row - 1));
			unionSetNoBackWash.union(convert(col, row), convert(col, row - 1));
		}
		if ((col + 1) < gridOpen.length && isOpen(row, col + 1)) {
			unionSet.union(convert(col, row), convert(col + 1, row));
			unionSetNoBackWash.union(convert(col, row), convert(col + 1, row));
		}
		if ((col - 1) >= 0 && isOpen(row, col - 1)) {
			unionSet.union(convert(col, row), convert(col - 1, row));
			unionSetNoBackWash.union(convert(col, row), convert(col - 1, row));
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (col >= gridOpen.length || col < 0) {
			throw new IndexOutOfBoundsException("Column is out of bound.");
		} else if (row >= gridOpen[0].length || row < 0) {
			throw new IndexOutOfBoundsException("Row is out of bound.");
		} else {
			return gridOpen[col][row];
		}
	}

	// is the site (row, col) unionSet
	public boolean isFull(int row, int col) {
		return (isOpen(row, col) && unionSetNoBackWash.connected(source, convert(col, row)));
	}

	// number of open sites
	public int numberOfOpenSites() {
		return openSite;
	}

	// does the system percolate?
	public boolean percolates() {
		return (unionSet.connected(source, end));
	}

	/*
	// use for unit testing
	public static void main(String[] args) {
		/*
		Percolation xx = new Percolation(5);

		System.out.println(xx.isFull(0, 3));
		xx.open(2, 3);
		System.out.println(xx.isFull(2, 3));
		xx.open(0,3);
		System.out.println(xx.isFull(0, 3));
		xx.open(1,3);
		System.out.println(xx.isFull(2, 3));
		xx.open(3,3);
		xx.open(4,3);

		System.out.println(xx.percolates());


		PercolationFactory pf = new PercolationFactory();
		PercolationStats ps = new PercolationStats(5, 30, pf);
		System.out.println(ps.mean());
		System.out.println(ps.stddev());
		System.out.println(ps.confidenceLow());
		System.out.println(ps.confidenceHigh());

		for (int j = 0; j < 5; j += 1) {
			for (int i = 0; i < 5; i += 1) {
				System.out.print(xx.gridNum[i][j] + " ");
			}
			System.out.println();
		}
		for (int j = 0; j < 5; j += 1) {
			for (int i = 0; i < 5; i += 1) {
				System.out.print(xx.gridOpen[i][j] + " ");
			}
			System.out.println();
		}

	}
	*/
}
