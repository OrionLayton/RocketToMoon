import java.awt.Color;

import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class RocketToMoonModel extends GraphicsProgram{
	
	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1000;
	private static final double ROCKET_WIDTH = 1;
	private static final double ROCKET_HEIGHT = 5;
	//Size XL rocket for viewing clarity.
	private static final double EARTH_SIZE = 15;
	private static final double EARTH_LEVEL = FRAME_HEIGHT/2-EARTH_SIZE/2;
	private static final double MOON_SIZE = EARTH_SIZE*.27;
	//The moon is approx. 27% the size of the earth.
	private static final double EARTH_RADII = EARTH_SIZE/2;
	private static final double MEAN_LUNAR_DISTANCE = EARTH_RADII*60.32;
	//The distance changes over the course of the orbit but this is the *mean*, relative to Earth's radius
	private static final double ROCKET_TOP_X = FRAME_WIDTH/2+ROCKET_WIDTH/2-2;
	private static final double ROCKET_TOP_Y = EARTH_LEVEL-ROCKET_HEIGHT*1.5;
	private static final int SEGMENTS = 3;
	private static final double LUNAR_ORBIT_DAYS = 27;
	private static final double LUNAR_MOVEMENT_PER_DAY = 360/LUNAR_ORBIT_DAYS;
	private static final double LUNAR_MOVEMENT_PER_HOUR = 360/(LUNAR_ORBIT_DAYS*24);
	

	
	public void init(){
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
	}
	public void run(){
		Earth earth = new Earth(FRAME_WIDTH/2-EARTH_SIZE/2, EARTH_LEVEL, EARTH_SIZE, EARTH_SIZE);
		add(earth);
		Rocket rocket = new Rocket(ROCKET_TOP_X, ROCKET_TOP_Y, ROCKET_WIDTH, ROCKET_HEIGHT, EARTH_LEVEL, SEGMENTS);
		add(rocket);
		Moon moon = new Moon(FRAME_WIDTH/2-EARTH_SIZE/2, FRAME_HEIGHT/2-MEAN_LUNAR_DISTANCE, MOON_SIZE, MOON_SIZE);
		add(moon);
		double angle = 0.0;
		double angleStepSize = LUNAR_MOVEMENT_PER_HOUR*Math.PI/180;
		while (angle < 2 * Math.PI){
			angle += angleStepSize;
			moon.setLocation(MEAN_LUNAR_DISTANCE*Math.cos(angle)+FRAME_WIDTH/2, MEAN_LUNAR_DISTANCE*Math.sin(angle)+FRAME_HEIGHT/2);
			pause(60);
			//60ms = 60 Earth minutes
		}
	}
	
}
