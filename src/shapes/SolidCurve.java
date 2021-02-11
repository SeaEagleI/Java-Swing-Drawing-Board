package shapes;

import java.awt.Graphics2D;
import static java.lang.Math.min;
import static java.lang.Math.max;
import static java.lang.Math.abs;

public class SolidCurve extends Shape {
	private static final long serialVersionUID = 1L;

	@Override
	public void draw(Graphics2D p) {
		p.setColor(color);
		p.fillOval(min(x1,x2), min(y1,y2), max(abs(x1-x2),abs(y1-y2)), max(abs(x1-x2),abs(y1-y2)));
	}
//	p.fillOval(min(x1,x2), min(y1,y2), abs(x1-x2), abs(y1-y2));//Add a red outline to the frame.


}
