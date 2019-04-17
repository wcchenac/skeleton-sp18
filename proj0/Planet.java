public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	
	// Constructor for Planet properties
	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}		

	// Constructor for initialization a copy Planet
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	// Calculate the distance between the supplied planet and the planet that is doing the calculation  
	public double calcDistance(Planet p) {
		double distance = Math.sqrt((p.xxPos - this.xxPos) * (p.xxPos - this.xxPos) + (p.yyPos - this.yyPos) * (p.yyPos - this.yyPos));
		return distance;
	}

	// Calculate the force exerted on this planet by the given planet
	public double calcForceExertedBy(Planet p) {
		double force = (6.67e-11) * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
		return force;
	}

	// Calculate the force exerted in the X direction
	public double calcForceExertedByX(Planet p) {
		double forceX = this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
		return forceX;
	}

	// Calculate the force exerted in the Y direction
	public double calcForceExertedByY(Planet p) {
		double forceY = this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
		return forceY;
	}

	// Calculate the net force exerted in the X direction
	public double calcNetForceExertedByX(Planet[] p) {
		double netForceX = 0.0;
		int i;
		for (i = 0; i < p.length; i++) {
			if (this.equals(p[i])) {
				continue;
			}
			netForceX += this.calcForceExertedByX(p[i]);
		}
		return netForceX;
	}

	// Calculate the net force exerted in the Y direction
	public double calcNetForceExertedByY(Planet[] p) {
		double netForceY = 0.0;
		int i;
		for (i = 0; i < p.length; i++) {
			if (this.equals(p[i])) {
				continue;
			}
			netForceY += this.calcForceExertedByY(p[i]);
		}
		return netForceY;
	}

	// Calculate last velocity and position
	public void update(double dt, double fX, double fY) {
		double accelX = fX / this.mass;
		double accelY = fY / this.mass;
		this.xxVel += dt * accelX;
		this.yyVel += dt * accelY;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}

	// Draw One Planet
	public void draw() {
		String filepath = "images/" + this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, filepath);
	}
}