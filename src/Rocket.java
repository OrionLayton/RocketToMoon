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
		add(capsule);
		
		GRect segment1 = new GRect(rocketTopX-rocketWidth/2, capBtm, rocketWidth, segmentLength);
		add(segment1);
		GRect segment2 = new GRect(rocketTopX-rocketWidth/2, capBtm+segmentLength, rocketWidth, segmentLength);
		add(segment2);
		GRect segment3 = new GRect(rocketTopX-rocketWidth/2, capBtm+segmentLength*2, rocketWidth, segmentLength);
		add(segment3);
		}
}