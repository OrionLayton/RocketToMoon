
public class OrbitingMoon extends Moon
							implements Runnable{
	public OrbitingMoon(double x, double y, double width, double height){
		super(x, y, width, height);
		
	}

	public void run() {
		//OrbitingMoon moon = new OrbitingMoon(0,0,MOON_SIZE,MOON_SIZE);
		
		double angle = 0.0;
		double angleStepSize = LUNAR_MOVEMENT_PER_HOUR*Math.PI/180;
		while (angle < (2*Math.PI)*2){		
			angle += angleStepSize;
			setLocation(MEAN_LUNAR_DISTANCE*Math.cos(angle)+FRAME_WIDTH/2, MEAN_LUNAR_DISTANCE*Math.sin(angle)+FRAME_HEIGHT/2);
			setVisible(true);
			pause(60);
		//60ms = 60 Earth minutes	
		}
	}
	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1000;
	private static final double LUNAR_ORBIT_DAYS = 27;
	private static final double LUNAR_MOVEMENT_PER_HOUR = 360/(LUNAR_ORBIT_DAYS*24);
	private static final double EARTH_RADIUS_IN_MILES = 3959;
	//scaled to fit and doubled for its diameter:
	private static final double EARTH_SIZE = EARTH_RADIUS_IN_MILES*.004;
	private static final double MOON_SIZE = EARTH_SIZE*.27; 
	//The moon is approx. 27% the size of the earth.
	private static final double EARTH_RADII = EARTH_SIZE/2;
	private static final double MEAN_LUNAR_DISTANCE = EARTH_RADII*60.32;
	
}
