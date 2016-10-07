import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GRect;

public class LaunchButton extends GRect{
	public LaunchButton(double x, double y, double width, double height){
		super(x, y, width, height);
		setFilled(true);
		setFillColor(Color.GREEN);
		setColor(Color.WHITE);
	}
}
