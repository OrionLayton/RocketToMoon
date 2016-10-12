import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;


import javax.swing.*;
import java.util.*;
//import javax.swing.*;

import acm.graphics.*;

import acm.program.GraphicsProgram;

public class RocketToMoonModel extends GraphicsProgram
								implements ActionListener{
	
	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1000;
	private static final double ROCKET_WIDTH = 3;
	private static final double ROCKET_HEIGHT = 12;
	//Size XL rocket for viewing clarity.
	private static final double EARTH_RADIUS_IN_MILES = 3959;
	//scaled to fit and doubled for its diameter:
	private static final double EARTH_SIZE = EARTH_RADIUS_IN_MILES*.004;
	private static final double EARTH_LEVEL = FRAME_HEIGHT/2-EARTH_SIZE/2;
	private static final double MOON_SIZE = EARTH_SIZE*.27; 
	//The moon is approx. 27% the size of the earth.
	private static final double EARTH_RADII = EARTH_SIZE/2;
	private static final double MEAN_LUNAR_DISTANCE = EARTH_RADII*60.32;
	private static final double MEAN_LUNAR_DIST_MILES = 3959*60.32;
	//The distance changes over the course of the orbit but this is the *mean*, relative to Earth's radius
	private static final double POINTS_TO_MILES = 20;
	private static final double ROCKET_TOP_X = FRAME_WIDTH/2;
	private static final double ROCKET_TOP_Y = EARTH_LEVEL-ROCKET_HEIGHT*1.5;
	private static final int SEGMENTS = 3;
	private static final double LUNAR_ORBIT_DAYS = 27;
	private static final double LUNAR_MOVEMENT_PER_HOUR = 360/(LUNAR_ORBIT_DAYS*24);
//	private static final double ROCKET_STEPS = (FRAME_HEIGHT/2-MEAN_LUNAR_DISTANCE)/23.9;
	private static final int MIN_RKT_SPEED = 1;
	private static final int MAX_RKT_SPEED = 99;
	private static final int DEFAULT_RKT_SPEED = 9;
	private static final int MIN_YAW = -1;
	private static final int MAX_YAW = 1;
	private static final int DEFAULT_YAW = 0;
	private Earth earth;
	//private Moon moon;
	private Rocket rocket;
	private JSlider rocketSpeed;
	private JSlider yaw;
	private GLabel title;
	private GLabel clickAnywhere;
	private GLabel three;
	private GLabel two;
	private GLabel one;
	private GLabel liftOff;
	private GLabel gameOver;
	private GLabel tada;
	private OrbitingMoon moon;
	private GLabel fuel;
	private GLabel lunarDist;
	private GLabel earthSize;
	private GLabel moonSize;
	private GLabel orbitDays;
	private GLabel rktEarthDist;
	private GLabel rktMoonDist;
	
	Thread moonThread;

	public void init(){
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setBackground(Color.BLACK);
		addMouseListeners();
		addActionListeners();
		earthSize = new GLabel("Earth Diameter: "+ (int)EARTH_RADIUS_IN_MILES*2+" miles", 50,50);
		earthSize.setColor(Color.WHITE);
		earthSize.setFont(new Font("Serif", Font.BOLD, 24));
		add(earthSize);
		moonSize = new GLabel("Moon Diameter: "+ (int)(EARTH_RADIUS_IN_MILES*2)*.27+" miles", 50,75);
		moonSize.setColor(Color.WHITE);
		moonSize.setFont(new Font("Serif", Font.BOLD, 24));
		add(moonSize);
		orbitDays = new GLabel("Lunar Orbit Time: "+ (int)LUNAR_ORBIT_DAYS+" Earth days", 50,100);
		orbitDays.setColor(Color.WHITE);
		orbitDays.setFont(new Font("Serif", Font.BOLD, 24));
		add(orbitDays);
		lunarDist = new GLabel("Earth to Moon: approx. "+ (int)MEAN_LUNAR_DIST_MILES+" miles", 50,125);
		lunarDist.setColor(Color.WHITE);
		lunarDist.setFont(new Font("Serif", Font.BOLD, 24));
		add(lunarDist);
		rocketSpeed = new JSlider(MIN_RKT_SPEED, MAX_RKT_SPEED, DEFAULT_RKT_SPEED);
		add(new JLabel("Throttle"),NORTH);
		add(rocketSpeed, NORTH);
		yaw = new JSlider(MIN_YAW, MAX_YAW, DEFAULT_YAW);
		add(new JLabel("Yaw"), NORTH);
		add(yaw, NORTH);
		rocket = new Rocket(ROCKET_TOP_X, ROCKET_TOP_Y, ROCKET_WIDTH, ROCKET_HEIGHT, EARTH_LEVEL, SEGMENTS);
		add(rocket);
		earth = new Earth(FRAME_WIDTH/2-EARTH_SIZE/2, EARTH_LEVEL, EARTH_SIZE, EARTH_SIZE);
		add(earth);
		moon = new OrbitingMoon(FRAME_WIDTH/2, FRAME_HEIGHT/2-MEAN_LUNAR_DISTANCE, MOON_SIZE, MOON_SIZE);
		add(moon);
		moon.setVisible(true);
		moonThread = new Thread(moon);
	}
	public void run(){	
		title();
		clickAnywhere();
		waitForClick();
		remove(clickAnywhere);
		opening();
		moonThread.start();
		launchRocket();
		
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Launch")){
			launchRocket();
		}
	} 
	public double getRocketSpeed(){
		return (double)rocketSpeed.getValue();
	}
	public double getYaw(){
		return (double)yaw.getValue();
	}
	public void launchRocket(){
		int rocketFuel = 500;
		String strFuel ="";
		String strEarthDist = "";
		String strRktMoonDist = "";
		rktEarthDist = new GLabel("", 50, 875);
		rktEarthDist.setColor(Color.WHITE);
		rktEarthDist.setFont(new Font("Serif", Font.BOLD, 24));
		rktMoonDist = new GLabel("", 50, 900);
		rktMoonDist.setColor(Color.WHITE);
		rktMoonDist.setFont(new Font("Serif", Font.BOLD, 24));
		fuel = new GLabel("", 50,925);
		fuel.setColor(Color.WHITE);
		fuel.setFont(new Font("Serif", Font.BOLD, 24));
		while (getCollidingObject() != rocket){	
			strEarthDist = "Rocket Distance From Earth: " +(rocketEarthDist(rocket.getLocation(), earth.getLocation())*POINTS_TO_MILES)+" miles";
			strFuel = "Fuel remaining: " + rocketFuel;
			strRktMoonDist = "Rocket Distance From Moon: "+(rocketMoonDist(rocket.getLocation(), moon.getLocation())*POINTS_TO_MILES)+" miles";
			rktEarthDist.setLabel(strEarthDist);
			//add(rktEarthDist);
			rktMoonDist.setLabel(strRktMoonDist);
		//	add(rktMoonDist);
			fuel.setLabel(strFuel);
			add(fuel);
			rocket.move(getYaw(), getRocketSpeed()*-.1045);
			pause(60);
		//60ms = 60 Earth minutes
			remove(fuel);
			remove(rktEarthDist);
			remove(rktMoonDist);
			if(rocketFuel <= 0){
				gameOver();
				remove(rocket);
				remove(fuel);
				
			}
			rocketFuel -= 1;
		}
		splosion();
		remove(rocket);
		remove(moon);
		tada();	
	}
	private double rocketMoonDist(GPoint rkt, GPoint moon){
		double distance = Math.sqrt(((rkt.getX()-moon.getX())*(rkt.getX()-moon.getX()))+((rkt.getY()-moon.getY())*(rkt.getY()-moon.getY())));
		return distance;
		
	}
	private double rocketEarthDist(GPoint rkt, GPoint earth){
		double distance = Math.sqrt(((rkt.getX()-earth.getX())*(rkt.getX()-earth.getX()))+((rkt.getY()-earth.getY())*(rkt.getY()-earth.getY())));
		return distance;
	}
	private void splosion(){
		GStar star1 = new GStar(60);
		GStar star2 = new GStar(60);
		GStar star3 = new GStar(60);
		GStar star4 = new GStar(60);
		star1.setFillColor(Color.PINK);
		star2.setFillColor(Color.ORANGE);
		star3.setFillColor(Color.RED);
		star4.setFillColor(Color.YELLOW);
		star1.setFilled(true);
		star2.setFilled(true);
		star3.setFilled(true);
		star4.setFilled(true);
		star1.setLocation(moon.getLocation());
		star2.setLocation(moon.getLocation());
		star3.setLocation(moon.getLocation());
		star4.setLocation(moon.getLocation());
		star2.move(-10, 10);
		star3.move(10, -10);
		star4.move(-10, -10);
		add(star1);
		pause(50);
		add(star2);
		pause(50);
		add(star3);
		pause(50);
		add(star4);
	}
	private void tada(){
		tada = new GLabel("MOON LANDING!", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		tada.setFont(new Font("Serif", Font.BOLD, 64));
		tada.move(-tada.getWidth()/2, 0);
		tada.setColor(Color.WHITE);
		for(int i = 0; i < 155; i++){
			add(tada);
			pause(50);
			remove(tada);
			pause(50);
		}
	}
	private void clickAnywhere(){
		clickAnywhere = new GLabel("click anywhere to begin", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		clickAnywhere.setFont(new Font("Serif", Font.BOLD, 48));
		clickAnywhere.move(-clickAnywhere.getWidth()/2, 0);
		clickAnywhere.setColor(Color.WHITE);
		add(clickAnywhere);
		
	}
	private void title(){
		title = new GLabel("ASTRO NOT", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		title.setFont(new Font("Serif", Font.BOLD, 64));
		title.move(-title.getWidth()/2, 0);
		title.setColor(Color.WHITE);
		for(int i = 0; i < 10; i++){
			add(title);
			remove(title);
			pause(5);
		}
		add(title);
		pause(1000);
		remove(title);
	}
	private void three(){
		three = new GLabel("3", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		three.setFont(new Font("Serif", Font.BOLD, 64));
		three.move(-three.getWidth()/2, 0);
		three.setColor(Color.WHITE);
		add(three);
		pause(1000);
		remove(three);
	}
	private void two(){
		two = new GLabel("2", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		two.setFont(new Font("Serif", Font.BOLD, 64));
		two.move(-two.getWidth()/2, 0);
		two.setColor(Color.WHITE);
		add(two);
		pause(1000);
		remove(two);
	}
	private void one(){
		one = new GLabel("1", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		one.setFont(new Font("Serif", Font.BOLD, 64));
		one.move(-one.getWidth()/2, 0);
		one.setColor(Color.WHITE);
		add(one);
		pause(1000);
		remove(one);
	}
	private void liftOff(){
		liftOff = new GLabel("LIFTOFF!", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		liftOff.setFont(new Font("Serif", Font.BOLD, 64));
		liftOff.move(-liftOff.getWidth()/2, 0);
		liftOff.setColor(Color.WHITE);
		add(liftOff);
		pause(1000);
		remove(liftOff);
	}
	private void gameOver(){
		gameOver = new GLabel("Well, so long then...", FRAME_WIDTH/2, FRAME_HEIGHT/3);
		gameOver.setFont(new Font("Serif", Font.BOLD, 64));
		gameOver.move(-gameOver.getWidth()/2, 0);
		gameOver.setColor(Color.WHITE);
		add(gameOver);

	}
	private GObject getCollidingObject(){
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
	public void opening(){
		three();
		two();
		one();
		liftOff();
	}
	
}
