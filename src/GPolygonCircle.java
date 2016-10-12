import java.awt.Color;

import acm.graphics.GPolygon;
import acm.program.GraphicsProgram;

public class GPolygonCircle extends GraphicsProgram{
	private static final double RADIUS = 300;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	public GPolygon circle;
	public void run(){
		this.setSize(WIDTH, HEIGHT);
		circle = new GPolygon();
		double angle = 0;
		while(angle < 2 * Math.PI){
		circle.addVertex(RADIUS*Math.cos(angle)+WIDTH/2, RADIUS*Math.sin(angle)+HEIGHT/2);
		angle += .1;
		}
		circle.setFilled(true);
		circle.setFillColor(Color.RED);
		add(circle);
	}
}
