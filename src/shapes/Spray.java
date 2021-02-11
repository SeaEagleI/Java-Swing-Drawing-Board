package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.Random;

public class Spray extends Shape {
    private static final long serialVersionUID = 1L;
    private Random rand=new Random();
    private int []xChange=new int[40];
    private int []yChange=new int[40];
    private boolean flag=false;

    @Override
    public void draw(Graphics2D p) {
        p.setColor(color);
        p.setStroke(new BasicStroke(thickness));
        for(int i=0;i<40;i++) {//每次拖动画出40个随机点分布
            if(!flag) {
                xChange[i]=rand.nextInt(20);//定义坐标半径变化范围在20以内
                yChange[i]=rand.nextInt(20);
            }
            p.drawLine(x1+xChange[i], y1+yChange[i], x1+xChange[i], y1+yChange[i]);
        }
        flag=true;
    }

}
