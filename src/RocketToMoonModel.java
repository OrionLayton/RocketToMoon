import java.awt.Color;
import java.awt.Event;
import java.awt.Font;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class RocketToMoonModel extends GraphicsProgram{
	
	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1000;
	private static final double ROCKET_WIDTH = 2;
	private static final double ROCKET_HEIGHT = 8;
	//Size XL rocket for viewing clarity.
	private static final double EARTH_RADIUS_IN_MILES = 3959;
	//scaled to fit and doubled for its diameter:
	private static final double EARTH_SIZE = EARTH_RADIUS_IN_MILES*.004;
	private static final double EARTH_LEVEL = FRAME_HEIGHT/2-EARTH_SIZE/2;
	private static final double MOON_SIZE = EARTH_SIZE*.27;
	//The moon is approx. 27% the size of the earth.
	private static final double EARTH_RADII = EARTH_SIZE/2;
	private static final double MEAN_LUNAR_DISTANCE = EARTH_RADII*60.32;
	//The distance changes over the course of the orbit but this is the *mean*, relative to Earth's radius
	private static final double ROCKET_TOP_X = FRAME_WIDTH/2;
	private static final double ROCKET_TOP_Y = EARTH_LEVEL-ROCKET_HEIGHT*1.5;
	private static final int SEGMENTS = 3;
	private static final double LUNAR_ORBIT_DAYS = 27;
	private static final double LUNAR_MOVEMENT_PER_DAY = 360/LUNAR_ORBIT_DAYS;
	private static final double LUNAR_MOVEMENT_PER_HOUR = 360/(LUNAR_ORBIT_DAYS*24);
	
	public void init(){
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setBackground(Color.BLACK);
	}
	public void run(){
		Earth earth = new Earth(FRAME_WIDTH/2-EARTH_SIZE/2, EARTH_LEVEL, EARTH_SIZE, EARTH_SIZE);
		add(earth);
		Rocket rocket = new Rocket(ROCKET_TOP_X, ROCKET_TOP_Y, ROCKET_WIDTH, ROCKET_HEIGHT, EARTH_LEVEL, SEGMENTS);
		add(rocket);
		Moon moon = new Moon(FRAME_WIDTH/2, FRAME_HEIGHT/2-MEAN_LUNAR_DISTANCE, MOON_SIZE, MOON_SIZE);
		add(moon);
		lunarOrbit(moon, rocket);
		
	}
	public void rocketLaunch(Rocket rocket){
		double y = FRAME_HEIGHT/2;
		double moonIntersect = FRAME_HEIGHT/2-MEAN_LUNAR_DISTANCE;
		while (y > moonIntersect){
			rocket.move(0, -1);
			pause(500);
		}
	}
	//public LaunchWindow(Event e){
		
//	}
	public void lunarOrbit(Moon moon, Rocket rocket){
		//moon.addActionListener(listener);
		double angle = 0.0;
		double angleStepSize = LUNAR_MOVEMENT_PER_HOUR*Math.PI/180;
		double noonInRadians = 270 * Math.PI / 180;
		double rocketSteps = (FRAME_HEIGHT/2-MEAN_LUNAR_DISTANCE)/23.9;
		while (getCollidingObject(moon) == null){
			angle += angleStepSize;
			moon.setLocation(MEAN_LUNAR_DISTANCE*Math.cos(angle)+FRAME_WIDTH/2, MEAN_LUNAR_DISTANCE*Math.sin(angle)+FRAME_HEIGHT/2);
			rocket.move(0, -rocketSteps);
			pause(6);
			//60ms = 60 Earth minutes
		}
		GLabel tada = new GLabel("MOON LANDING!", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		tada.setFont(new Font("Serif", Font.BOLD, 64));
		tada.move(-tada.getWidth()/2, 0);
		tada.setColor(Color.WHITE);
		add(tada);
	}
	private GObject getCollidingObject(Moon moon){
		double x = moon.getX();
		double y = moon.getY();
		if(getElementAt(x, y) != null){
			return getElementAt(x, y);
		} else if (getElementAt(x + (MOON_SIZE), y) != null){
			return getElementAt(x + (MOON_SIZE), y);
		} else if (getElementAt(x, y + (MOON_SIZE)) != null){
			return getElementAt(x, y + (MOON_SIZE));
		} else if (getElementAt(x + (MOON_SIZE), y + (MOON_SIZE)) != null){
			return getElementAt(x + (MOON_SIZE), y + (MOON_SIZE));
		} else {
			return null;
		}
	}
}
