package mainframecomponent;

import static panel.Function.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class DrawBoard extends JPanel implements MouseListener,MouseMotionListener{
	private static final long serialVersionUID = 1L;
	private JLabel mousePostion;
	public BufferedImage image=null;
	
	public DrawBoard(JLabel mouseDirection) {
		mousePostion=mouseDirection;
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		setBackground(Color.white);
		addMouseListener();
	}
	
	private void addMouseListener() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);//重绘原来固有组件,只有这样才能替换刷新成白板,把原先画过的用白板覆盖
		Graphics2D p=(Graphics2D)g;
		if(image!=null)//如果有image就先绘制image，用于编辑图片。
		    //observer观察者是个不好的设计最好设置为null,这里也因为能够一次读取完image
		    p.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		for(int i=0;i<=index;i++)//重绘之前ShapesQueue中的图形对象
		        shapesQueue[i].draw(p);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		mousePostion.setText("画笔进入:"+"["+e.getX()+","+e.getY()+"]");
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		mousePostion.setText("画笔移出:"+"["+e.getX()+","+e.getY()+"]");
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mousePostion.setText("画笔位置:"+"["+e.getX()+","+e.getY()+"]");
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		mousePostion.setText("画笔按下:"+"["+e.getX()+","+e.getY()+"]");
		if(shapeChoice!=3) {//添加文本只在click监听下有用
		    isChanged=true;//只要按下画笔当前打开文件就被修改,会提醒是否需要保存
		    index++;
		    creatAction();
		    shapesQueue[index].x1=e.getX();
		    shapesQueue[index].y1=e.getY();
		}
		if(shapeChoice==0||shapeChoice==2||shapeChoice==5)
		    shapesQueue[index].left=index;
		if(shapeChoice==5)
		    repaint();
	}
	
	@Override
	public  void mouseDragged(MouseEvent e) {
		mousePostion.setText("画笔画图:"+"["+e.getX()+","+e.getY()+"]");
		if(shapeChoice==0||shapeChoice==2) {
			index++;
			if(index<=maxShapesCount) {//当现在要画的图形会覆盖队列后面的图形时,将队列右移拓宽
				for(int i=maxShapesCount+1;i>=index+1;i--)
					queueUpdate(i-1,i);
				maxShapesCount++;
			}
			creatAction();//放在前面,这样不会多余在左上角多画出一个点
			shapesQueue[index-1].x2=shapesQueue[index].x2=shapesQueue[index].x1=e.getX();
			shapesQueue[index-1].y2=shapesQueue[index].y2=shapesQueue[index].y1=e.getY();
		}
		else if(shapeChoice==5) {//喷漆
		    index++;
		    creatAction();
		    shapesQueue[index].x1=e.getX();
		    shapesQueue[index].y1=e.getY();
		}
		else if(shapeChoice!=3){//只要不是文字,其它单对象图形
			shapesQueue[index].x2=e.getX();
			shapesQueue[index].y2=e.getY();
		}
		if(shapeChoice!=3)//排除插入文本拖动的情形
		    repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePostion.setText("画笔释放:"+"["+e.getX()+","+e.getY()+"]");
		if(shapeChoice==0||shapeChoice==2||shapeChoice==5)
		    shapesQueue[index].right=index;
		if(shapeChoice!=3) {
		    shapesQueue[index].x2=e.getX();
		    shapesQueue[index].y2=e.getY();
		    repaint();//为画一个点而设		    
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    if(shapeChoice==3) {
	        inputContent=JOptionPane.showInputDialog("请输入要添加的文本内容");
	        if(inputContent!=null) {
	            index++;
	            creatAction();
	            shapesQueue[index].x1=e.getX();
	            shapesQueue[index].y1=e.getY();
	            repaint();
	            isChanged=true;
	        }
	    }
	}

}
