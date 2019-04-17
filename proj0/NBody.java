public class NBody {
	// Read planet numbers
	public static int readPlanetNums(String fileName) {
		In file = new In(fileName);
		int planetNums = file.readInt();
		file.close();
		return planetNums;
	}

	// Read radius of universe
	public static double readRadius(String fileName) {
		In file = new In(fileName);
		int planetNums = file.readInt();
		double universeRadius = file.readDouble();
		file.close(); 
		return universeRadius;
	}

	//
	public static Planet[] readPlanets(String fileName) {
		In file = new In(fileName);
		int planetNums = file.readInt();
		Planet[] allPlanets = new Planet[planetNums];
		double universeRadius = file.readDouble(); 
		for (int i = 0; i < planetNums; i++) {
			double xP = file.readDouble();
			double yP = file.readDouble();
			double xV = file.readDouble();
			double yV = file.readDouble();
			double m = file.readDouble();
			String img = file.readString();
			allPlanets[i] = new Planet(xP, yP, xV, yV, m, img);
		}
		file.close(); 
		return allPlanets;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);			// Simulation end condition
		double dt = Double.parseDouble(args[1]);			// Time interval
		String filename = args[2];					// Import filename
		int planetNums = readPlanetNums(filename);		// How many planets in file
		double radius = readRadius(filename);			// How large the universe is
		Planet[] allPlanets = readPlanets(filename);		// Create instance recorded in file
		
		StdDraw.setScale(-radius, radius);				// Set canvas scale
		StdDraw.clear();						// Clear canvas to white
		StdDraw.picture(0, 0, "images/starfield.jpg");		// Draw background

		/* Draw planets */
		for (Planet p : allPlanets) {
			p.draw();
		}

		StdDraw.enableDoubleBuffering();
		double time = 0.0;
		double[] xForces = new double[planetNums];
		double[] yForces = new double[planetNums];
		while (time <= T) {
			for (int i = 0; i < planetNums; i++) {
				xForces[i] = allPlanets[i].calcNetForceExertedByX(allPlanets);
				yForces[i] = allPlanets[i].calcNetForceExertedByY(allPlanets);
			}
			for (int i = 0; i < planetNums; i++) {
				allPlanets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");

			/* Draw planets */
			for (Planet p : allPlanets) {
				p.draw();
			}
			StdDraw.show();					// Show offscreen canvas to onscreen
			StdDraw.pause(10);					// int t, t milliseconds
			time += dt;
		}
		StdOut.printf("%d\n", allPlanets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < allPlanets.length; i++) {
		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
				allPlanets[i].xxPos, allPlanets[i].yyPos, allPlanets[i].xxVel,
                 		allPlanets[i].yyVel, allPlanets[i].mass, allPlanets[i].imgFileName);
		}   
	}
}