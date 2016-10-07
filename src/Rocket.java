import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GPolygon;
import acm.graphics.GRect;

public class Rocket extends GCompound{
	public Rocket(double rocketTopX, double rocketTopY, double rocketWidth, double rocketHeight, double earthLevel, double segments) {
		double segmentLength = rocketHeight/segments;
		double capBtm = earthLevel-rocketHeight;
		GPolygon capsule = new GPolygon();
		capsule.addEdge(rocketTopX, rocketTopY);
		capsule.addEdge(rocketWidth/2, capBtm-rocketTopY);
		capsule.addEdge(-rocketWidth, 0);
		capsule.setColor(Color.WHITE);
		capsule.setFilled(true);
		capsule.setFillColor(Color.RED);
		add(capsule);
		
		GRect segment1 = new GRect(rocketTopX-rocketWidth/2, capBtm, rocketWidth, segmentLength);
		segment1.setColor(Color.WHITE);
		segment1.setFilled(true);
		segment1.setFillColor(Color.WHITE);
		add(segment1);
		GRect segment2 = new GRect(rocketTopX-rocketWidth/2, capBtm+segmentLength, rocketWidth, segmentLength);
		segment2.setColor(Color.RED);
		segment2.setFilled(true);
		segment2.setFillColor(Color.RED);
		add(segment2);
		GRect segment3 = new GRect(rocketTopX-rocketWidth/2, capBtm+segmentLength*2, rocketWidth, segmentLength);
		segment3.setColor(Color.WHITE);
		segment3.setFilled(true);
		segment3.setFillColor(Color.WHITE);
		add(segment3);
		
		GPolygon flames = new GPolygon();
		flames.addEdge(rocketTopX, rocketTopY+rocketHeight);
		flames.addEdge(rocketWidth/2+3, segmentLength*5);
		flames.addEdge(-rocketWidth-6, 0);
		flames.setColor(Color.YELLOW);
		flames.setFilled(true);
		flames.setFillColor(Color.ORANGE);
		add(flames);
		}

	public Rocket() {
	}
	
}