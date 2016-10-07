import java.awt.Color;

import acm.graphics.GPolygon;

public class RocketCapsule extends GPolygon{
	public void setPeakX(double peakX){		
	}
	public void setPeakY(double peakY){
	}
	public void setWidth(double width){
	}
	public void setHeight(double height){
	}
	public void drawCapsule(double peakX, double peakY, double width, double height){
		GPolygon capsule = new GPolygon();
		capsule.addEdge(peakX, peakY);
		capsule.addEdge(width/2, height);
		capsule.addEdge(-width, 0);
		
	}
}
