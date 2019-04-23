package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
	private int period;
	private int state;

	public StrangeBitwiseGenerator(int period) {
		this.period = period;
		state = 0;
	}

	@Override
	public double next() {
		state = state + 1;
		int weirdState = state & (state >>> 3) % period;
		// Slope = (1 - (-1)) / period
		// Each state increment = slope * state
		return (-1 + 2.0 * weirdState / period);
	}
}
