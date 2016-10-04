import java.awt.Color;
import acm.graphics.GOval;

public class Earth extends GOval{
	public Earth(double x, double y, double width, double height){
		super(x, y, width, height);
		setFilled(true);
		setFillColor(Color.BLUE);
		setColor(Color.WHITE);
	}
}
