package panel;

import static panel.Function.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import mainframecomponent.DrawBoard;
import shapes.Curve;
import shapes.Eraser;
import shapes.Spray;

public class ShapesQueueUpdate extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private DrawBoard drawPanel;
	public JRadioButton []jRadioButtonPens = new JRadioButton[4];

	public ShapesQueueUpdate(DrawBoard paintBoard) {
		drawPanel=paintBoard;
		setLayout(new GridLayout(2,2));
		String []toolNames= {"����","�ָ�","ɾ��","��ԭ"};
		for(int i=0;i<jRadioButtonPens.length;i++) {
			JRadioButton jRadioButtonPen = new JRadioButton();
			//�趨ָ����ť��ѡ��
			ImageIcon rawImageIcon = new ImageIcon("ImageIcons/ShortCutToolIcons/"+toolNames[i]+".png");
			ImageIcon resizedImageIcon = new ImageIcon(rawImageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
			jRadioButtonPen.setIcon(resizedImageIcon);
			jRadioButtonPen.setToolTipText(toolNames[i]);
			jRadioButtonPen.addActionListener(this);
			jRadioButtonPen.setLayout(null);
			jRadioButtonPen.setBorder(BorderFactory.createLineBorder(Color.red));
			jRadioButtonPen.setBounds(20,20,20,20);
			jRadioButtonPen.setHorizontalAlignment(SwingConstants.CENTER);
			jRadioButtonPen.setVerticalAlignment(SwingConstants.CENTER);
//			jRadioButtonPen.setPreferredSize(new Dimension(20, 20));

			jRadioButtonPens[i] = jRadioButtonPen;
			add(jRadioButtonPen);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jRadioButtonPens[0])
			revoke();
		else if(e.getSource()==jRadioButtonPens[1])
			recover();
		else if(e.getSource()==jRadioButtonPens[2])
			delete();
		else if(e.getSource()==jRadioButtonPens[3])
			restore();
	}
	//������ǰ��β��ͼ��
	private void revoke() {
		if(index>-1) {
			if(shapesQueue[index].getClass()!=Curve.class
					&&shapesQueue[index].getClass()!=Eraser.class
					&&shapesQueue[index].getClass()!=Spray.class)//�ж�����ϵ�ͼ�ε���״
				index--;
			else {
				//��������߻���Ƥ��������,��ôĩ�˵Ķ˵�index��rightһ�����,������԰ѱ����ǵ������
				index--;
				//ֱ�����������߻���Ƥ����������Ҫ�������ߵ������˵����
				while(shapesQueue[index].left!=index)
					index--;
				index--;//ֻ���Ƶ����߻���Ƥ��ǰһ��ͼ�ε�ĩ�˵�
			}
			drawPanel.repaint();
		}
	}
	//�ָ���һ����ͼ��
	private void recover() {
		if(index<maxShapesCount) {
			if(shapesQueue[index+1].getClass()!=Curve.class
					&&shapesQueue[index+1].getClass()!=Eraser.class
					&&shapesQueue[index+1].getClass()!=Spray.class)//�жϺ�һ��ͼ�ε���״
				index++;
			else {
				//˵���������߿��ܲ����Ѿ������ͼ�����,�����������е����ߵĴ���
				if(shapesQueue[index+1].left!=index+1) {
					int left=index+1,right;
					while(shapesQueue[left].right!=left)
						left++;
					left++;//��������Ҫ�����ߵ��¸�ͼ�ο�ʼ���ƶ���
					for(right=left,left=index+1;right<=maxShapesCount;right++,left++)
						queueUpdate(right,left);
					maxShapesCount=left-1;//�����������ͼ����Ŀ
				}
				if(maxShapesCount>index) {//ֻ����������������߲������һ��ͼ��ʱ��ִ��
					//�п��������Ҫ�����ߵ���һ��ͼ�β���������,����һ�������ͼ��
					if(shapesQueue[index+1].left==index+1) {
						//���������߻���Ƥ��ʱֱ�ӽ���,�������߻���Ƥ���Ҷ˵����
						while(shapesQueue[index+1].right!=index+1)
							index++;						
					}
					index++;//������ĩ�˻��һ��ͼ�λ��Ƴ���
				}
			}
			drawPanel.repaint();
		}
	}
	//����ǰ��β��ͼ�δӶ�����ɾ��
	private void delete() {
		if(index>-1) {
			if(shapesQueue[index].getClass()!=Curve.class
					&&shapesQueue[index].getClass()!=Eraser.class
					&&shapesQueue[index].getClass()!=Spray.class) {
				for(int i=index;i<=maxShapesCount-1;i++)//���ƶ���
					queueUpdate(i+1,i);
				maxShapesCount--;
			}
			else {
				int left,right=index+1;
				while(shapesQueue[index].left!=index)
					index--;
				for(left=index;right<=maxShapesCount;right++,left++)
					queueUpdate(right,left);
				maxShapesCount=left-1;
			}
			index--;//ֻ���Ƶ�Ҫɾ��ͼ�ε�ǰһ��ͼ�ε�ĩ��
			drawPanel.repaint();
		}
	}
	//��ͼ�θ�ԭΪ���ͼ����
	private void restore() {
		recover();//������Ϊ���ܰ��м���Ϊ����ͼ�ζ��ضϵĸ���ͼ�θ���ɾ���������Ļ���
		index=maxShapesCount;
		drawPanel.repaint();
	}

}
