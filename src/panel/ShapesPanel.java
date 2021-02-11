package panel;

import static panel.Function.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class ShapesPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	public JRadioButton []jRadioButtonPens = new JRadioButton[12];

	public ShapesPanel() {
		setLayout(new GridLayout(7,2));
		String []toolNames = {"自由画笔","直线","橡皮擦","文本框","五角星","喷漆","矩形","实心矩形","椭圆","实心椭圆","圆","实心圆"};
		for(int i=0;i<jRadioButtonPens.length;i++) {
			JRadioButton jRadioButtonPen = new JRadioButton();
			//设定指定按钮被选中
			if (i==0) {
				jRadioButtonPen.setSelected(true);
			}
			ImageIcon rawImageIcon = new ImageIcon("ImageIcons/ShapeIcons/"+toolNames[i]+".png");
			ImageIcon resizedImageIcon = new ImageIcon(rawImageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			jRadioButtonPen.setIcon(resizedImageIcon);
			jRadioButtonPen.setToolTipText(toolNames[i]);
			jRadioButtonPen.addActionListener(this);
			jRadioButtonPen.setLayout(null);
			//Add a red outline to the frame.
			Border blackline = BorderFactory.createLineBorder(Color.black);
			jRadioButtonPen.setBorder(blackline);
			jRadioButtonPen.setBounds(20,20,60,60);
			jRadioButtonPen.setHorizontalAlignment(SwingConstants.CENTER);
			jRadioButtonPen.setVerticalAlignment(SwingConstants.CENTER);
//			jRadioButtonPen.setPreferredSize(new Dimension(20, 20));

			jRadioButtonPens[i] = jRadioButtonPen;
			add(jRadioButtonPen);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0;i<jRadioButtonPens.length;i++) {
			if(e.getSource()==jRadioButtonPens[i]) {
				shapeChoice=i;
			}
		}
	}
}
