package shapes;

import static java.lang.Math.abs;
import static java.lang.Math.min;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class Oval extends Shape {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void draw(Graphics2D p) {
		p.setColor(color);
		p.setStroke(new BasicStroke(thickness));
		p.drawOval(min(x1, x2), min(y1, y2), abs(x2-x1), abs(y2-y1));
	}
	
}
