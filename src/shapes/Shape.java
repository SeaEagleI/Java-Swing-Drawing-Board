package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Shape implements Serializable{
	private static final long serialVersionUID = 1L;
	public int x1,y1,x2,y2;
	public int left=-1,right=-1;//作为curve和eraser的起始和结束标志
	public Color color;
	public float thickness;
	public boolean EOF=true;
	abstract public void draw(Graphics2D p);
}






