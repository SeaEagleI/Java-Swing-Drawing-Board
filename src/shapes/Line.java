package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class Line extends Shape {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void draw(Graphics2D p) {
		p.setColor(color);
		p.setStroke(new BasicStroke(thickness));
		p.drawLine(x1, y1, x2, y2);
	}
	
}
