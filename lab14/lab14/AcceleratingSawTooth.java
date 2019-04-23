package lab14;

import lab14lib.Generator;

public class AcceleratingSawTooth implements Generator {
	private double period;
	private double state;
	private double factor;

	public AcceleratingSawTooth(int period, double factor) {
		this.period = period;
		state = 0;
		this.factor = factor;
	}

	@Override
	public double next() {
		state = (state + 1) % period;
		double val = -1 + 2.0 * state / period;
		if ((int) state == 0) {
			period = period * factor;
			state = 0;
		}
		return val;
	}
}
