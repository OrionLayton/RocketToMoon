import java.awt.Color;

import acm.graphics.GOval;

public class Moon extends GOval{
	public Moon(double x, double y, double width, double height){
		super(x, y, width, height);
		setFilled(true);
		setFillColor(Color.WHITE);
		setColor(Color.WHITE);
	}
}
