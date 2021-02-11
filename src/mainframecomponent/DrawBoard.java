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
		super.paintComponent(g);//�ػ�ԭ���������,ֻ�����������滻ˢ�³ɰװ�,��ԭ�Ȼ������ðװ帲��
		Graphics2D p=(Graphics2D)g;
		if(image!=null)//�����image���Ȼ���image�����ڱ༭ͼƬ��
		    //observer�۲����Ǹ����õ�����������Ϊnull,����Ҳ��Ϊ�ܹ�һ�ζ�ȡ��image
		    p.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		for(int i=0;i<=index;i++)//�ػ�֮ǰShapesQueue�е�ͼ�ζ���
		        shapesQueue[i].draw(p);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		mousePostion.setText("���ʽ���:"+"["+e.getX()+","+e.getY()+"]");
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		mousePostion.setText("�����Ƴ�:"+"["+e.getX()+","+e.getY()+"]");
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mousePostion.setText("����λ��:"+"["+e.getX()+","+e.getY()+"]");
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		mousePostion.setText("���ʰ���:"+"["+e.getX()+","+e.getY()+"]");
		if(shapeChoice!=3) {//����ı�ֻ��click����������
		    isChanged=true;//ֻҪ���»��ʵ�ǰ���ļ��ͱ��޸�,�������Ƿ���Ҫ����
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
		mousePostion.setText("���ʻ�ͼ:"+"["+e.getX()+","+e.getY()+"]");
		if(shapeChoice==0||shapeChoice==2) {
			index++;
			if(index<=maxShapesCount) {//������Ҫ����ͼ�λḲ�Ƕ��к����ͼ��ʱ,�����������ؿ�
				for(int i=maxShapesCount+1;i>=index+1;i--)
					queueUpdate(i-1,i);
				maxShapesCount++;
			}
			creatAction();//����ǰ��,����������������ϽǶ໭��һ����
			shapesQueue[index-1].x2=shapesQueue[index].x2=shapesQueue[index].x1=e.getX();
			shapesQueue[index-1].y2=shapesQueue[index].y2=shapesQueue[index].y1=e.getY();
		}
		else if(shapeChoice==5) {//����
		    index++;
		    creatAction();
		    shapesQueue[index].x1=e.getX();
		    shapesQueue[index].y1=e.getY();
		}
		else if(shapeChoice!=3){//ֻҪ��������,����������ͼ��
			shapesQueue[index].x2=e.getX();
			shapesQueue[index].y2=e.getY();
		}
		if(shapeChoice!=3)//�ų������ı��϶�������
		    repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePostion.setText("�����ͷ�:"+"["+e.getX()+","+e.getY()+"]");
		if(shapeChoice==0||shapeChoice==2||shapeChoice==5)
		    shapesQueue[index].right=index;
		if(shapeChoice!=3) {
		    shapesQueue[index].x2=e.getX();
		    shapesQueue[index].y2=e.getY();
		    repaint();//Ϊ��һ�������		    
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    if(shapeChoice==3) {
	        inputContent=JOptionPane.showInputDialog("������Ҫ��ӵ��ı�����");
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
