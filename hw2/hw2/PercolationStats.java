package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int times;
	private double[] fraction;

	// perform T independent experiment on an N-by-N grid
	public PercolationStats(int N, int T, PercolationFactory pf) {
		if (N <= 0) {
			throw new IllegalArgumentException(N + " is invalid.");
		} else if (T <= 0) {
			throw new IllegalArgumentException(T + " is invalid.");
		}
		fraction = new double[T];
		for (times = 0; times < T; times += 1) {
			Percolation expGrid = pf.make(N);
			while(!expGrid.percolates()) {
				int x = StdRandom.uniform(0, N);
				int y = StdRandom.uniform(0, N);
				expGrid.open(x, y);
			}
			fraction[times] = (double) expGrid.numberOfOpenSites() / (N * N);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(fraction);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(fraction);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLow() {
		return (mean() - (1.96 * stddev() / Math.sqrt(times)));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHigh() {
		return (mean() + (1.96 * stddev() / Math.sqrt(times)));
	}
}
