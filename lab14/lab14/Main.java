package lab14;

import lab14lib.*;

public class Main {
	public static void main(String[] args) {
		/** Your code here. */
		//Generator g1 = new SineWaveGenerator(200);

		/*
		GeneratorPlayer gp = new GeneratorPlayer(g1);
		gp.play(1000000);
		*/
		/*
		GeneratorDrawer gd = new GeneratorDrawer(g1);
		gd.draw(4096);
		*/
		/*
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(g1);
		gav.drawAndPlay(4096, 1000000);
		*/
		/*
		Generator g2 = new SineWaveGenerator(201);
		ArrayList<Generator> generators = new ArrayList<>();
		generators.add(g1);
		generators.add(g2);

		MultiGenerator mg = new MultiGenerator(generators);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(mg);
		gav.drawAndPlay(500000, 1000000);
		*/
		/*
		Generator g3 = new SawToothGenerator(512);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(g3);
		gav.drawAndPlay(4096, 1000000);
		*/

		Generator g4 = new AcceleratingSawTooth(200, 1.1);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(g4);
		gav.drawAndPlay(4096, 1000000);

		/*
		Generator g5 = new StrangeBitwiseGenerator(512);
		GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(g5);
		gav.drawAndPlay(4096, 1000000);
		*/
	}
} 