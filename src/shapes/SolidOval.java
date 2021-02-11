package shapes;

import java.awt.Graphics2D;
import static java.lang.Math.min;
import static java.lang.Math.abs;

public class SolidOval extends Shape {
	private static final long serialVersionUID = 1L;

	@Override
	public void draw(Graphics2D p) {
		p.setColor(color);
		p.fillOval(min(x1,x2), min(y1,y2), abs(x1-x2), abs(y1-y2));
	}

}
