package panel;

import static panel.ColorsPanel.jButtonPen;

import java.awt.Color;

import shapes.Shape;
import shapes.SolidCurve;
import shapes.SolidOval;
import shapes.SolidRectangle;
import shapes.Spray;
import shapes.Circle;
import shapes.Curve;
import shapes.Eraser;
import shapes.Line;
import shapes.Oval;
import shapes.Rectangle;
import shapes.Star;
import shapes.Text;

public abstract class Function {
	public static  Shape []shapesQueue=new Shape[10000];
	public static  int index=-1;
	public static int maxShapesCount=-1;
	public static boolean isChanged=false;
	public static  int shapeChoice=0;//默认打开画图工具时画曲线
	protected static int colorChoice=0;//这里默认选择黑色,为橡皮擦而设
	protected static int strokeChoice;
	private static Color []colorArray = {
										new Color(0, 0, 0),new Color(128, 128, 128),new Color(128, 0, 0),new Color(128, 128, 0),
										new Color(0, 128, 0),new Color(0, 128, 128),new Color(0, 0, 128),new Color(128, 0, 128),
										new Color(128, 128, 64),new Color(0, 64, 64),new Color(0, 128, 255),new Color(0, 64, 128),
										new Color(128, 0, 255),new Color(128, 64, 0),new Color(255, 255, 255),new Color(192, 192, 192),
										new Color(255, 0, 0),new Color(255, 255, 0),new Color(0, 255, 0),new Color(0, 255, 255),
										new Color(0, 0, 255),new Color(255, 0, 255),new Color(255, 255, 128),new Color(0, 255, 128),
										new Color(128, 255, 255),new Color(128, 128, 255),new Color(255, 0, 128),new Color(255, 128, 64),
										new Color(128, 192, 255),new Color(255, 128, 192)
									};
	private static float []width= {1,3,5,10};
	public static Color color=colorArray[0];
	public static Color backgroundColor=Color.white;
	public static float stroke=width[0];
	public static String fontStyle=null,inputContent;
	private static Text tmpText=null;
 
	public static void creatAction() {
		switch(shapeChoice) {
			case 0:	shapesQueue[index]=new Curve();break;
			case 1: shapesQueue[index]=new Line();break;
			case 2: shapesQueue[index]=new Eraser();break;
			case 3: tmpText=new Text();
					tmpText.fontName=fontStyle;
					tmpText.inputContent=inputContent;
			        shapesQueue[index]=tmpText;
			        break;
			case 4: shapesQueue[index]=new Star();break;
			case 5: shapesQueue[index]=new Spray();break;
			case 6: shapesQueue[index]=new Rectangle();break;
			case 7: shapesQueue[index]=new SolidRectangle();break;
			case 8: shapesQueue[index]=new Oval();break;
			case 9: shapesQueue[index]=new SolidOval();break;
			case 10: shapesQueue[index]=new Circle();break;
			case 11: shapesQueue[index]=new SolidCurve();break;
		}
		shapesQueue[index].color=color;
		shapesQueue[index].thickness=stroke;
		if(index>maxShapesCount)
			maxShapesCount=index;
	}

	protected static void newColor() {
		color = colorArray[colorChoice];
		jButtonPen.setBackground(color);
	}

	protected static void newStroke() {
		stroke = width[strokeChoice];
	}

	//对ShapesQueue的更新维护
	public static void queueUpdate(int pastIndex,int newIndex) {
		if(shapesQueue[pastIndex].getClass()==Curve.class||shapesQueue[pastIndex].getClass()==Eraser.class
				||shapesQueue[pastIndex].getClass()==Spray.class) {
			if(shapesQueue[pastIndex].left==pastIndex)
				shapesQueue[pastIndex].left=newIndex;
			else if(shapesQueue[pastIndex].right==pastIndex)
				shapesQueue[pastIndex].right=newIndex;
		}
		shapesQueue[newIndex]=shapesQueue[pastIndex];
	}
}
