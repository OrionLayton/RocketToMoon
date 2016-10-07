import acm.graphics.GRect;

public class RocketSegment extends GRect{

	public RocketSegment(double peakX, double peakY, double width, double segHeight) {
		super(peakX-width/2, peakY, width, segHeight);
	}	
}

