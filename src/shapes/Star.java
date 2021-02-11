package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import static java.lang.Math.max;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Star extends Shape {
    private static final long serialVersionUID = 1L;
    private int []xPoints=new int[10];
    private int []yPoints=new int[10];
    private int nPoints=10;
    private int edge;
    
    //标准五角星5个内角为36' 拐角为108'
    private void initPoints() {
        edge=max(abs(x2-x1)/2, abs(y2-y1)/2);
        xPoints[0]=x1;yPoints[0]=y1;
        xPoints[1]=xPoints[0]+edge;yPoints[1]=yPoints[0];
        //72'=0.4π=1.256
        xPoints[2]=(int)(xPoints[1]+edge*cos(1.256));yPoints[2]=(int)(yPoints[1]-edge*sin(1.256));
        xPoints[3]=(int)(xPoints[2]+edge*cos(1.256));yPoints[3]=yPoints[1];
        xPoints[4]=xPoints[3]+edge;yPoints[4]=yPoints[3];
        //54'=0.3π=0.942
        xPoints[5]=(int)(xPoints[4]-edge*sin(0.942));yPoints[5]=(int)(yPoints[4]+edge*cos(0.942));
        xPoints[6]=(int)(xPoints[5]+edge*cos(1.256));yPoints[6]=(int)(yPoints[5]+edge*sin(1.256));
        xPoints[7]=(int)(xPoints[6]-edge*sin(0.942));yPoints[7]=(int)(yPoints[6]-edge*cos(0.942));
        xPoints[8]=(int)(xPoints[7]-edge*sin(0.942));yPoints[8]=(int)(yPoints[7]+edge*cos(0.942));
        xPoints[9]=(int)(xPoints[8]+edge*cos(1.256));yPoints[9]=(int)(yPoints[8]-edge*sin(1.256));
    }
    
    @Override
    public void draw(Graphics2D p) {
        initPoints();
        p.setColor(color);
        p.setStroke(new BasicStroke(thickness));
        p.drawPolygon(xPoints, yPoints, nPoints);
    }
}
