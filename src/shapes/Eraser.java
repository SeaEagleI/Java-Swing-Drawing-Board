package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Eraser extends Shape {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void draw(Graphics2D p) {
		p.setColor(Color.white);
		p.setStroke(new BasicStroke(10));
		p.drawLine(x1, y1, x2, y2);
	}
	
}
